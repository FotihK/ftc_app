package org.firstinspires.ftc.team2993;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Auto - Crater Drop", group="crater")
public class AutonomousCraterDrop extends LinearOpMode
{
    private RobotHardware robot;
    private GoldAlignDetector detector;
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);



    @Override
    public void runOpMode()
    {
        robot = new RobotHardware(hardwareMap);



        waitForStart();



        robot.lift.setPower(.5);
        wait(4000);
        robot.lift.setPower(0);

        Drive(1, 400 , 0);

        Turn(1, 700, 2000);
        Drive(1, 2500, 0);

        robot.intake.setPower(1d);
        wait(2000);
        robot.intake.setPower(0d);

        Drive(-1, 750, 1000);
    }



    public void Turn(int direction, int time, int extraWait)
    {
        robot.Drive(-direction, direction);
        wait(time);
        robot.Drive(0d);
        wait(extraWait);
    }

    public void Drive(int direction, int time, int extraWait)
    {
        robot.Drive(.5 * direction, .5 * direction);
        wait(time);
        robot.Drive(0d);
        wait(extraWait);
    }



    public void wait (int ms)
    {
        timer.reset();
        while (timer.time() < ms && opModeIsActive())
            idle();
    }
}