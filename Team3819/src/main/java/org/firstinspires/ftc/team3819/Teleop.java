package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team3819.Hardware;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {
    Hardware robot;

    @Override
    public void init() {
        robot = new Hardware(hardwareMap);
    }

    private void driverOne() {
        robot.drive(gamepad1);

        if(gamepad1.dpad_down) {
            robot.slideDown();
            //robot.down();
        }
        else if(gamepad1.dpad_up) {
            robot.slideUp();
            //robot.up();
        }

        else {
            robot.slideStop();
        }

        if(gamepad1.a) {
            robot.intake();
        }
        else if(gamepad1.b) {
            robot.donttake();
        }
        else if(gamepad1.x) {
            robot.outtake();
        }

        if(gamepad1.left_bumper) {
            robot.encoderTest();
        }


        telemetry.addData("Left Position", robot.left.getCurrentPosition());
        telemetry.addData("Right Position", robot.right.getCurrentPosition());
        telemetry.update();

    }

    @Override
    public void loop() {
        init();
        driverOne();
    }
}
