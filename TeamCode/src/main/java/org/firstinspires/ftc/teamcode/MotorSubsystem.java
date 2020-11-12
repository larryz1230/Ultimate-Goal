package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorSubsystem extends SubsystemBase {
    private final Motor m_motor;

    public MotorSubsystem(final HardwareMap hm, final String name) {
        m_motor = new Motor(hm, name);

        m_motor.setRunMode(Motor.RunMode.RawPower);
    }

    public void set(double p) {
        m_motor.set(p);
    }


}
