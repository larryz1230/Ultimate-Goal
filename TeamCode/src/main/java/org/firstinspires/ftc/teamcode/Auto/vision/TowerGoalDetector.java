package org.firstinspires.ftc.teamcode.Auto.vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import java.util.ArrayList;
import java.util.List;

public class TowerGoalDetector extends OpenCvPipeline {
    final Scalar donut_found = new Scalar(0, 255, 0);
    final Scalar donut_not_found = new Scalar(255, 0, 0);
    private Rect[] boundRect_arr;
    private Telemetry t;
    private final double pole_ratio = 1;
    private final double  goal_ratio = 2;

    public TowerGoalDetector(Telemetry t){
        t = this.t;
    }

    @Override
    // A matix of pixels (the picture the camera takes)
    public final Mat processFrame(Mat input){
        Mat workingMatrix = new Mat();
        //Holds the grayscaled image
        Mat grayScaled = new Mat();


        input.copyTo(workingMatrix);

        if(workingMatrix.empty()){
            return input;
        }

        Imgproc.medianBlur(workingMatrix, workingMatrix, 3); //No background noise
        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2HSV); //Looking for a color range
        //Define the range of colors we are looking for
        Scalar lowHSV = new Scalar(62, 32, 100); //Hue, Saturation, Values
        Scalar highHSV = new Scalar(54, 100, 29);

        //Thresholding the image, only show yellow and nothing else (Grayscaled)
        Core.inRange(workingMatrix, lowHSV, highHSV, workingMatrix);
        boundRect_arr = getBoundingRect(workingMatrix);

        return workingMatrix;
    }

    private Rect[] getBoundingRect(Mat workingMatrix){
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>(); //List of contours stored as a list of points
        Imgproc.findContours(workingMatrix, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        /*
            workingMatrix: Binary image
            contours: List of detected contours
            hierarchy: Output vector containing information about the image topology
            RETR_TREE: The mode, retrieves all of the contours and reconstructs a full hierarchy of nested contours
            CHAIN_APPROX_SIMPLE: The method, compresses horizontal, vertical, and diagonal segments and leaves on;y their end points (simplify the shape)
         */

        MatOfPoint2f[] contoursPoly = new MatOfPoint2f[contours.size()]; //A 2D array that wholes the contour points
        Rect[] boundRect_arr = new Rect[contours.size()]; // Store the boundRect

        // Get the shapes
        for(int i = 0; i < contours.size(); i++){
            contoursPoly[i] = new MatOfPoint2f();
            boundRect_arr[i] = Imgproc.boundingRect(new MatOfPoint(contoursPoly[i].toArray()));
        }

        List<MatOfPoint> contoursPolyList = new ArrayList<> (contoursPoly.length); // All the input contours, each contour is a vector of points
        for(MatOfPoint2f poly : contoursPoly){
            contoursPolyList.add(new MatOfPoint(poly.toArray()));
        }

        for(int i = 0; i < contours.size(); i++){
            Imgproc.drawContours(workingMatrix, contoursPolyList, i, donut_not_found); //The 'i' is the contourldx, can be any number other an negative
            Imgproc.rectangle(workingMatrix, boundRect_arr[i].tl(), boundRect_arr[i].br(), donut_not_found);
        }
        return boundRect_arr;
    }

    public Rect[] returnBoundRect(){
        if(boundRect_arr == null) {
            t.addData("boundRect not found", 0);
            t.update();
            System.exit(1);
        }
        return boundRect_arr;
    }

    private double scoreRects(Rect rect){
        double rect_ratio = rect.width / rect.height;
        double score = rect_ratio / pole_ratio;
        return score;
    }

    public MaxHeapPQ sortRects(Rect[] rectsFound){
        MaxHeapPQ mhpq = new MaxHeapPQ();
        for(Rect rect : rectsFound){
            mhpq.insert(rect, scoreRects(rect));
        }
        return mhpq;
    }

    public Rect goal_coor(MaxHeapPQ mhpq){
        Rect best_match = mhpq.poll();
        Rect second_match;
        Rect tower_goal = null;
        for(int i = 0; i < mhpq.size(); i++){
            second_match = mhpq.poll();
            tower_goal = new Rect(
                    new Point(best_match.x, best_match.y),
                    new Point(second_match.x, second_match.y)
            );
            double guess_ratio = tower_goal.width/tower_goal.height;
            if(guess_ratio/goal_ratio > 0.8){
                break;
            }else{
                continue;
            }
        }
        return tower_goal;
    }


}

