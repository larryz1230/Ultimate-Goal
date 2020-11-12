package org.firstinspires.ftc.teamcode.Auto;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.command.SubsystemBase;

public class gearSubsystem extends SubsystemBase {
    private final Motor leftMotor;
    private final Motor rightMotor;
    private ElapsedTime runtime = new ElapsedTime();

    public gearSubsystem(final HardwareMap hMap, final String nameLeft, final String nameRight) {
        leftMotor = new Motor(hMap, nameLeft);
        rightMotor = new Motor(hMap, nameRight);
        leftMotor.setRunMode(Motor.RunMode.RawPower);
        rightMotor.setRunMode(Motor.RunMode.RawPower);
    }

    public void sped() {
        leftMotor.set(1);
        rightMotor.set(1);
    }

    public void stop() {
        leftMotor.set(0);
        rightMotor.set(0);
    }
}
