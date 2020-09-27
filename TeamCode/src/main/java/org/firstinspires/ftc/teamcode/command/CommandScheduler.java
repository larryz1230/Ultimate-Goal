package org.firstinspires.ftc.teamcode.command;

import android.os.Build;
import android.text.style.SubscriptSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class CommandScheduler {
    /**
     * The Singleton Instance
     */
    private static CommandScheduler instance;

    /**
     * Returns the Scheduler instance.
     *
     * @return CommandScheudler instance
     */
    public static synchronized CommandScheduler getInstance() {
        if (instance == null) {
            instance = new CommandScheduler();
        }

        return instance;
    }

    // A map from command to their state.
    private final Map<Command, CommandState> m_scheduledCommands = new LinkedHashMap<>();

    // A map from required subsystems to their requiring commands.
    private final Map<Subsystem, Command> m_requirements = new LinkedHashMap<>();

    // A map from subssytems registered with the scheduler to their default commands
    private final Map<Subsystem, Command> m_subsystems = new LinkedHashMap<>();

    // A set of registered buttons that will be polled every iteration
    private final Collection<Runnable> m_buttons = new LinkedHashSet<>();

    private boolean m_disabled;

    // Lists of user-supplied actions to be executed on scheduling events for every
    // command.
    private final List<Consumer<Command>> m_initActions = new ArrayList<>();
    private final List<Consumer<Command>> m_executeActions = new ArrayList<>();
    private final List<Consumer<Command>> m_interruptActions = new ArrayList<>();
    private final List<Consumer<Command>> m_finishActions = new ArrayList<>();

    CommandScheduler() {
    }

    /**
     * Adds a button binding to the scheduler, which will be polled to scheudle
     * commands
     */
    public void addButton(Runnable button) {
        m_buttons.add(button);
    }

    /**
     * Removes all button bindings from the scheduler.
     */
    public void clearButtons() {
        m_buttons.clear();
    }

    /**
     * Initializes a given command, adds its requirements to the list, and performs
     * the init actions.
     *
     * @param command       The command to initialize
     * @param interruptible Whether the command is iterruptable
     * @param requirements  The command requirements
     */
    private void initCommand(Command command, boolean interruptible, Set<Subsystem> requirements) {
        CommandState scheduledCommand = new CommandState(interruptible);
        command.init();
        for (Subsystem requirement : requirements) {
            m_requirements.put(requirement, command);
        }

        for (Consumer<Command> action : m_initActions) {
            action.accept(command);
        }
    }

    /**
     * Schedulers a command for execution. Does nothing if the command is already
     * scheduled. If a command's requirements are not available, it will only be
     * started if all the commands currently using those requiremetns are
     * interruptible.
     *
     * @param interruptible whether this command can be interrupted
     * @param command       the command to schedule
     */
    private void schedule(boolean interruptible, Command command) {
        if (CommandGroupBase.getGroupedCommands().contains(command)) {
            throw new IllegalArgumentException("A command that is part of a command group cannot be independently scheduled");
        }

        // Do nothing if the scheduler is disabled, the robot is disabled and the
        // command doesn't run
        if (m_disabled || command.runsWhenDisabled() || m_scheduledCommands.containsKey(command)) {
            return;
        }

        Set<Subsystem> requirements = command.getRequirements();

        // Schedule the command if the requirements are not currently in-use.
        if (Collections.disjoint(m_requirements.keySet(), requirements)) {
            initCommand(command, interruptible, requirements);
        } else {
            // Else check if the requirements that are in use all have interruptible
            // commands,
            // and if so, interrupt those commands and schedule the new command.
            for (Subsystem requirement : requirements) {
                if (m_requirements.containsKey(requirement)
                        && !m_scheduledCommands.get(m_requirements.get(requirement)).isIterruptible()) {
                    return;
                }
            }

            for (Subsystem requirement : requirements) {
                if (m_requirements.containsKey(requirement)) {
                    cancel(m_requirements.get(requirement));
                }
            }

            initCommand(command, interruptible, requirements);

        }
    }

    /**
     * Schedules multiple commands for execution.
     *
     * @param interruptible whether the commands should be interruptible
     * @param commands      the commands to schedule
     */
    public void schedule(boolean interruptible, Command... commands) {
        for (Command command : commands) {
            schedule(interruptible, command);
        }
    }

    /**
     * Schedules multiple commands for execution, with interruptible defaulting to
     * true.
     *
     * @param commands the commands to schedule
     */
    public void schedule(Command... commands) {
        schedule(true, commands);
    }

    /**
     * Runs a single iteration of the scheudler. The execution occurs in the
     * following order:
     *
     * <p>
     * Subsystem periodic methods are called.
     *
     * <p>
     * Button bindings are polled, and new commands are scheduled from them.
     *
     * <p>
     * Currently-scheduled commands are executed.
     *
     * <p>
     * End conditions are checked on commands, and commands that are finished have
     * their end methods called and are removed.
     *
     * <p>
     * Any subsystems not being used as requirements have their default methods
     * started.
     */
    public void run() {
        if (m_disabled) {
            return;
        }

        // Run the periodic method for all registered subsystems.
        for (Subsystem subsystem : m_subsystems.keySet()) {
            subsystem.periodic();
        }

        // Poll buttons for new commands to add.
        for (Runnable button : m_buttons) {
            button.run();
        }

        // Run scheduled commands, remove finished commands.
        for (Iterator<Command> iterator = m_scheduledCommands.keySet().iterator(); iterator.hasNext(); ) {
            Command command = iterator.next();

            if (!command.runsWhenDisabled()) {
                command.end(true);
                for (Consumer<Command> action : m_interruptActions) {
                    action.accept(command);
                }
                m_requirements.keySet().removeAll(command.getRequirements());
                iterator.remove();
                continue;
            }

            command.execute();

            for (Consumer<Command> action : m_executeActions) {
                action.accept(command);
            }
            if (command.isFinished()) {
                command.end(false);
                for (Consumer<Command> action : m_finishActions) {
                    action.accept(command);
                }

                iterator.remove();

                m_requirements.keySet().removeAll(command.getRequirements());
            }

        }

        // Add default commands for un-required registered subsystems.
        for (Map.Entry<Subsystem, Command> subsystemCommand : m_subsystems.entrySet()) {
            if (!m_requirements.containsKey(subsystemCommand.getKey()) && subsystemCommand.getValue() != null) {
                schedule(subsystemCommand.getValue());
            }
        }
    }

    /**
     * Registers subsystems with the scheduler. This must be called for subsystem's periodic
     * block to run when the scheduler is run, and for the subsystem's default command to be
     * scheduled. It is recommended to call this from the constructor of your subsystem
     * implementations.
     *
     * @param subsystems the subsystems to register
     */
    public void registerSubsystem(Subsystem... subsystems) {
        for (Subsystem subsystem : subsystems) {
            m_subsystems.put(subsystem, null);
        }
    }

    /**
     * Un-registers subsystems with the scheduler. The subsystem will no longer have its periodic
     * block called, and will not have its default command scheduled.
     *
     * @param subsystems the subsystems to un-register
     */
    public void unregisterSubsystem(Subsystem... subsystems) {
        m_subsystems.keySet().removeAll(Arrays.asList(subsystems));
    }

    /**
     * Sets the default command for a subsystem. Registers that subsystem if it is not already registered.
     * Default commands will run whenever there is no other command currently scheduled
     * that requires the subsystem.
     *
     * @param subsystem      the system whose default command will set
     * @param defaultCommand the default command to associate with the subsystem
     */
    public void setDefaultCommand(Subsystem subsystem, Command defaultCommand) {
        if (!defaultCommand.getRequirements().contains(subsystem)) {
            throw new IllegalArgumentException("Default commands must require their subsystems!");
        }

        if (defaultCommand.isFinished()) {
            throw new IllegalArgumentException("Default commands should not end!");
        }

        m_subsystems.put(subsystem, defaultCommand);
    }

    /**
     * Gets the default command associated with this subsystem. Null if this subsystem has not default command associated with it.
     *
     * @param subsystem the subsystem to inquire about
     * @return the default command associated with the subsystem
     */
    public Command getDefaultCommand(Subsystem subsystem) {
        return m_subsystems.get(subsystem);
    }

    /**
     * Cancels commands. The scheduler will only call the end method
     * of the canceled command with {@code true},
     * indicating they were canceled (as opposed to finishing normally.
     *
     * <p>Commands will be canceled even if they are not scheduled as interruptible.
     */
    public void cancel(Command... commands) {
        for (Command command : commands) {
            if (!m_scheduledCommands.containsKey(command)) {
                continue;
            }

            command.end(true);
            for (Consumer<Command> action : m_interruptActions) {
                action.accept(command);
            }

            m_scheduledCommands.remove(command);
            m_requirements.keySet().removeAll(command.getRequirements());
        }
    }

    /**
     * Cancels all commands that are currenly scheduled.
     */
    public void cancelAll() {
        for (Command command : m_scheduledCommands.keySet()) {
            cancel(command);
        }
    }

    /**
     * Whether the given commands are running.
     * Note that this only works on commands that are directly scheduled by the
     * scheduler and will not work on commands inside CommandGroups.
     *
     * @param commands the command to query
     * @return whether the command is currently scheduled
     */
    public boolean isScheduled(Command... commands) {
        return m_scheduledCommands.keySet().containsAll(Arrays.asList(commands));
    }
    /**
     * Returns the command currently requiring a given subsystem. Null if no commands are requiring the subsystem
     * @param subsystem the subsystem to be inquired about
     * @return the command currently requiring the subsystem
     */
    public Command requiring(Subsystem subsystem) {
        return m_requirements.get(subsystem);
    }

    /**
     * Disables the command scheduler
     */
    public void disable() {
        m_disabled = true;
    }

    /**
     * Enables the command scheduler
     */
    public void enable() {
        m_disabled = false;
    }

    /**
     * Adds an action to perform on the initialization of any command by the scheduler.
     *
     * @param action the action to perform
     */
    public void onCommandInitialize(Consumer<Command> action) {
        m_initActions.add(action);
    }

    /**
     * Adds an action to perform on the execution of any command by the scheduler.
     *
     * @param action the action to perform
     */
    public void onCommandExecute(Consumer<Command> action) {
        m_executeActions.add(action);
    }

    /**
     * Adds an action to perform on the interruption of any command by the scheduler.
     *
     * @param action the action to perform
     */
    public void onCommandInterrupt(Consumer<Command> action) {
        m_interruptActions.add(action);
    }

    /**
     * Adds an action to perform on the finishing of any command by the scheduler.
     *
     * @param action the action to perform
     */
    public void onCommandFinish(Consumer<Command> action) {
        m_finishActions.add(action);
    }
}
