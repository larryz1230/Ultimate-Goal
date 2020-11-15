package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DefualtDrive extends CommandBase {
    private final DriveSubsystem m_drive;
    private final DoubleSupplier m_hSpeed;
    private final DoubleSupplier m_vSpeed;
//    private final double m_hSpeed;
//    private final double m_vSpeed;
//    private final double m_gyroAngle;

    public DefualtDrive(DriveSubsystem subsystem, DoubleSupplier hSpeed, DoubleSupplier vSpeed) {
        this.m_drive = subsystem;
        this.m_hSpeed = hSpeed;
        this.m_vSpeed = vSpeed;
        addRequirements(this.m_drive);
    }

    @Override
    public void execute() {
        m_drive.drive(
                this.m_hSpeed.getAsDouble(),
                this.m_vSpeed.getAsDouble()
        );
    }
}
