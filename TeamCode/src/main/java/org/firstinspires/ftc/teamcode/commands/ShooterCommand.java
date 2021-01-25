package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;

import java.util.function.DoubleSupplier;

public class ShooterCommand extends CommandBase {
    private final BevelShooterSubsystem shooter;
    private final DoubleSupplier speed;

    public ShooterCommand(BevelShooterSubsystem subsystem, DoubleSupplier speed) {
        this.shooter = subsystem;
        this.speed = speed;
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
