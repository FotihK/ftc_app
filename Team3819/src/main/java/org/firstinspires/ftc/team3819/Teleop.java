package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
            //robot.slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.slide.setPower(-50);
        }
        else if(gamepad1.dpad_up) {
            //robot.slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.slide.setPower(50);
        }
        else {
            robot.slide.setPower(0);
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

    }

    @Override
    public void loop() {
        init();
        driverOne();
    }
}
