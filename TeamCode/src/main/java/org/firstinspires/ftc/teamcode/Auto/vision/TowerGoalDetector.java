package org.firstinspires.ftc.teamcode.Auto.vision;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class TowerGoalDetector extends OpenCvPipeline {
    Scalar redLowHSV = new Scalar(0, 72, 170); //Hue, Saturation, Values
    Scalar blueLowHSV = new Scalar(114, 45, 47); //Hue, Saturation, Values
    Scalar redHighHSV = new Scalar(180, 255, 255);
    Scalar blueHighHSV = new Scalar(143, 201, 144);

    Mat threshHoldedBlue = new Mat();
    Mat threshHoldedRed = new Mat();

    void ThreshHoldingRed(Mat input, Mat output) {
        Imgproc.blur(input, output, new Size(9, 9));
        Imgproc.cvtColor(output, output, Imgproc.COLOR_BGR2HSV);
        Core.inRange(output, redLowHSV, redHighHSV, threshHoldedRed);
    }

    void ThreshHoldingBlue(Mat input, Mat output) {
        Imgproc.blur(input, output, new Size(9, 9));
        Imgproc.cvtColor(output, output, Imgproc.COLOR_BGR2HSV);
        Core.inRange(output, blueLowHSV, blueHighHSV, threshHoldedBlue);
    }

    @Override
    public void init(Mat firstFrame) {
        ThreshHoldingRed(firstFrame, threshHoldedRed);
    }

    /*
    - First threshold to find the largest blue or red
    - Limit vision to that slice of image
    - Next find the red / blue poles
    - Create a bounding rect around the extreme points from step 3
     */
    @Override
    // A matix of pixels (the picture the camera takes)
    public final Mat processFrame(Mat input) {
        if (input.empty()) return input;

        ThreshHoldingRed(input, threshHoldedRed);
        Rect big_rect = getBigSquare(threshHoldedRed, input);
        Rect slice = getSlice(threshHoldedRed, input, big_rect);
        ThreshHoldingBlue(input, threshHoldedBlue);
        Rect[] rect_arr_blue = getBoundingRect(threshHoldedBlue, input, slice);
        getCenter(input, rect_arr_blue);

        return input;
    }

    private Rect getBigSquare(Mat workingMatrix, Mat output) {
        List<MatOfPoint> contours = new ArrayList<>(); //List of contours stored as a list of points
        Imgproc.findContours(workingMatrix, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        Rect[] boundRect_arr = new Rect[contours.size()]; // Store the boundRect

        // Get the shapes
        for (int i = 0; i < contours.size(); i++) {
            boundRect_arr[i] = Imgproc.boundingRect(new MatOfPoint(contours.get(i)));
        }

        Rect max = boundRect_arr[0];
        for (int i = 0; i < boundRect_arr.length; i++) {
            if (boundRect_arr[i].area() > max.area()) {
                max = boundRect_arr[i];
            }
        }

        Imgproc.rectangle(output, max.tl(), max.br(), new Scalar(0, 204, 0));
        return max;
    }

    private Rect getSlice(Mat workingMatrix, Mat output, Rect bigRect) {
        Rect slice = new Rect(
                new Point(bigRect.tl().x - 20, 0),
                new Point(bigRect.br().x + 20, workingMatrix.height())
        );

        Imgproc.rectangle(output, slice.tl(), slice.br(), new Scalar(255, 255, 102));
        return slice;
    }

    private Rect[] getBoundingRect(Mat workingMatrix, Mat output, Rect slice) {
        List<MatOfPoint> contours = new ArrayList<>(); //List of contours stored as a list of points
        //Mat slice_region = workingMatrix.submat(slice); // Coordinates are not specific to the slice region
        Imgproc.findContours(workingMatrix, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        /*
            workingMatrix: Binary image
            contours: List of detected contours
            hierarchy: Output vector containing information about the image topology
            RETR_TREE: The mode, retrieves all of the contours and reconstructs a full hierarchy of nested contours
            CHAIN_APPROX_SIMPLE: The method, compresses horizontal, vertical, and diagonal segments and leaves on;y their end points (simplify the shape)
         */

        Rect[] boundRect_arr = new Rect[contours.size()]; // Store the boundRect
        // Get the shapes
        for (int i = 0; i < contours.size(); i++) {
            boundRect_arr[i] = Imgproc.boundingRect(new MatOfPoint(contours.get(i)));
        }

        for (int i = 0; i < contours.size(); i++) {
            Imgproc.rectangle(output, boundRect_arr[i].tl(), boundRect_arr[i].br(), new Scalar(0, 0, 255));
        }
        return boundRect_arr;
    }

    private Point getCenter(Mat output, Rect[] boundRect_arr) {
        // Get tl
        Point max_tl = boundRect_arr[0].tl();
        Point max_br = boundRect_arr[0].br();

        for (Rect rect : boundRect_arr) {
            if (rect.tl().x < max_tl.x) {
                max_tl = rect.tl();
            }
        }

        for (Rect rect : boundRect_arr) {
            if (rect.br().y > max_br.y) {
                max_br = rect.br();
            }
        }

        Rect boundRect = new Rect(max_tl, max_br);
        Point center = new Point((boundRect.tl().x + boundRect.br().x) * 0.5, (boundRect.tl().y + boundRect.br().y) * 0.5);
        Imgproc.circle(output, center, 4, new Scalar(0, 0, 255));
        return new Point((boundRect.tl().x + boundRect.br().x) * 0.5, (boundRect.tl().y + boundRect.br().y) * 0.5);
    }
}

