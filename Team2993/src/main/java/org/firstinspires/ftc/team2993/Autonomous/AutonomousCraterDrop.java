package org.firstinspires.ftc.team2993.Autonomous;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import org.firstinspires.ftc.team2993.*;



@Autonomous(name="Auto - Crater Drop", group="Crater")
public class AutonomousCraterDrop extends LinearOpMode
{
    public boolean debug = false;

    public final double driveSpeed = .5d;
    public final double turnSpeed = .6d;
    public final double turnThreshold = 1d;



    RobotHardware robot;
    GoldAlignDetector detector;
    BNO055IMU imu;

    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    Orientation lastAngles = new Orientation();
    double globalAngle;



    @Override
    public void runOpMode()
    {
        initializeRobot();
        waitForStart();



        // Drop down, drive forward to unhook, get cheese position

        SetLift(.5d, 4000);
        Drive(driveSpeed, 400 , 250);

        int cheesePos = getCheesePosition();
        telemetry.addData("?Cheese Donde ESTA¿  " , cheesePos); // Gold X position.
        telemetry.update();
        wait(250);



        // Turn to face crater, follow path for where the cheese currently is, return to center

        Turn(85, 100);

        switch (cheesePos)
        {
            case 0:
            case 2:
                int direction = cheesePos == 0 ? 1 : -1;

                Turn(25 * direction, 100);
                Drive(driveSpeed, 1600, 100);
                //Turn(25 * direction, 0);
                //Turn(25 * -direction, 0);
                Drive(-driveSpeed, 1600, 100);
                Turn(25 * -direction, 100);
                Drive(driveSpeed, 250, 100);
                break;

            case 1:
                wait(500);
                Drive(driveSpeed, 1500, 250);
                Drive(-driveSpeed, 1000, 250);
                break;
        }



        // Drive past crater and go to depot

        Turn(70, 250);
        Drive(driveSpeed, 2250, 250);
        Turn(50, 250);
        Drive(driveSpeed, 3000, 250);



        // Drop team marker, turn to crater, drive to crater

        SetIntake(.5d, 2000);
        Drive(-driveSpeed, 6000, 0);
    }



    public void Drive(double speed, int time, int extraWait)
    {
        robot.Drive(speed, speed);
        wait(time);

        robot.stop();
        wait(extraWait);
    }

    private void Turn(double angle, int extraWait)
    {
        globalAngle = 0;

        double dif;
        while (Math.abs(dif = (angle - globalAngle)) > turnThreshold && !isStopRequested())
        {
            getAngle();
            double sign = Math.signum(dif);
            robot.fL.setPower(turnSpeed * sign);
            robot.fR.setPower(-turnSpeed * sign);

            if (debug)
            {
                telemetry.addData("Current angle: ", getAngle());
                telemetry.addData("Target angle: ", angle);
                telemetry.addData("Global angle: ", globalAngle);
                telemetry.update();
            }
        }

        robot.stop();
        wait(extraWait);
    }

    public void SetLift(double speed, int time)
    {
        robot.lift.setPower(speed);
        wait(time);
        robot.lift.setPower(0);
    }

    public void SetIntake(double speed, int time)
    {
        robot.intake.setPower(speed);
        wait(time);
        robot.intake.setPower(0);
    }



    private void initializeRobot()
    {
        robot = new RobotHardware(hardwareMap);

        imu = hardwareMap.get(BNO055IMU.class, "imu");



        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        imu.initialize(parameters);

        while (!isStopRequested() && !imu.isGyroCalibrated())
            idle();



        setupDetector();

        telemetry.addData("****** Robot Ready! IMU Status: ", imu.getCalibrationStatus());
        telemetry.update();
    }

    public void setupDetector()
    {
        detector = new GoldAlignDetector(debug);
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        detector.alignSize = 20;
        detector.alignPosOffset = 0;
        detector.downscale = 0.4;

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.perfectAreaScorer.weight = 0.005;

        detector.enable();
    }



    private double getAngle()
    {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;
        lastAngles = angles;

        return angles.firstAngle;
    }

    public int getCheesePosition()
    {
        double x = detector.getXPosition();

        int pos = 1;
        if (!detector.isFound())
            pos = 0;
        else if (x < 320)
            pos = 1;
        else if (x >= 320)
            pos = 2;

        return pos;
    }

    public void wait (int ms)
    {
        timer.reset();
        while (timer.time() < ms && opModeIsActive())
            idle();
    }
}