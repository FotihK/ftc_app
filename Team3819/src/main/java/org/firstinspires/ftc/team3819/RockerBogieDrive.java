package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team3819.RockerBogieHardware;

@TeleOp(name="RockerBogieDrive")
public class RockerBogieDrive extends OpMode {
    RockerBogieHardware robot;

    @Override
    public void init() {
        robot = new RockerBogieHardware(hardwareMap);
        robot.init();
    }

    private void driverOne() {
        robot.drive(gamepad1);

        if(gamepad1.dpad_down) {
            robot.slideDown();
        }
        if(gamepad1.dpad_up) {
            robot.slideUp();
        }
        if(gamepad1.a) {
            robot.intake();
        }
        if(gamepad1.b) {
            robot.donttake();
        }
        if(gamepad1.x) {
            robot.outtake();
        }

    }

    @Override
    public void loop() {
        init();
        driverOne();
    }
}
