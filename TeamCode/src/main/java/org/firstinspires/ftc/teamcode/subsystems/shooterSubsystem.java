package org.firstinspires.ftc.teamcode.subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class shooterSubsystem extends SubsystemBase {
    private final Motor m_motor;

    public shooterSubsystem(final HardwareMap hMap, final String motorName) {
        m_motor = new Motor(hMap, motorName);
        m_motor.setRunMode(Motor.RunMode.RawPower);
    }

    public void setPower(double power){
        m_motor.set(power);
    }
}
