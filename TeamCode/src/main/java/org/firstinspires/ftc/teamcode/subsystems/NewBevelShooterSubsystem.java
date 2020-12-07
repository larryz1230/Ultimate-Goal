package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class NewBevelShooterSubsystem extends SubsystemBase {
    private final NewMotorSubsystem m_motor;
    private final NewMotorSubsystem m_reverse_motor;

    public NewBevelShooterSubsystem(NewMotorSubsystem m_motor, NewMotorSubsystem m_reverse_motor) {
        this.m_motor = m_motor;
        this.m_reverse_motor = m_reverse_motor;
    }

    public void set(double p){
        m_motor.set(p);
        m_reverse_motor.set(p);
    }
}
