package org.firstinspires.ftc.teamcode.vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

enum stack_pos {
  NONE, ONE, FOUR,
}

public class StackDetector extends OpenCvPipeline {
  private final double pass = 0.25;
  private final Scalar defualt = new Scalar(50, 50, 50);
  private final Scalar one = new Scalar(255, 0, 0); // Red
  private final Scalar four = new Scalar(0, 255, 0); //Green
  private final Scalar zero = new Scalar(0, 0, 255); // Blue
  private final Scalar lowHSV = new Scalar(10, 130, 85); //Hue, Saturation, Value
  private final Scalar highHSV = new Scalar(180, 255, 255);
  private final Rect secondRect = new Rect(
          new Point(100, 200),
          new Point(180, 230)
  );
  private final Rect firstRect = new Rect(
          new Point(100, 230),
          new Point(180, 260)
  );
  Mat region1_Cb, region2_Cb;
  Mat threshHolded = new Mat();
  Telemetry dashboardTele;

  double percentage_top, percentage_bottom;
  private stack_pos stackPos = stack_pos.NONE;


  public StackDetector(Telemetry dashboardTele){
      this.dashboardTele = dashboardTele;
  }

  void ThreshHolding(Mat input){
    Imgproc.cvtColor(input, threshHolded, Imgproc.COLOR_BGR2HSV);
    Core.inRange(threshHolded, lowHSV, highHSV, threshHolded);
  }


  @Override
  public void init(Mat firstFrame)
  {
//        inputToCb(firstFrame);
    ThreshHolding(firstFrame);

    region1_Cb = threshHolded.submat(secondRect);
    region2_Cb = threshHolded.submat(firstRect);
  }

  @Override
  public Mat processFrame(Mat input)
  {
    ThreshHolding(input);

    region1_Cb = threshHolded.submat(secondRect);
    region2_Cb = threshHolded.submat(firstRect);

    percentage_top = Core.sumElems(region1_Cb).val[0] / secondRect.area() / 255;
    percentage_bottom = Core.sumElems(region2_Cb).val[0] / firstRect.area() / 255;

    Imgproc.rectangle(threshHolded, secondRect, defualt, 3);
    Imgproc.rectangle(threshHolded, firstRect, defualt, 3);

    if(round(percentage_top) < pass && round(percentage_bottom) > pass){
      stackPos = stack_pos.ONE;
      Imgproc.rectangle(input, firstRect, one, 1);
      Imgproc.rectangle(input, secondRect, defualt, 1);
    }else if(round(percentage_top) > pass && round(percentage_bottom) > pass){
      stackPos = stack_pos.FOUR;
      Imgproc.rectangle(input, firstRect, four, 1);
      Imgproc.rectangle(input, secondRect, four, 1);
    }else{
      Imgproc.rectangle(input, firstRect, zero, 1);
      Imgproc.rectangle(input, secondRect, zero, 1);
    }
    getInfo();

    return input;
  }

  public void getInfo(){
    dashboardTele.addData("Stack is: ", stackPos);
    dashboardTele.addData("Percentage Top: ", round(percentage_top));
    dashboardTele.addData("Percentage Bottom: ", round(percentage_bottom));
    dashboardTele.update();
  }

  public static double round(double x){
    return (double)Math.round(x * 1000d) / 1000d;
  }
}
