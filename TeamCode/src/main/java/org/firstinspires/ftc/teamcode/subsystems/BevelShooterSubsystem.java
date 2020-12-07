package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BevelShooterSubsystem extends SubsystemBase {
    private final MotorSubsystem m_motor;
    private final MotorSubsystem m_reverse_motor;

    public BevelShooterSubsystem(HardwareMap hm, MotorEx m_motor, MotorEx m_reverse_motor) {
        this.m_motor = new MotorSubsystem(hm, m_motor, "velo");
        this.m_reverse_motor = new MotorSubsystem(hm, m_reverse_motor, "velo");
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
