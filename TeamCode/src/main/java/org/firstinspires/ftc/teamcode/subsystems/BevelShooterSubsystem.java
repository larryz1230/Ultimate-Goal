package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BevelShooterSubsystem extends SubsystemBase {
    private final MotorEx m_motor;
    private final MotorEx m_reverse_motor;
    private final MotorEx m_intake;

    private final double kP = 30.00;
    private final double kI = 0.00;
    private final double kD = 7.00;
    private final double kS = 0.10;
    private final double kV = 0.0003;

    public BevelShooterSubsystem(MotorEx m_motor, MotorEx m_reverse_motor, MotorEx m_intake) {
        this.m_motor = m_motor;
        this.m_reverse_motor = m_reverse_motor;
        this.m_intake = m_intake;

        m_motor.setVeloCoefficients(kP, kI, kD);
        m_reverse_motor.setVeloCoefficients(kP, kI, kD);
        m_motor.setFeedforwardCoefficients(kS, kV);
        m_reverse_motor.setFeedforwardCoefficients(kS, kV);
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
}
