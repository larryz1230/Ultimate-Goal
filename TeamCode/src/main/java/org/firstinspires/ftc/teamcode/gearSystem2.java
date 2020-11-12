package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.command.SubsystemBase;

public class gearSystem2 extends SubsystemBase {
    private final Motor leftMotor;
    private final Motor rightMotor;
    private ElapsedTime runtime = new ElapsedTime();

    public gearSystem2(final HardwareMap hMap, final String nameLeft, final String nameRight) {
        leftMotor = new Motor(hMap, nameLeft);
        rightMotor = new Motor(hMap, nameRight);
        leftMotor.setRunMode(Motor.RunMode.RawPower);
        rightMotor.setRunMode(Motor.RunMode.RawPower);
    }

    public void spedLeft() {
        leftMotor.set(1);
    }

    public void spedRight(){
        rightMotor.set(1);
    }

    public void stop() {
        leftMotor.set(0);
        rightMotor.set(0);
    }
}
