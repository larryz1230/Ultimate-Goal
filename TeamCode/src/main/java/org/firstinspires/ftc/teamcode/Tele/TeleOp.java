package org.firstinspires.ftc.teamcode.Tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.commands.DefualtDrive;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Test Drive")
public class TeleOp extends CommandOpMode {

    private MotorEx m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight;
    private DriveSubsystem driveSubsys;
    private GamepadEx driverController;
    private DefualtDrive driveCommand;

    @Override
    public void initialize() {
        m_frontLeft = new MotorEx(hardwareMap, "frontLeft");
        m_frontRight = new MotorEx(hardwareMap, "frontRight");
        m_bottomLeft = new MotorEx(hardwareMap, "bottomLeft");
        m_bottomRight = new MotorEx(hardwareMap, "bottomRight");
        driveSubsys = new DriveSubsystem(
                m_frontLeft,
                m_frontRight,
                m_bottomLeft,
                m_bottomRight
        );

        driverController = new GamepadEx(gamepad1);
        driveCommand = new DefualtDrive(
                driveSubsys,
                () -> driverController.getLeftX(),
                () -> driverController.getLeftY(),
                () -> hardwareMap.gyroSensor.get("BNO055IMU").getRotationFraction()
        );

        driveSubsys.setDefaultCommand(driveCommand);
        register(driveSubsys);
    }
}
