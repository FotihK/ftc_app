package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.vision.MasterVision;
import org.firstinspires.ftc.teamcode.vision.SampleRandomizedPositions;

@Autonomous(name = "TensorCrater")
public class TensorAutonCrater extends LinearOpMode{
    private Hardware robot = null;

    private ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public void initialize() {
        robot = new Hardware(hardwareMap);
    }

    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraName = hardwareMap.get(WebcamName.class,"Webcam 1");
        //parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AWN1kMH/////AAABmW5e69+Ipk5mtJ3mu+ukdJQCV7Ua9BkkAuynss2OFoEIzRvaayTU1o/OElTzaokcxqy0YIOMu0wE7EklChus6LpqjLfROa6QkKzRAeYNqg6eLAxtNZJUxtdtdr7DkpOlJxitgyrPZjk03AfwwCuCUkfDUnZBQ3Vlt7Ky3otvFyu2BrK+bBqTfXqk2BUDc8s6fr4vC9aHn9LmzLjjwRvRJ4fDg4LrJD0E08cHWBSbju0OhtfqxQLBqFPUXHiEqbnYKiQBjf8S88coiJp5DYHUFUhUnKdeImzfI7h/rPJLWZgf7FC4LkulEuxly2QISYotR64PAJbfHdoy2YKV5Uei3TGaFIPwHDpEvdklGZeIIu2p";
        robot = new Hardware(hardwareMap);

        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_RIGHT);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        waitForStart();

        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        while(opModeIsActive()){
            telemetry.addData("goldPosition was", goldPosition);// giving feedback
            telemetry.update();
            //add drop TODO
            driveInches(.25,6);
            robot.stop();
            driveInches(.26,-6);
            robot.stop();

            switch(goldPosition)
            {
                case LEFT:
                    driveInches(.5, 14);
                    turn(45);
                    waitCustom(1000);
                    driveInches(.5,18);
                    waitCustom(500);
                    driveInches(.5,-18); //ends case
                    turn(45);
                    break;
                case CENTER:
                    driveInches(.5,20);
                    waitCustom(1000);
                    driveInches(.5, -20);
                    turn(90);
                    break;
                case RIGHT:
                    turn(-45);
                    driveInches(.5,24);
                    waitCustom(1000);
                    driveInches(.5,-24);
                    turn(135);
                    break;
                case UNKNOWN:
                    driveInches(.5,20);
                    waitCustom(1000);
                    driveInches(.5, -20);
                    turn(90);
                    break;
                default:
                    driveInches(.5,20);
                    waitCustom(1000);
                    driveInches(.5, -20);
                    turn(90);
                    break;
            }
        }

        driveInches(.5,(int)(3.75*12));
        turn(45);
        driveInches(.5, 52);
        robot.outtake();
        waitCustom(1000);
        robot.donttake();
        driveInches(.5, -75);

        vision.shutdown();
    }

    public void driveInches(double pow, int in) {
        resetEncoders();
        int target = (int)(in*robot.CPI);
        int dir = in >= 0 ? 1 : -1;
        robot.left.setPower(pow*dir);
        robot.right.setPower(pow*dir);
        while( (robot.left.getCurrentPosition()>target + 75||robot.left.getCurrentPosition()<target-75) ||
                (robot.right.getCurrentPosition()>target + 75||robot.right.getCurrentPosition()<target-75) &&
                opModeIsActive()) {
            telemetry.addLine("Target: " + target);
            telemetry.addLine("Left: " + robot.left.getCurrentPosition());
            telemetry.addLine("Right: " + robot.right.getCurrentPosition());
            telemetry.update();
        }
        robot.stop();
    }

    public void turn(double degrees) {
        resetEncoders();
        int powL = degrees >= 0 ? 1 : -1;
        int powR = powL * -1;

        int targetL = -1*((int) ((degrees / 360) * robot.CIRCUMFRENCE * robot.CPI)); //left gets a negative
        int targetR = targetL * -1;

        robot.left.setPower(powL*.25);
        robot.right.setPower(powR*.25);

        while ((robot.left.getCurrentPosition() > targetL + 20 || robot.left.getCurrentPosition() < targetL - 20) ||
                (robot.right.getCurrentPosition() > targetR + 20 || robot.right.getCurrentPosition() < targetR - 20) &&
                opModeIsActive()) {
            telemetry.addLine("TargetL: " + targetL);
            telemetry.addLine("Left: " + robot.left.getCurrentPosition());
            telemetry.addLine("TargetR: " + targetR);
            telemetry.addLine("Right: " + robot.right.getCurrentPosition());
            telemetry.update();
        }
        robot.stop();
    }



    public void waitCustom(int ms) {
        time.reset();
        while(time.milliseconds()<ms) {}
    }

    public void resetEncoders(){
        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
