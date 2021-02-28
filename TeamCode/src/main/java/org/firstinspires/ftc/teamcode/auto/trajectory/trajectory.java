package org.firstinspires.ftc.teamcode.auto.trajectory;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class trajectory extends LinearOpMode {
    @Override
    public void runOpMode(){
        sample_tra_1();
    }

    public void sample_tra_1(){

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(5, 8), 0)
                .splineTo(new Vector2d(9, 8), 0)
                .build();

        waitForStart();
        if(isStopRequested()) return;

        drive.followTrajectory(myTrajectory);
    }
}
