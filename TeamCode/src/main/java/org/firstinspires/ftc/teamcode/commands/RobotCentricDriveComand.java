package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystemFieldCentric;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystemRobotCentric;

import java.util.function.DoubleSupplier;

public class RobotCentricDriveComand extends CommandBase {
    private final DriveSubsystemRobotCentric m_drive;
    private final DoubleSupplier m_hSpeed;
    private final DoubleSupplier m_vSpeed;
    private final DoubleSupplier m_tSpeed;

    //Field Centric
    public RobotCentricDriveComand(DriveSubsystemRobotCentric subsystem, DoubleSupplier hSpeed, DoubleSupplier vSpeed, DoubleSupplier tSpeed) {
        this.m_drive = subsystem;
        this.m_hSpeed = hSpeed;
        this.m_vSpeed = vSpeed;
        this.m_tSpeed = tSpeed;
        addRequirements(this.m_drive);
    }

    @Override
    public void execute() {
        m_drive.drive(
                this.m_hSpeed.getAsDouble(),
                this.m_vSpeed.getAsDouble(),
                this.m_tSpeed.getAsDouble()
        );
    }
}
