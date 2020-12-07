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

@Config
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Motor Velocity PID")
public class motorPIDTest extends CommandOpMode {
  private BevelShooterSubsystem shooter;
  private SetShootPower shootCommand;
  private double currentVelocity;
  private double maximumVelocity = 0.0;

  @Override
  public void initialize() {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    MotorEx m_motor = new MotorEx(hardwareMap, "motor");
    MotorEx m_reverse_motor = new MotorEx(hardwareMap, "inverted");
    shooter = new BevelShooterSubsystem(hardwareMap, m_motor, m_reverse_motor);

    GamepadEx driverOp = new GamepadEx(gamepad1);
    GamepadButton flywheelShoot = new GamepadButton(driverOp, GamepadKeys.Button.A);
    ButtonReader reader = new ButtonReader(driverOp, GamepadKeys.Button.A);

    if(reader.isDown()){
      shootCommand = new SetShootPower(shooter, 1);
      currentVelocity = shooter.getCurrentVelocityMotor();
    }

    if(currentVelocity > maximumVelocity){
      maximumVelocity = currentVelocity;
    }

    dashboardTelemetry.addData("current velocity", currentVelocity);
    dashboardTelemetry.addData("maximum velocity", maximumVelocity);

    schedule(shootCommand);
    register(shooter);
  }
}
