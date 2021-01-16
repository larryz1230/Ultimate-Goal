package org.firstinspires.ftc.teamcode.experimental;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.experimental.NewBevelShooterSubsystem;

import java.util.function.DoubleSupplier;


public class NewSetShootPower extends CommandBase {
    private final NewBevelShooterSubsystem shooter;
    private final double speed;

    public NewSetShootPower(NewBevelShooterSubsystem subsystem, double speed) {
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
