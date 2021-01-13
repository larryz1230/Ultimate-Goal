package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.commands.FieldCentricDriveComand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystemFieldCentric;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Test Drive")
public class testDriveFieldCentric extends CommandOpMode {

    private MotorEx m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight;
    private RevIMU gyro;
    private DriveSubsystemFieldCentric driveSubsys;
    private GamepadEx driverController;
    private FieldCentricDriveComand driveCommand;

    @Override
    public void initialize() {
        m_frontLeft = new MotorEx(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435);
        m_frontRight = new MotorEx(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435);
        m_bottomLeft = new MotorEx(hardwareMap, "bottomLeft", Motor.GoBILDA.RPM_435);
        m_bottomRight = new MotorEx(hardwareMap, "bottomRight", Motor.GoBILDA.RPM_435);
        gyro = new RevIMU(hardwareMap, "imu");
        gyro.init();
        driveSubsys = new DriveSubsystemFieldCentric(
                m_frontLeft,
                m_frontRight,
                m_bottomLeft,
                m_bottomRight,
                gyro
        );

        driverController = new GamepadEx(gamepad1);
        driveCommand = new FieldCentricDriveComand(
                driveSubsys,
                () -> driverController.getLeftX(),
                () -> driverController.getLeftY(),
                () -> -driverController.getRightX(),
                () -> gyro.getHeading()
        );


        schedule(driveCommand);
        register(driveSubsys);
    }
}
