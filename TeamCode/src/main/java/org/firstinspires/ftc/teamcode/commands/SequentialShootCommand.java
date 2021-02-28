package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

public class SequentialShootCommand extends SequentialCommandGroup {
    private ShooterCommand shootCommand;

    public SequentialShootCommand(InstantCommand runShooter, ShooterCommand shootCommand){
        this.shootCommand = shootCommand;
        addCommands(runShooter, shootCommand);
    }

    @Override
    public void end(boolean interrupted){
       shootCommand.stopShooter();
    }
}
