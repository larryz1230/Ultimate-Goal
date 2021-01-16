package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BevelShooterSubsystem extends SubsystemBase {
    private final MotorEx m_motor;
    private final MotorEx m_reverse_motor;
    private final MotorEx m_intake;

    public BevelShooterSubsystem(MotorEx m_motor, MotorEx m_reverse_motor, MotorEx m_intake) {
        this.m_motor = m_motor;
        this.m_reverse_motor = m_reverse_motor;
        this.m_intake = m_intake;
//        m_motor.setRunMode(Motor.RunMode.VelocityControl);
//        m_reverse_motor.setRunMode(Motor.RunMode.VelocityControl);
//        m_intake.setRunMode(Motor.RunMode.VelocityControl);
        m_motor.setRunMode(Motor.RunMode.RawPower);
        m_reverse_motor.setRunMode(Motor.RunMode.RawPower);
        m_intake.setRunMode(Motor.RunMode.RawPower);
        m_intake.setInverted(true);
    }


    public void set(double p) {
//        this.m_motor.resetEncoder();
//        this.m_reverse_motor.resetEncoder();
//        this.m_intake.resetEncoder();
        this.m_motor.set(p);
        this.m_reverse_motor.set(p);
        this.m_intake.set(p);
    }

    public void setVelo(double kP, double kI, double kD) {
        this.m_motor.setVeloCoefficients(kP, kI, kD);
        this.m_reverse_motor.setVeloCoefficients(kP, kI, kD);
    }

    public void setFF(double kS, double kV) {
        this.m_motor.setFeedforwardCoefficients(kS, kV);
        this.m_reverse_motor.setFeedforwardCoefficients(kS, kV);
    }

    public double[] getVeloCoff(){
        return this.m_motor.getVeloCoefficients();
    }

    public double[] getFFCoff(){
        return this.m_motor.getFeedforwardCoefficients();
    }

    public double getCurrentVelocity(){
        return this.m_motor.getVelocity();
    }

}
