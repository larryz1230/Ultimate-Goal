package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DefualtDrive extends CommandBase {
    private final DriveSubsystem m_drive;
    private final DoubleSupplier m_hSpeed;
    private final DoubleSupplier m_vSpeed;
    private final DoubleSupplier m_tSpeed;
    private final DoubleSupplier m_gyroAngle;
//    private final double m_hSpeed;
//    private final double m_vSpeed;
//    private final double m_gyroAngle;

    public DefualtDrive(DriveSubsystem subsystem, DoubleSupplier hSpeed, DoubleSupplier vSpeed, DoubleSupplier tSpeed, DoubleSupplier gyro) {
        this.m_drive = subsystem;
        this.m_hSpeed = hSpeed;
        this.m_vSpeed = vSpeed;
        this.m_tSpeed = tSpeed;
        this.m_gyroAngle = gyro;
        addRequirements(this.m_drive);
    }

    @Override
    public void execute() {
        m_drive.drive(
                this.m_hSpeed.getAsDouble(),
                this.m_vSpeed.getAsDouble(),
                this.m_tSpeed.getAsDouble(),
                this.m_gyroAngle.getAsDouble()
        );
    }
}
