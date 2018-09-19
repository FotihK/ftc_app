package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team3819.RobotHardware;

/**
 * Created by Brandaddy on 9/18/18.
 */

@TeleOp(name="Tank Drive")
public class TankDrive extends OpMode {
    RobotHardware robot;

    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap);
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
