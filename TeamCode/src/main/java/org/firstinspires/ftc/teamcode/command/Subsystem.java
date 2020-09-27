package org.firstinspires.ftc.teamcode.command;

public interface Subsystem {
  /**
   * This command is called periodically by the {@link CommandScheduler}. Useful
   * for updating subsystem specific state that you dont want to offload to a
   * {@link Command}.
   */
  default void periodic() {
  }

  /**
   * Sets the default {@link Command} of the subsystem. The default command will
   * be auto scheduled when no other commands are scheduled that require the
   * subsystem.
   * 
   * @param defaultCommand the default command to associate with this subsystem
   */
  default void setDefaultCommand(Command defaultCommand) {
    CommandScheduler.getInstance().setDefaultCommand(this, defaultCommand);
  }

  /**
   * Gets the default command for this subsystem. Returns null if no default
   * command is currently associated with the subsystem.
   * 
   * @return the default command associated with this subsystem
   */
  default Command getDefaultCommand() {
    return CommandScheduler.getInstance().getDefaultCommand(this);
  }

  /**
   * Returns the current command currently running on this subsystem. Returns null
   * if no command is scheduled that requires this subsystem.
   * 
   * @return the scheduled command currently requiring this subsystem
   */
  default Command getCurrentCommand() {
    return CommandScheduler.getInstance().requiring(this);
  }

  /**
   * Registers this subsystem with the {@link CommandScheduler}, allowing its
   * periodic method to be called when the scheduler runs.
   */
  default void register() {
    CommandScheduler.getInstance().registerSubsystem(this);
  }

}
