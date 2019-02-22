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

@Autonomous(name = "TensorDepot")
public class TensorAutonDepot extends LinearOpMode{
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
        waitCustom(2000);
        robot.liftFront.setPower(0);
        robot.liftBack.setPower(0); //lowers the bot
        waitCustom(1000);

        telemetry.addData("goldPosition was", goldPosition);// giving feedback
        telemetry.update();

        switch(goldPosition)
        {
            case LEFT: //where it will go if gold position is left
                telemetry.addData("goldPosition was", goldPosition);
                turn(45);
                driveInches(.4,24); //turns towards and goes to left particle
                waitCustom(1000);
                turn(-90);           //turns towards and goes to rendevous
                driveInches(.4,24);
                turn(45);            //turns to face corner at rendevous
                break;
            case CENTER: //path if gold particle is in center
                telemetry.addData("goldPosition was", goldPosition);
                driveInches(.2,(int)(Math.sqrt(2*Math.pow(24,2)))); //hits center particle and goes to rendevouz
                break;
            case RIGHT://where it will go if gold position is right
                telemetry.addData("goldPosition was", goldPosition);
                turn(-45);
                driveInches(.4,24); //turns towards and goes to right particle
                waitCustom(1000);
                turn(90);            //turns towards and goes to rendevous
                driveInches(.4,24);
                turn(-45);           //turns to face corner at rendevous
                break;
            case UNKNOWN: //assumes center if unknown
                telemetry.addData("goldPosition was", goldPosition);
                driveInches(.2,20); //hits center particle
                waitCustom(500);
                driveInches(.2, -8); //returns to rendevous
                break;
            default: //assumes center as default
                telemetry.addData("goldPosition was", goldPosition);
                driveInches(.2,20); //hits center particle
                waitCustom(500);
                driveInches(.2, -8); //returns to rendevous
                break;
        }

        driveInches(.2,4); //goes a little into depot

        robot.outtake();
        waitCustom(1000);
        robot.donttake();        //spits out particle

        turn(-90);           //face wall

        driveInches(.3,6);   //go to wall

        turn(-45);            //face crater

        driveInches(.6,72); //go to crater



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

        robot.left.setPower(powL*.25);
        robot.right.setPower(powR*.25);
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
