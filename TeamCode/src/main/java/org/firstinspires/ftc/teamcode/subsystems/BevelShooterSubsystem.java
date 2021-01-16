package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BevelShooterSubsystem extends SubsystemBase {
    private Motor m_motor;
    private Motor m_reverse_motor;
    private MotorEx m_intake;

    private final double kP = 30.00;
    private final double kI = 0.00;
    private final double kD = 7.00;
    private final double kS = 0.10;
    private final double kV = 0.0003;

    public BevelShooterSubsystem(Motor m_motor, Motor m_reverse_motor, MotorEx m_intake) {
        this.m_motor = m_motor;
        this.m_reverse_motor = m_reverse_motor;
        this.m_intake = m_intake;

        this.m_motor.setRunMode(Motor.RunMode.RawPower);
        this.m_reverse_motor.setRunMode(Motor.RunMode.RawPower);
        this.m_intake.setRunMode(Motor.RunMode.RawPower);
        this.m_intake.setInverted(true);

        this.m_motor.setVeloCoefficients(kP, kI, kD);
        this.m_reverse_motor.setVeloCoefficients(kP, kI, kD);

        this.m_motor.setFeedforwardCoefficients(kS, kV);
        this.m_reverse_motor.setFeedforwardCoefficients(kS, kV);
    }

    public void shootAt() {
        m_motor.set(0.7);
        m_reverse_motor.set(0.7);
    }

    public void runIntake(){
        m_intake.set(1);
    }

    public void stop(){
        m_motor.stopMotor();
        m_reverse_motor.stopMotor();
        m_intake.stopMotor();
    }
}
