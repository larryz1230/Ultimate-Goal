package org.firstinspires.ftc.teamcode.Tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.commands.DefualtDrive;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Test Drive")
public class TeleOp extends CommandOpMode {

    private MotorEx m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight;
    private DriveSubsystem m_drive;
    private GamepadEx m_driveOp;
    private DefualtDrive m_driveCommand;

    @Override
    public void initialize() {
        m_frontLeft = new MotorEx(hardwareMap, "frontLeft");
        m_frontRight = new MotorEx(hardwareMap, "frontRight");
        m_bottomLeft = new MotorEx(hardwareMap, "bottomLeft");
        m_bottomRight = new MotorEx(hardwareMap, "bottomRight");
        m_drive = new DriveSubsystem(m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight);

        m_driveOp = new GamepadEx(gamepad1);
        m_driveCommand = new DefualtDrive(m_drive, m_driveOp.getLeftX(), m_driveOp.getLeftY(), hardwareMap.gyroSensor.get("BNO055IMU").getRotationFraction()); //How to get tSpeed and gyroAngle

        m_drive.setDefaultCommand(m_driveCommand);
        register(m_drive);
    }
}
