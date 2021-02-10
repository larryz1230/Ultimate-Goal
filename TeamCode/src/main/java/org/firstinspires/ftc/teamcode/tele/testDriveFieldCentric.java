package org.firstinspires.ftc.teamcode.tele;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.commands.FieldCentricDriveComand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystemFieldCentric;
import org.firstinspires.ftc.teamcode.util.Encoder;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Field Centric Drive")
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

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();
        }


        schedule(driveCommand);
        register(driveSubsys);
    }
}
