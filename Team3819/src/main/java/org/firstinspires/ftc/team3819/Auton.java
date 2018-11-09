package org.firstinspires.ftc.team3819;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team3819.Hardware;
import org.firstinspires.ftc.team3819.TensorFlow;

/**
 * Created by Brandaddy on 12/14/2017.
 */

@Autonomous(name="Auton")
public class Auton extends LinearOpMode {

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

        robot.driveInches(75,36);
        idler();
        robot.turn(75,90);
        idler();
        robot.driveInches(75, 36);
        idler();
        robot.turn(75, -125);
        idler();
        robot.outtake();
        wait(2000);
        robot.donttake();
        robot.driveInches(-75,-6);
        idler();
        robot.turn(75, 180);
        idler();
        robot.driveInches(75,6*12);


    }
}