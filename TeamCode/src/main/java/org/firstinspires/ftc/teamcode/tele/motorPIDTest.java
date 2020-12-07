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
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Motor Velocity PID")
public class motorPIDTest extends CommandOpMode {
  private BevelShooterSubsystem shooter;
  private SetShootPower shootCommand;
  static double kP = 0;
  static double kI = 0;
  static double kD = 0;
  static double kS = 0;
  static double kV = 0;


  @Override
  public void initialize() {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    MotorSubsystem m_motor = new MotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "motor"), "velo");
    MotorSubsystem m_reverse_motor = new MotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "inverted"), "velo");
    shooter = new BevelShooterSubsystem(hardwareMap, m_motor, m_reverse_motor);

    m_motor.setVelo(kP, kI, kD);
    m_motor.setFF(kS, kV);
    m_reverse_motor.setVelo(kP, kI, kD);
    m_reverse_motor.setFF(kS, kV);
    
    m_motor.set(0.5);
    m_reverse_motor.set(0.5);

    dashboardTelemetry.addData("current velocity Motor:", m_motor::getCurrentVelocity);
    dashboardTelemetry.addData("current velocity Inverse: ", m_reverse_motor::getCurrentVelocity);

    schedule(shootCommand);
    register(shooter);
  }
}
