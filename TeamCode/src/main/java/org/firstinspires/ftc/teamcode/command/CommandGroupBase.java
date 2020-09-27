package org.firstinspires.ftc.teamcode.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class CommandGroupBase extends CommandBase implements Command {

  public static final Set<Command> m_groupedCommands = Collections.newSetFromMap(new WeakHashMap<>());

  static void registerGroupedCommands(Command... commands) {
    m_groupedCommands.addAll(Arrays.asList(commands));
  }

  /**
   * Clears the list of grouped commands, allowing commands to be freely used
   * again.
   */
  public static void clearGroupedCommands() {
    m_groupedCommands.clear();
  }

  /**
   * Removes a single command from the list of grouped commands, allowing it to be
   * freely used again.
   * 
   * @param command the command to remove from the list of grouped commands
   */
  public static void clearGroupedCommand(Command command) {
    m_groupedCommands.remove(command);
  }

  /**
   * Requires that the specified commands not have been already allowed to a
   * CommandGroup.
   * 
   * @param commands The commands to check
   */
  public static void requireUngrouped(Command... commands) {
    requireUngrouped(Arrays.asList(commands));
  }

  public static void requireUngrouped(Collection<Command> commands) {
    if (!Collections.disjoint(commands, getGroupedCommands())) {
      throw new IllegalArgumentException("Commands cannot be added to more than one CommandGroup");
    }
  }

  static Set<Command> getGroupedCommands() {
    return m_groupedCommands;
  }

  /**
   * Adds the given commands to the command group.
   * 
   * @param commands The commands to add.
   */
  public abstract void addCommands(Command... commands);

}
