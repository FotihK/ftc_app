package org.firstinspires.ftc.team2993;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Auto - Depot Drop", group="depot")
public class AutonomousDepotDrop extends LinearOpMode
{
    private RobotHardware robot;
    private GoldAlignDetector detector;
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);



    @Override
    public void runOpMode()
    {
        robot = new RobotHardware(hardwareMap);
        setupDetector();



        waitForStart();



        robot.lift.setPower(.5);
        wait(4000);
        robot.lift.setPower(0);

        Drive(1, 400 , 0);
        turn(90);

        int cheesePos = 1;
        if (!detector.isFound())
            cheesePos = 0;
        else if (detector.getXPosition() >= 320)
            cheesePos = 2;
        else if (detector.getXPosition() < 320)
            cheesePos = 1;
        wait(500);


        telemetry.addData("Is left" , cheesePos); // Gold X position.
        telemetry.update();
        switch (cheesePos)
        {
            case 0:
            case 2:
                int direction = cheesePos == 0 ? 1 : -1;
                telemetry.addData("Is left" , direction); // Gold X position.

                turn(30 * direction);
                Drive(1, 1750, 1000);
                turn(-60 * direction);
                Drive(1, 1500, 1000);
                turn(30 * direction);
                break;
            case 1:
                Drive(1, 2500, 1000);
                break;
        }



        Drive(1, 500, 0);

        robot.intakeE.setPower(-.5);
        wait(1000);
        robot.intakeE.setPower(0);

        robot.intake.setPower(1d);
        wait(2000);
        robot.intake.setPower(0d);

        Drive(-1, 750, 1000);



        turn(-90);
        Drive(1, 1500, 1000);

        turn(-30);
        Drive(1, 2000, 1000);
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

    public void setupDetector()
    {
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable();
    }


    public void turn(double degrees) {
        robot.resetEnc();
        int powL = degrees >= 0 ? 1 : -1;
        int powR = powL * -1;

        int targetL = ((int) ((degrees / 360) * robot.CIRCUMFRENCE * robot.CPI)); //left gets a negative
        int targetR = targetL * -1;

        robot.fL.setPower(powL * .8);
        robot.fR.setPower(powR * .8);

        while ((robot.fL.getCurrentPosition() > targetL + 10 || robot.fL.getCurrentPosition() < targetL - 10) &&
                (robot.fR.getCurrentPosition() > targetR + 10 || robot.fR.getCurrentPosition() < targetR - 10) &&
                opModeIsActive()) {
        }
        robot.stop();
        wait(1000);
    }
}