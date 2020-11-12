package org.firstinspires.ftc.teamcode.Tele;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Teleop")
public class teleOp extends CommandOpMode {
    private shooterSubsystem shooter_one;
    private shooterSubsystem shooter_two;

    @Override
    public void initialize() {
        shooter_one = new shooterSubsystem(hardwareMap, "test_shooter_one");
        shooter_two = new shooterSubsystem(hardwareMap, "test_shooter_two");
        GamepadEx shooter_driver = new GamepadEx(gamepad1);

        Button shooterOneStart = (new GamepadButton(shooter_driver, GamepadKeys.Button.Y))
                .whenPressed(new shooterCommand(shooter_one, 1));
        Button shooterOneEnd = (new GamepadButton(shooter_driver, GamepadKeys.Button.B))
                .whenPressed(new shooterCommand(shooter_one, 0));
        Button shooterTwoStart = (new GamepadButton(shooter_driver, GamepadKeys.Button.Y))
                .whenPressed(new shooterCommand(shooter_two, 1));
        Button shooterTwoEnd = (new GamepadButton(shooter_driver, GamepadKeys.Button.Y))
                .whenPressed(new shooterCommand(shooter_two, 0));

        register(shooter_one, shooter_two);
    }
}
