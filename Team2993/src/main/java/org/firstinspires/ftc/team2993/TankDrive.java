package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.*;



@TeleOp(name="team2993")
public class TankDrive extends OpMode
{
    RobotHardware robot;

    public final double threshold = .1d;



    @Override
    public void init()
    {
        robot = new RobotHardware(hardwareMap);
    }



    private void driverOne()
    {
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;

        if (Math.abs(y) < threshold)
            y = 0;
        if (Math.abs(x) < threshold)
            x = 0;

        double l = y + x;
        double r = y - x;

        if (Math.abs(l) > 1d)
            l = 1d * Math.signum(l);
        if (Math.abs(r) > 1d)
            r = 1d * Math.signum(r);

        robot.Drive(l, x);
    }



    @Override
    public void loop() {
        init();
        driverOne();
    }
}



