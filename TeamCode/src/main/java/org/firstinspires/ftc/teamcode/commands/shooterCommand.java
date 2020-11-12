package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.*;

public class shooterCommand extends CommandBase {
    private final shooterSubsystem shooter_subsystem;
    private final double motor_power;

    public shooterCommand(shooterSubsystem subsystem, double power) {
        this.shooter_subsystem = subsystem;
        this.motor_power= power;
        addRequirements(this.shooter_subsystem);
    }

    @Override
    public void initialize() {
        shooter_subsystem.setPower(this.motor_power);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
