package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.commands.FieldCentricDriveComand;
import org.firstinspires.ftc.teamcode.commands.SequentialShootCommand;
import org.firstinspires.ftc.teamcode.commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystemFieldCentric;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Test this shooter!!! and also everything else")
public class ShooterTest extends CommandOpMode {

    private MotorEx m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight;
    private Motor m_shooterMotor, m_reverse_shooterMotor;
    private MotorEx m_intake;
    private RevIMU gyro;
    private DriveSubsystemFieldCentric driveSubsys;
    private BevelShooterSubsystem shooter;
    private GamepadEx driverController;
    private ShooterCommand shooterCommand;
    private SequentialShootCommand shootCommandGroup;
    private InstantCommand runShooterCommand;
    private FieldCentricDriveComand driveCommand;

    @Override
    public void initialize() {
        m_frontLeft = new MotorEx(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435);
        m_frontRight = new MotorEx(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435);
        m_bottomLeft = new MotorEx(hardwareMap, "bottomLeft", Motor.GoBILDA.RPM_435);
        m_bottomRight = new MotorEx(hardwareMap, "bottomRight", Motor.GoBILDA.RPM_435);

        m_shooterMotor = new MotorEx(hardwareMap, "motor", Motor.GoBILDA.RPM_435);
        m_reverse_shooterMotor = new MotorEx(hardwareMap, "inverted", Motor.GoBILDA.RPM_435);
        m_intake = new MotorEx(hardwareMap, "intake", Motor.GoBILDA.RPM_435);


        gyro = new RevIMU(hardwareMap, "imu");
        gyro.init();

        driveSubsys = new DriveSubsystemFieldCentric(
                m_frontLeft,
                m_frontRight,
                m_bottomLeft,
                m_bottomRight,
                gyro
        );

        driveCommand = new FieldCentricDriveComand(
                driveSubsys,
                () -> driverController.getLeftX(),
                () -> driverController.getLeftY(),
                () -> -driverController.getRightX(),
                () -> gyro.getHeading()
        );

        shooter = new BevelShooterSubsystem(m_shooterMotor, m_reverse_shooterMotor, m_intake);
        shooterCommand = new ShooterCommand(shooter);
        runShooterCommand = new InstantCommand(shooter::shootAt, shooter);
        shootCommandGroup = new SequentialShootCommand(runShooterCommand, shooterCommand);

        driverController = new GamepadEx(gamepad1);
        GamepadButton buttonB = new GamepadButton(
                driverController, GamepadKeys.Button.B
        );
        buttonB.whileHeld(shootCommandGroup);

        register(driveSubsys, shooter);
        driveSubsys.setDefaultCommand(driveCommand);
    }
}

