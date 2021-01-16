package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.FieldCentricDriveComand;
import org.firstinspires.ftc.teamcode.commands.SetShootPower;
import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystemFieldCentric;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "old Shooter and Drive Test")
public class ShooterTest extends CommandOpMode {

    private MotorEx m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight, m_shooterMotor, m_reverse_shooterMotor, m_intake;
    private RevIMU gyro;
    private DriveSubsystemFieldCentric driveSubsys;
    private GamepadEx driverController;
    private FieldCentricDriveComand driveCommand;
    private BevelShooterSubsystem shooter;

    static double kP = 30.00;
    static double kI = 0.00;
    static double kD = 7.00;
    static double kS = 0.10;
    static double kV = 0.0003;
    static double power = 0.5;


    @Override
    public void initialize() {
        m_frontLeft = new MotorEx(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435);
        m_frontRight = new MotorEx(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435);
        m_bottomLeft = new MotorEx(hardwareMap, "bottomLeft", Motor.GoBILDA.RPM_435);
        m_bottomRight = new MotorEx(hardwareMap, "bottomRight", Motor.GoBILDA.RPM_435);

        m_shooterMotor = new MotorEx(hardwareMap, "motor", Motor.GoBILDA.RPM_435);
        m_reverse_shooterMotor = new MotorEx(hardwareMap, "inverted", Motor.GoBILDA.RPM_435);
        m_intake = new MotorEx(hardwareMap, "intake", Motor.GoBILDA.RPM_435);
        shooter = new BevelShooterSubsystem(m_shooterMotor, m_reverse_shooterMotor, m_intake);

        gyro = new RevIMU(hardwareMap, "imu");
        gyro.init();

        shooter.setVelo(kP, kI, kD);
        shooter.setFF(kS, kV);
        telemetry.addData("m_shooter kP: ", m_shooterMotor.getVeloCoefficients()[0]);
        telemetry.update();

        driveSubsys = new DriveSubsystemFieldCentric(
                m_frontLeft,
                m_frontRight,
                m_bottomLeft,
                m_bottomRight,
                gyro
        );

        driverController = new GamepadEx(gamepad1);
        GamepadButton buttonB = new GamepadButton(
                driverController, GamepadKeys.Button.B
        );

        driveCommand = new FieldCentricDriveComand(
                driveSubsys,
                () -> driverController.getLeftX(),
                () -> driverController.getLeftY(),
                () -> -driverController.getRightX(),
                () -> gyro.getHeading()
        );

        buttonB.whenPressed(new SetShootPower(shooter, power))
               .whenReleased(new SetShootPower(shooter, 0));

        schedule(driveCommand);
        register(driveSubsys, shooter);
    }
}

