package org.firstinspires.ftc.teamcode.Auto;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Auto.vision.TowerGoalDetector;
import org.firstinspires.ftc.teamcode.Auto.vision.StackDetector;
import org.opencv.core.Rect;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Test Vision / bad PID")
public abstract class Autonomous extends LinearOpMode {

    public static DcMotor topLeft;
    public static DcMotor topRight;
    public static DcMotor bottomLeft;
    public static DcMotor bottomRight;
    public static DcMotor leftEncoder;
    public static DcMotor rightEncoder;
    public static DcMotor rearEncoder;
    public static OpenCvWebcam webcam;
    public BNO055IMU imu;

    public void initHardware() {
        initImu();
        topLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        topRight = hardwareMap.get(DcMotor.class, "frontRight");
        bottomLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        bottomRight = hardwareMap.get(DcMotor.class, "rearRight");
        leftEncoder = hardwareMap.get(DcMotor.class, "leftEncoder");
        rightEncoder = hardwareMap.get(DcMotor.class, "rightEncoder");
        rearEncoder = hardwareMap.get(DcMotor.class, "rearEncoder");

        topRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        topLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //might need to reverse some motors

        topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        resetMotors();



        telemetry.addData("Ready...", "Press Start");
        telemetry.update();
        waitForStart();
    }

    public void initImu() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        telemetry.addData("Status: ", "Calibrating");
        telemetry.update();
        imu.initialize(parameters);

        telemetry.addData("Status: ", "Imu Calibration Ready");
        telemetry.update();
    }

    public void resetMotors(){
        topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void donut_detector(){
        int cameraMonitorViewId = hardwareMap
                .appContext
                .getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory
                .getInstance()
                .createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.openCameraDevice();
        StackDetector detector = new StackDetector();
        webcam.setPipeline(detector); // Adding detector to camera stream
        webcam.openCameraDeviceAsync(
                () -> webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT) //These numbers are not real, find out real ones
        );

        waitForStart();
        webcam.stopStreaming();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //Call the thread for calculating odom
        //move motors here
        //comment
        donut_detector();
        //initHardware();
        double motorPower = 0;
    }

    public void findAngle(OpenCvWebcam cam){
        TowerGoalDetector pcd = new TowerGoalDetector();
        webcam.setPipeline(pcd);
    }

}
