package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MotorSubsystem;

import java.util.function.DoubleSupplier;


public class SetShootPower extends CommandBase {
    private final BevelShooterSubsystem shooter;
    private final double speed;

    public SetShootPower(BevelShooterSubsystem subsystem, DoubleSupplier speed) {
        this.shooter = subsystem;
        this.speed = speed.getAsDouble();
        addRequirements(shooter);
    }

    public SetShootPower(BevelShooterSubsystem subsystem, double speed) {
        this.shooter = subsystem;
        this.speed = speed;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
       shooter.set(speed);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
