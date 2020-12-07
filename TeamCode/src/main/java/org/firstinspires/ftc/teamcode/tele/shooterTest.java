package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.commands.DefualtDrive;
import org.firstinspires.ftc.teamcode.commands.SetPower;
import org.firstinspires.ftc.teamcode.commands.SetShootPower;
import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MotorSubsystem;

import java.time.Instant;

@TeleOp
public class shooterTest extends CommandOpMode {
    private BevelShooterSubsystem shooter;
    private SetShootPower shootCommand;

    @Override
    public void initialize() {
        shooter = new BevelShooterSubsystem(new MotorEx(hardwareMap, "motor"), new MotorEx(hardwareMap, "inverted"));

        GamepadEx driverOp = new GamepadEx(gamepad1);
        shootCommand = new SetShootPower(shooter, driverOp::getLeftY);

        schedule(shootCommand);
        register(shooter);
    }
}

