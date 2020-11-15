package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.SetPower;
import org.firstinspires.ftc.teamcode.subsystems.MotorSubsystem;

@TeleOp
public class testMotor extends CommandOpMode {
    private MotorSubsystem shooter;
    private MotorSubsystem passthrough;
    private MotorSubsystem grabber;
    private MotorSubsystem lift;

    @Override
    public void initialize() {
        shooter = new MotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435));
        passthrough = new MotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435));
        grabber = new MotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "bottomLeft", Motor.GoBILDA.RPM_435));
        lift = new MotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "bottomRight", Motor.GoBILDA.RPM_435));

        GamepadEx driverOp = new GamepadEx(gamepad1);

        Button shooterStart = (new GamepadButton(driverOp, GamepadKeys.Button.Y))
                .whenPressed(new SetPower(shooter, -1));
        Button shooterEnd = (new GamepadButton(driverOp, GamepadKeys.Button.B))
                .whenPressed(new SetPower(passthrough, 1));
        Button passthroughStart = (new GamepadButton(driverOp, GamepadKeys.Button.X))
                .whenPressed(new SetPower(grabber, -1));
        Button passthroughEnd = (new GamepadButton(driverOp, GamepadKeys.Button.A))
                .whenPressed(new SetPower(lift, 1));

        register(shooter, passthrough);
    }
}
