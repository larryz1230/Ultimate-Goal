package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class teleop extends LinearOpMode {
    @Override
    public void runOpMode(){
        waitForStart();
        gearSystem2 gs = new gearSystem2(hardwareMap, "leftMotor", "rightMotor");
        GamepadEx gamepadEx = new GamepadEx(gamepad1);
        TriggerReader triggerReaderRight = new TriggerReader(
                gamepadEx, GamepadKeys.Trigger.RIGHT_TRIGGER
        );

        TriggerReader triggerReaderLeft = new TriggerReader(
                gamepadEx, GamepadKeys.Trigger.LEFT_TRIGGER
        );

        while(opModeIsActive()){
            while(triggerReaderLeft.isDown()){
                gs.spedLeft();
            }
            while(triggerReaderLeft.isDown()){
                gs.spedRight();
            }
        }


    }
}
