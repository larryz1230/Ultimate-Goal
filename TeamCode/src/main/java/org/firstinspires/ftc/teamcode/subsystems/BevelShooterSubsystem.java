package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BevelShooterSubsystem extends SubsystemBase {
    private final MotorSubsystem m_motor;
    private final MotorSubsystem m_reverse_motor;

    public BevelShooterSubsystem(HardwareMap hm, MotorSubsystem m_motor, MotorSubsystem m_reverse_motor) {
        this.m_motor = m_motor;
        this.m_reverse_motor = m_reverse_motor;
    }

    public void set(double p){
        m_motor.set(p);
        m_reverse_motor.set(p);
    }

    public double getCurrentVelocityMotor(){
        return m_motor.getCurrentVelocity();
    }

    public double getCurrentVelocityReversed(){
        return m_reverse_motor.getCurrentVelocity();
    }
}
