package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class IntakeSubsystem extends SubsystemBase {
    private final MotorEx m_motor;

    public IntakeSubsystem(MotorEx m_motor) {
        this.m_motor = m_motor;

        m_motor.setRunMode(Motor.RunMode.RawPower);
    }

    public void set(double p) {
        m_motor.set(p);
    }

}
