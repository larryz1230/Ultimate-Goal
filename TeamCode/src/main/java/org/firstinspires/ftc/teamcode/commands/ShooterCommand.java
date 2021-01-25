package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;

public class ShooterCommand extends CommandBase {
    private final BevelShooterSubsystem shooter;

    public ShooterCommand(BevelShooterSubsystem subsystem) {
        this.shooter = subsystem;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.runIntake();
    }

    public void stopShooter(){
        shooter.stop();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}