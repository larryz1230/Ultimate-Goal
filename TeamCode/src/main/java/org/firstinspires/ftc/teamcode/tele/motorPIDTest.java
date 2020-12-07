package org.firstinspires.ftc.teamcode.tele;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.SetShootPower;
import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MotorSubsystem;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Motor Velocity PID")
public class motorPIDTest extends CommandOpMode {
  private BevelShooterSubsystem shooter;
  private SetShootPower shootCommand;
  public static double kP = 0;
  public static double kI = 0;
  public static double kD = 0;
  public static double kS = 0;
  public static double kV = 0;


  @Override
  public void initialize() {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    shooter = new BevelShooterSubsystem(new MotorEx(hardwareMap, "motor"), new MotorEx(hardwareMap, "inverted"));
    shooter.setVelo(kP, kI, kD);
    shooter.setFF(kS, kV);
    shootCommand = new SetShootPower(shooter, 0.5);

    dashboardTelemetry.addData("current velocity Motor:", shooter::getCurrentVelocity);

    schedule(shootCommand);
    register(shooter);
  }
}
