package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;

public class SetIntakePower extends CommandBase {
    private final IntakeSubsystem intake;
    private final DoubleSupplier speed;

    public SetIntakePower(IntakeSubsystem subsystem, DoubleSupplier speed) {
        intake = subsystem;
        this.speed = speed;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.set(speed.getAsDouble());
    }
}
