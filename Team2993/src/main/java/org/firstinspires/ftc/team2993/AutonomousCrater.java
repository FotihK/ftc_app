package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="Auto - Crater", group="crater")
public class AutonomousCrater extends LinearOpMode
{
    private RobotHardware robot;
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);



    @Override
    public void runOpMode()
    {
        robot = new RobotHardware(hardwareMap);

        waitForStart();



        wait(20000);
        robot.Drive(-1d);
        wait(4000);
        robot.Drive(0d);

    }



    public void wait (int ms)
    {
        timer.reset();
        while (timer.time() < ms && opModeIsActive())
            idle();
    }
}