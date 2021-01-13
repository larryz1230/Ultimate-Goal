package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystemFieldCentric;
import java.util.function.DoubleSupplier;

public class FieldCentricDriveComand extends CommandBase {
    private final DriveSubsystemFieldCentric m_drive;
    private final DoubleSupplier m_hSpeed;
    private final DoubleSupplier m_vSpeed;
    private final DoubleSupplier m_tSpeed;
    private final DoubleSupplier m_gyroAngle;

    //Field Centric
    public FieldCentricDriveComand(DriveSubsystemFieldCentric subsystem, DoubleSupplier hSpeed, DoubleSupplier vSpeed, DoubleSupplier tSpeed, DoubleSupplier gyro) {
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
