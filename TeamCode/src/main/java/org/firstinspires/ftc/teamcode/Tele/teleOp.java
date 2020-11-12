package org.firstinspires.ftc.teamcode.Tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.SetPower;
import org.firstinspires.ftc.teamcode.subsystems.MotorSubsystem;

@TeleOp
public class teleOp extends CommandOpMode {
    private MotorSubsystem shooter;
    private MotorSubsystem passthrough;

    @Override
    public void initialize() {
        shooter = new MotorSubsystem(hardwareMap, "shooter");
        passthrough = new MotorSubsystem(hardwareMap, "passthrough");

        GamepadEx driverOp = new GamepadEx(gamepad1);

        Button shooterStart = (new GamepadButton(driverOp, GamepadKeys.Button.Y))
                .whenPressed(new SetPower(shooter, 1));
        Button shooterEnd = (new GamepadButton(driverOp, GamepadKeys.Button.B))
                .whenPressed(new SetPower(shooter, 0));
        Button passthroughStart = (new GamepadButton(driverOp, GamepadKeys.Button.X))
                .whenPressed(new SetPower(passthrough, 1));
        Button passthroughEnd = (new GamepadButton(driverOp, GamepadKeys.Button.A))
                .whenPressed(new SetPower(passthrough, 0));

        register(shooter, passthrough);
    }
}
