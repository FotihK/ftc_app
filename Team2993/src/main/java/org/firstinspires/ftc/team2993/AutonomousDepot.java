package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Auto - Depot", group="blue")
public class AutonomousDepot extends LinearOpMode
{
    private RobotHardware robot;
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);



    @Override
    public void runOpMode()
    {
        robot = new RobotHardware(hardwareMap);

        waitForStart();


        /*
        robot.lift.setPower(-.3);
        wait(2000);
        robot.lift.setPower(0); */

        robot.Drive(-1d);
        wait(4500);
        robot.Drive(0d);

        robot.intake.setPower(1d);
        wait(3000);
        robot.intake.setPower(0d);

        robot.Drive(1d);
        wait(3000);
        robot.Drive(0d);

        robot.Drive(1d, -1d);
        wait(2000);
        robot.Drive(0d);
    }



    public void wait (int ms)
    {
        timer.reset();
        while (timer.time() < ms && opModeIsActive())
            idle();
    }
}