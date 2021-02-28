package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.GyroEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class DriveSubsystemRobotCentric extends SubsystemBase {
    private final MecanumDrive m_drive;

    private final MotorEx m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight;


    public DriveSubsystemRobotCentric(MotorEx leftMotorTop, MotorEx rightMotorTop, MotorEx leftMotorBottom, MotorEx rightMotorBottom) {
        this.m_frontLeft = leftMotorTop;
        this.m_frontRight = rightMotorTop;
        this.m_bottomLeft = leftMotorBottom;
        this.m_bottomRight = rightMotorBottom;

        this.m_frontLeft.setRunMode(Motor.RunMode.RawPower);
        this.m_frontRight.setRunMode(Motor.RunMode.RawPower);
        this.m_bottomLeft.setRunMode(Motor.RunMode.RawPower);
        this.m_bottomRight.setRunMode(Motor.RunMode.RawPower);

        this.m_drive = new MecanumDrive(
                this.m_frontLeft,
                this.m_frontRight,
                this.m_bottomLeft,
                this.m_bottomRight
        );
        m_drive.setMaxSpeed(1);
    }

    public void drive(double hSpeed, double vSpeed, double tSpeed){
        m_drive.driveRobotCentric(
                hSpeed,
                vSpeed,
                tSpeed
        );
    }
}
