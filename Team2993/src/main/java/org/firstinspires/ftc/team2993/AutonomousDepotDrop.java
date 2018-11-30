package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Auto - Depot Drop", group="blue")
public class AutonomousDepotDrop extends LinearOpMode
{
    private RobotHardware robot;
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);



    @Override
    public void runOpMode()
    {
        robot = new RobotHardware(hardwareMap);

        waitForStart();



        robot.lift.setPower(-.3);
        wait(4000);
        robot.lift.setPower(0);

        Drive(1, 500, 1000);
        Turn(1, 500, 1000);
        Drive(1, 500, 1000);
        Turn(1, 500, 1000);
        Drive(1, 3000, 1000);



        robot.intake.setPower(1d);
        wait(2000);
        robot.intake.setPower(0d);

        Drive(-1, 1000, 1000);



        Turn(-1, 2000, 1000);
        Drive(1, 1500, 1000);

        Turn(-1, 2000, 1000);
        Drive(1, 1500, 1000);
    }

    public void Turn(int direction, int time, int extraWait)
    {
        robot.Drive(1d * direction, -1d * direction);
        wait(time);
        robot.Drive(0d);
    }

    public void Drive(int direction, int time, int extraWait)
    {
        robot.Drive(direction, direction);
        wait(time);
        robot.Drive(0d);
    }



    public void wait (int ms)
    {
        timer.reset();
        while (timer.time() < ms && opModeIsActive())
            idle();
    }
}