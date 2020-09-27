package org.firstinspires.ftc.teamcode.command;

import java.util.Set;

public interface Command {

    /**
     * The initial subroutine of a command. Called once when the command is first
     * scheduled.
     */
    default void init() {
    }

    /**
     * The main body of a command. Called repeatedly while the command is scheudled
     */
    default void execute() {
    }

    /**
     * Called when the command finishes, either normally, or if it is interupted or
     * canceled
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    default void end(boolean interrupted) {
    }

    /**
     * Whether the command has finished. Once a command finishes, the scheudler will
     * call the end() method and un-schedule it.
     *
     * @return whether the command has finished
     */
    default boolean isFinished() {
        return false;
    }

    /**
     * Specifies the set of subsystems that are used by this command. Two commands
     * cannot use the same subsystems, so the subsystem will interrupt this command
     * if it is labeled iterruptable or it will wait for the command to finish
     * before executeing the next command.
     *
     * @return the set of subsystems that are required
     */
    Set<Subsystem> getRequirements();

    /**
     * Whether the command requires a given subsystem
     *
     * @param requirement the subsystem to inquire about
     * @return whether the subsystem is required
     */
    default boolean hasRequirement(Subsystem requirement) {
        return getRequirements().contains(requirement);
    }

    /**
     * Schedules this command.
     *
     * @param interruptable whether or not this command can be interrupted by another command
     */
    default void schedule(boolean interruptable) {
        CommandScheduler.getInstance().schedule(interruptable, this);
    }

    /**
     * Schedules this command, defaulting to interruptible.
     */
    default void schedule() {
        schedule(true);
    }

    /**
     * Cancels this command. Will call the command's interrupted() method.
     * Commands will be canceled even if they are not marked as interruptible.
     */
    default void cancel() {
        CommandScheduler.getInstance().cancel(this);
    }

    /**
     * Whether or not the command is currently scheduled. Doesn't detect if the command is in a command group
     *
     * @return whether the command should run when the robot is disabled
     */
    default boolean isScheduled() {
        return CommandScheduler.getInstance().isScheduled(this);
    }

    default boolean runsWhenDisabled() {
        return false;
    }

    default String getName() {
        return this.getClass().getSimpleName();
    }




}
