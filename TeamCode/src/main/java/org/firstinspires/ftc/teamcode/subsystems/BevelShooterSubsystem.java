package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BevelShooterSubsystem extends SubsystemBase {
    private final MotorEx m_motor;
    private final MotorEx m_reverse_motor;

    public BevelShooterSubsystem(MotorEx m_motor, MotorEx m_reverse_motor) {
        this.m_motor = m_motor;
        this.m_reverse_motor = m_reverse_motor;

        m_motor.setRunMode(Motor.RunMode.RawPower);
        m_reverse_motor.setRunMode(Motor.RunMode.RawPower);
        //m_reverse_motor.setInverted(true);
    }

    public void set(double p) {
        m_motor.set(p);
        m_reverse_motor.set(p);
    }


}
