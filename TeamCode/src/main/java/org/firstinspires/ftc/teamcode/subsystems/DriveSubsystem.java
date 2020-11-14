package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class DriveSubsystem extends SubsystemBase {
    private final MecanumDrive m_drive;

    private final MotorEx m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight;


    public DriveSubsystem(MotorEx leftMotorTop, MotorEx rightMotorTop, MotorEx leftMotorBottom, MotorEx rightMotorBottom){
        this.m_frontLeft = leftMotorTop;
        this.m_frontRight = rightMotorTop;
        this.m_bottomLeft = leftMotorBottom;
        this.m_bottomRight = rightMotorBottom;

        this.m_drive = new MecanumDrive(this.m_frontLeft, this.m_frontRight, this.m_bottomLeft, this.m_bottomRight);
    }

    public void drive(double hSpeed, double vSpeed, double gyroAngle){
        double tSpeed = (hSpeed+vSpeed)/2;
        m_drive.driveFieldCentric(hSpeed, vSpeed, tSpeed ,gyroAngle);
    }

}
