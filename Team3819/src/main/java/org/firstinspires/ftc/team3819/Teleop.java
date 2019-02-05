package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team3819.Hardware;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {
    Hardware robot;
    double turn;

    @Override
    public void init() {
        robot = new Hardware(hardwareMap);
    }

    private void driverOne() {
        robot.drive(gamepad1);
        telemetry.addLine("Y stick 1 " + gamepad1.left_stick_y);
        telemetry.addLine("X stick 2 " + gamepad1.right_stick_x);
        telemetry.update();

        if(gamepad1.dpad_down) {
            robot.armUp();
        }
        else if(gamepad1.dpad_up) {
            robot.armDown();
        }
        else {
            robot.armStop();
        }

        if(gamepad1.y) {
            robot.servoUp();
        }

        if(gamepad1.x)
        {
            robot.servoDown();
        }

    }

    public void driverTwo() {

        if(gamepad2.left_bumper) {
           robot.outtake();
        }
        else if(gamepad2.right_bumper) {
           robot.intake();
        }
        else {
            robot.donttake();
        }

        if(Math.abs(gamepad2.left_stick_y) > .05)
            robot.winch(gamepad2.left_stick_y);
        else robot.winch(0);
/*
        if(Math.abs(gamepad2.right_stick_y) > .05)
            robot.liftSlide(gamepad2.right_stick_y);
        else robot.liftSlide(0); */

    }

    @Override
    public void loop() {
        init();
        driverOne();
        driverTwo();
    }
}
