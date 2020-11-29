package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.SetIntakePower;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp
public class IntakeTest extends CommandOpMode {
    private IntakeSubsystem intake;
    private SetIntakePower intakeCommand;

    @Override
    public void initialize() {
        intake = new IntakeSubsystem( new MotorEx(hardwareMap, "intake"));

        GamepadEx driverOp = new GamepadEx(gamepad1);
        intakeCommand = new SetIntakePower(intake, driverOp::getLeftY);

        schedule(intakeCommand);
        register(intake);
    }
}