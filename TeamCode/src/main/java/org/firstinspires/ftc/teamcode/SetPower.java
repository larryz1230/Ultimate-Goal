package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;


public class SetPower extends CommandBase {
    private final MotorSubsystem m_motorSubsystem;
    private final double k_power;

    public SetPower(MotorSubsystem subsystem, double power) {
        m_motorSubsystem = subsystem;
        k_power = power;
        addRequirements(m_motorSubsystem);
    }

    @Override
    public void initialize() {
        m_motorSubsystem.set(k_power);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
