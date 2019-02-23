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
        initialize();

        robot.liftBack.setPower(.3);
        robot.liftFront.setPower(.5);

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraName = hardwareMap.get(WebcamName.class,"Webcam 1");
        //parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AWN1kMH/////AAABmW5e69+Ipk5mtJ3mu+ukdJQCV7Ua9BkkAuynss2OFoEIzRvaayTU1o/OElTzaokcxqy0YIOMu0wE7EklChus6LpqjLfROa6QkKzRAeYNqg6eLAxtNZJUxtdtdr7DkpOlJxitgyrPZjk03AfwwCuCUkfDUnZBQ3Vlt7Ky3otvFyu2BrK+bBqTfXqk2BUDc8s6fr4vC9aHn9LmzLjjwRvRJ4fDg4LrJD0E08cHWBSbju0OhtfqxQLBqFPUXHiEqbnYKiQBjf8S88coiJp5DYHUFUhUnKdeImzfI7h/rPJLWZgf7FC4LkulEuxly2QISYotR64PAJbfHdoy2YKV5Uei3TGaFIPwHDpEvdklGZeIIu2p";

        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_NONE);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        waitForStart();

        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        robot.liftBack.setPower(0);
        robot.liftFront.setPower(-.2);
        waitCustom(2500);
        robot.liftFront.setPower(0);
        robot.liftBack.setPower(0); //lowers the bot
        waitCustom(500);

        resetEncoders();
        int target = (int)(2*robot.CPI);
        robot.right.setPower(.2);
        while ((Math.abs(robot.right.getCurrentPosition()) <= Math.abs(target)) && opModeIsActive()) {}
        robot.stop();

        resetEncoders();
        target = (int)(4*robot.CPI);
        robot.left.setPower(.2);
        while ((Math.abs(robot.left.getCurrentPosition()) <= Math.abs(target)) && opModeIsActive()) {}
        robot.stop();

        resetEncoders();
        target = (int)(2*robot.CPI);
        robot.right.setPower(.2);
        while ((Math.abs(robot.right.getCurrentPosition()) <= Math.abs(target)) && opModeIsActive()) {}
        robot.stop();

        telemetry.addData("goldPosition was", goldPosition);// giving feedback
        telemetry.update();

            switch(goldPosition)
            {
                case LEFT:
                    telemetry.addData("goldPosition was", goldPosition);
                    turn(45);
                    waitCustom(500);
                    driveInches(.4,20);
                    waitCustom(500);
                    driveInches(.4,-8);
                    waitCustom(500);
                    turn(30);
                    break;
                case CENTER:
                    telemetry.addData("goldPosition was", goldPosition);
                    driveInches(.4,18); //hits center particle
                    waitCustom(500);
                    driveInches(.4, -9); //returns to rendevous
                    turn(70); //turns towards outside walladb
                    break;
                case RIGHT:
                    telemetry.addData("goldPosition was", goldPosition);
                    turn(-45);
                    driveInches(.4,18);
                    waitCustom(1000);
                    driveInches(.4,-9);
                    turn(115);
                    break;
                case UNKNOWN:
                    telemetry.addData("goldPosition was", goldPosition);
                    driveInches(.4,20); //hits center particle
                    waitCustom(500);
                    driveInches(.4, -8); //returns to rendevous
                    turn(70); //turns towards outside wall
                    break;
                default:
                    telemetry.addData("goldPosition was", goldPosition);
                    driveInches(.4,20); //hits center particle
                    waitCustom(500);
                    driveInches(.4, -8); //returns to rendevous
                    turn(70); //turns towards outside wall
                    break;
            }

            driveInches(.6,20); //goes to wall

            turn(42);

            driveInches(.6, 22);
            robot.outtake();
            waitCustom(1000);
            robot.donttake();        //spits out particle
            turn(2);
            driveInches(.6, -72);

               vision.shutdown();
    }

    public void driveInches(double pow, int in) {
        resetEncoders();
        int target = (int)(in*robot.CPI);
        int dir = in >= 0 ? 1 : -1;
        robot.left.setPower(pow*dir);
        robot.right.setPower(pow*dir);

        while ((Math.abs(robot.left.getCurrentPosition()) <= Math.abs(target)) &&
                (Math.abs(robot.right.getCurrentPosition()) <= Math.abs(target))
                && opModeIsActive()) {
            telemetry.addData("goldPosition was", goldPosition);
            telemetry.addLine("Target: " + target);
            telemetry.addLine("Left: " + robot.left.getCurrentPosition());
            telemetry.addLine("Right: " + robot.right.getCurrentPosition());
            telemetry.update();
        }

        /*
        while( (robot.left.getCurrentPosition()>target + 75||robot.left.getCurrentPosition()<target-75) ||
                (robot.right.getCurrentPosition()>target + 75||robot.right.getCurrentPosition()<target-75) &&
                opModeIsActive()) {
            telemetry.addLine("Target: " + target);
            telemetry.addLine("Left: " + robot.left.getCurrentPosition());
            telemetry.addLine("Right: " + robot.right.getCurrentPosition());
            telemetry.update();
        } */
        robot.stop();
    }

    public void turn(double degrees) {
        resetEncoders();
        int powL = degrees >= 0 ? -1 : 1;
        int powR = powL * -1;

        int targetL = -1*((int) ((degrees / 360) * robot.CIRCUMFRENCE * robot.CPI)); //left gets a negative
        int targetR = targetL * -1;

        robot.left.setPower(powL*.3);
        robot.right.setPower(powR*.3);
        while ((Math.abs(robot.left.getCurrentPosition()) < Math.abs(targetL)) &&
                (Math.abs(robot.right.getCurrentPosition()) < Math.abs(targetR))
                && opModeIsActive()) {
            telemetry.addData("goldPosition was", goldPosition);
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
        while(time.milliseconds()<ms && opModeIsActive()) {}
    }

    public void resetEncoders(){
        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
