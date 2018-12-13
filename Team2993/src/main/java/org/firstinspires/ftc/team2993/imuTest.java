package org.firstinspires.ftc.team2993;

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



@Autonomous(name="IMU Test Turning", group="test")
public class imuTest extends LinearOpMode
{
    public double turnSpeed = .6d;
    public double threshold = 2d;



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



        robot.lift.setPower(.5);
        wait(4000);
        robot.lift.setPower(0);

        Drive(1, 400 , 500);

        int cheesePos = 1;
        if (!detector.isFound())
            cheesePos = 0;
        else if (detector.getXPosition() < 320)
            cheesePos = 1;
        else if (detector.getXPosition() >= 320)
            cheesePos = 2;
        wait(5000);

        turn(90);

        telemetry.addData("?Cheese Donde ESTA¿  " , cheesePos); // Gold X position.
        telemetry.update();
        switch (cheesePos)
        {
            case 0:
            case 2:
                int direction = cheesePos == 0 ? 1 : -1;

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



        turn(135);
        Drive(1, 3000, 0);
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



    private void turn(double angle)
    {
        globalAngle = 0;

        double dif;
        while (Math.abs(dif = (angle - globalAngle)) > threshold && !isStopRequested())
        {
            double sign = Math.signum(dif);
            robot.fL.setPower(turnSpeed * sign);
            robot.fR.setPower(-turnSpeed * sign);

            telemetry.addData("Current angle: ", getAngle());
            telemetry.addData("Target angle: ", angle);
            telemetry.addData("Gloabal angle: ", globalAngle);
            telemetry.update();
        }

        robot.stop();
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

        telemetry.addData("Robot Ready! IMU Status: ", imu.getCalibrationStatus());
        telemetry.update();
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
        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        detector.alignSize = 100;
        detector.alignPosOffset = 0;
        detector.downscale = 0.4;

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.maxAreaScorer.weight = 0.005;

        detector.enable();
    }
}