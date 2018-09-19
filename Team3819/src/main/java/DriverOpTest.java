package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team3819.Structural.RobotHardware1;

/**
 * Created by Brandaddy on 9/18/18.
 */

@TeleOp(name="DriverOpTests")
public class DriverOpTest extends OpMode {
    RobotHardware1 robot;

    @Override
    public void init() {
        robot = new RobotHardware1(hardwareMap);
        robot.init();
    }

    private void driverOne() {
        robot.drive(gamepad1);
    }



    @Override
    public void loop() {
        init();
        driverOne();
    }
}
