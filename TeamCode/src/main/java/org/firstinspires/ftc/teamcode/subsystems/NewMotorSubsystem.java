package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class NewMotorSubsystem extends SubsystemBase {
    private final MotorEx m_motor;

    public NewMotorSubsystem(final HardwareMap hm, MotorEx m_motor, String mode) {
        this.m_motor = m_motor;
        switch (mode){
            case "velo":
                m_motor.setRunMode(Motor.RunMode.VelocityControl);
                break;
            case "pos":
                m_motor.setRunMode(Motor.RunMode.PositionControl);
                break;
            default:
                m_motor.setRunMode(Motor.RunMode.RawPower);
                break;
        }
    }

    public void set(double p) {
        this.m_motor.set(p);
    }

    public void setVelo(double kP, double kI, double kD) {
        this.m_motor.setVeloCoefficients(kP, kI, kD);
    }

    public void setFF(double kS, double kV) {
        this.m_motor.setFeedforwardCoefficients(kS, kV);
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