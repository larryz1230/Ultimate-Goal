package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.SetIntakePower;
import org.firstinspires.ftc.teamcode.commands.SetShootPower;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;

@TeleOp
public class IntakeTest extends CommandOpMode {
  private IntakeSubsystem intake;
  private SetIntakePower intakeCommand;
  private BevelShooterSubsystem shooter;
  private SetShootPower shootCommand;

  @Override
  public void initialize() {
    intake = new IntakeSubsystem(new MotorEx(hardwareMap, "intake"));
    shooter = new BevelShooterSubsystem(new MotorEx(hardwareMap, "motor"), new MotorEx(hardwareMap, "inverted"));

    GamepadEx driverOp = new GamepadEx(gamepad1);
    intakeCommand = new SetIntakePower(intake, driverOp::getLeftY);
    shootCommand = new SetShootPower(shooter, driverOp::getRightY);

    schedule(intakeCommand, shootCommand);
    register(intake, shooter);
  }
}

