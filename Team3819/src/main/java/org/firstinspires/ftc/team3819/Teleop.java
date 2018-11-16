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
            //robot.slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.slideDown();
        }
        else if(gamepad1.dpad_up) {
            //robot.slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.slideUp();
        }
        else {
            robot.slide.setPower(0);
        }



    }

    public void driverTwo() {

        if(gamepad2.left_trigger > 0) {
            robot.outtake(gamepad2.left_trigger);
        }
        else if(gamepad2.right_trigger > 0) {
            robot.intake(gamepad2.right_trigger);
        }
        else {
            robot.outtake();
        }

        robot.winch(gamepad2.left_stick_y);

    }

    @Override
    public void loop() {
        init();
        driverOne();
    }
}
