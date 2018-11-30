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

        Turn(1, 700);
        wait(500);

        Drive(-1, 500);

        Turn(-1, 750);



        Drive(-1, 5500);

        robot.intake.setPower(1d);
        wait(2000);
        robot.intake.setPower(0d);

        Drive(1, 1000);



        Turn(1, 2000);
        Drive(-1, 1500);

        Turn(1, 500);
        Drive(-1, 6000);
    }

    public void Turn(int direction, int time)
    {
        robot.Drive(1d * direction, -1d * direction);
        wait(time);
        robot.Drive(0d);
    }

    public void Drive(int direction, int time)
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