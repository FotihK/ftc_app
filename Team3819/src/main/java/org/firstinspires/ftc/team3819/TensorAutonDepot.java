package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Brandaddy on 12/14/2017.
 */

@Autonomous(name="TensorAutonDepot")
public class TensorAutonDepot extends LinearOpMode {

    private Hardware robot = null;
    private TensorFlow tensorFlow = null;
    private ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public void initialize() {
        robot = new Hardware(hardwareMap);
        tensorFlow = new TensorFlow();
        //color = (ModernRoboticsI2cColorSensor) hardwareMap.get(ColorSensor.class, "color");
    }

    public void idler() {
        time.reset();
        while (robot.isBusy() && opModeIsActive() && time.milliseconds() < 3000) {
            idle();
        }
        robot.stop();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        int goldPos = tensorFlow.getPosition();
        robot.driveInches(25, -6);
        idler();
        robot.turn(25,180);
        idler();

        //Robot is in front of center particle prior to fetching particle.
        switch (goldPos) {
            case 0: {
                robot.driveInches(25,6);
                idler();
                robot.slideDownEnc();
                idler();
                robot.intake();
                wait(1000);
                robot.donttake();
                robot.slideUpEnc();
                idler();
                robot.driveInches(25,6);
                idler();
                break;
            }
            case -1: {
                robot.turn(25,90);
                idler();
                robot.driveInches(25, 12);
                idler();
                robot.turn(25, -90);
                idler();
                robot.driveInches(25,6);
                idler();
                robot.intake();
                wait(1000);
                robot.donttake();
                robot.slideUpEnc();
                idler();
                robot.driveInches(25,-6);
                idler();
                robot.turn(25,90);
                idler();
                robot.driveInches(25,-12);
                idler();
                robot.turn(25,-90);
                idler();
                break;
            }
            case 1: {
                robot.turn(25,-90);
                idler();
                robot.driveInches(25, 12);
                idler();
                robot.turn(25, 90);
                idler();
                robot.driveInches(25,6);
                idler();
                robot.intake();
                wait(1000);
                robot.donttake();
                robot.slideUpEnc();
                idler();
                robot.driveInches(25,-6);
                idler();
                robot.turn(25,-90);
                idler();
                robot.driveInches(25,-12);
                idler();
                robot.turn(25,90);
                idler();
                break;
            }
        }//End Switch. Robot is in front of center particle location facing out.

        robot.turn(25,90);
        idler();
        robot.driveInches(50, 24);
        idler();
        robot.turn(25,-125); //Robot is facing depot
        idler();
        robot.driveInches(50,3*12);
        idler();
        robot.outtake();
        wait(1000);
        robot.donttake();
        robot.turn(25,180);
        idler();
        robot.driveInches(50,6*12);


    }
}