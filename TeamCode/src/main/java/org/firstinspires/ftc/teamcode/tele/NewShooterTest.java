package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.commands.DefualtDriveComand;
import org.firstinspires.ftc.teamcode.commands.NewSetShootPower;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.NewBevelShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.NewMotorSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Shooter and Drive Test")
public class NewShooterTest extends CommandOpMode {

    private MotorEx m_frontLeft, m_frontRight, m_bottomLeft, m_bottomRight;
    private RevIMU gyro;
    private DriveSubsystem driveSubsys;
    private GamepadEx driverController;
    private DefualtDriveComand driveCommand;
    private NewBevelShooterSubsystem shooter;
    private NewSetShootPower shootCommand;
    private NewMotorSubsystem m_shooterMotor;
    private NewMotorSubsystem m_reverse_shooterMotor;

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

        m_shooterMotor = new NewMotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "motor"), "velo");
        m_reverse_shooterMotor = new NewMotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "inverted"), "velo");

        gyro = new RevIMU(hardwareMap, "imu");
        gyro.init();

        m_shooterMotor.setVelo(kP, kI, kD);
        m_shooterMotor.setFF(kS, kV);
        m_reverse_shooterMotor.setVelo(kP, kI, kD);
        m_reverse_shooterMotor.setFF(kS, kV);

        shooter = new NewBevelShooterSubsystem(m_shooterMotor, m_reverse_shooterMotor);
        driveSubsys = new DriveSubsystem(
                m_frontLeft,
                m_frontRight,
                m_bottomLeft,
                m_bottomRight,
                gyro
        );

        driverController = new GamepadEx(gamepad1);
        ButtonReader buttonAreader = new ButtonReader(
                driverController, GamepadKeys.Button.A
        );

        driveCommand = new DefualtDriveComand(
                driveSubsys,
                () -> driverController.getLeftX(),
                () -> driverController.getLeftY(),
                () -> -driverController.getRightX(),
                () -> gyro.getHeading()
        );

        if(buttonAreader.isDown()){
            shootCommand = new NewSetShootPower(shooter, power);
        }

        schedule(driveCommand);
        schedule(shootCommand);
        register(driveSubsys);
        register(shooter);
        register(m_shooterMotor);
        register(m_reverse_shooterMotor);
    }
}

