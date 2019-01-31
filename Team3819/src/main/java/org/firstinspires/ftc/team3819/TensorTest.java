package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.vision.MasterVision;
import org.firstinspires.ftc.teamcode.vision.SampleRandomizedPositions;

@Autonomous(name = "TensorTest")
@Disabled
public class TensorTest extends LinearOpMode{
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
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AWN1kMH/////AAABmW5e69+Ipk5mtJ3mu+ukdJQCV7Ua9BkkAuynss2OFoEIzRvaayTU1o/OElTzaokcxqy0YIOMu0wE7EklChus6LpqjLfROa6QkKzRAeYNqg6eLAxtNZJUxtdtdr7DkpOlJxitgyrPZjk03AfwwCuCUkfDUnZBQ3Vlt7Ky3otvFyu2BrK+bBqTfXqk2BUDc8s6fr4vC9aHn9LmzLjjwRvRJ4fDg4LrJD0E08cHWBSbju0OhtfqxQLBqFPUXHiEqbnYKiQBjf8S88coiJp5DYHUFUhUnKdeImzfI7h/rPJLWZgf7FC4LkulEuxly2QISYotR64PAJbfHdoy2YKV5Uei3TGaFIPwHDpEvdklGZeIIu2p";

        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_RIGHT);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        waitForStart();

        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        while(opModeIsActive()){
            telemetry.addData("goldPosition was", goldPosition);// giving feedback

            switch (goldPosition){ // using for things in the autonomous program
                case LEFT:
                    telemetry.addLine("going to the left");
                    break;
                case CENTER:
                    telemetry.addLine("going straight");
                    break;
                case RIGHT:
                    telemetry.addLine("going to the right");
                    break;
                case UNKNOWN:
                    telemetry.addLine("staying put");
                    break;
            }

            telemetry.update();
        }

        vision.shutdown();
    }

    public void driveInches(double pow, int in) {
        robot.resetEnc();
        int target = -1*(int)(in*robot.CPI);
        int dir = in >= 0 ? 1 : -1;
        robot.left.setPower(pow*dir);
        robot.right.setPower(pow*dir);
        while( (robot.left.getCurrentPosition()>target + 50||robot.left.getCurrentPosition()<target-50) &&
                (robot.right.getCurrentPosition()>target + 50||robot.right.getCurrentPosition()<target-50) &&
                opModeIsActive()) {
            telemetry.addLine("Left: " + robot.left.getCurrentPosition());
            telemetry.addLine("Right: " + robot.right.getCurrentPosition());
            telemetry.update();
        }
        robot.stop();
    }

    public void turn(double degrees) {
        robot.resetEnc();
        int powL = degrees >= 0 ? 1 : -1;
        int powR = powL * -1;

        int targetL = ((int) ((degrees / 360) * robot.CIRCUMFRENCE * robot.CPI)); //left gets a negative
        int targetR = targetL * -1;

        robot.left.setPower(powL*.25);
        robot.right.setPower(powR*.25);

        while ((robot.left.getCurrentPosition() > targetL + 10 || robot.left.getCurrentPosition() < targetL - 10) &&
                (robot.right.getCurrentPosition() > targetR + 10 || robot.right.getCurrentPosition() < targetR - 10) &&
                opModeIsActive()) {
            telemetry.addLine("Left: " + robot.left.getCurrentPosition());
            telemetry.addLine("Right: " + robot.right.getCurrentPosition());
            telemetry.update();
        }
        robot.stop();
    }



    public void waitCustom(int ms) {
        time.reset();
        while(time.milliseconds()<ms) {}
    }
}
