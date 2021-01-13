package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.experimental.NewBevelShooterSubsystem;

import java.util.function.DoubleSupplier;


public class NewSetShootPower extends CommandBase {
    private final NewBevelShooterSubsystem shooter;
    private final double speed;

    public NewSetShootPower(NewBevelShooterSubsystem subsystem, DoubleSupplier speed) {
        this.shooter = subsystem;
        this.speed = speed.getAsDouble();
        addRequirements(shooter);
    }

    public NewSetShootPower(NewBevelShooterSubsystem subsystem, double speed) {
        this.shooter = subsystem;
        this.speed = speed;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
       shooter.set(speed);
    }
}
