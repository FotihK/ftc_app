package org.firstinspires.ftc.team2993;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;



@Autonomous(name="IMU Test Turning", group="test")
public class imuTest extends LinearOpMode
{
    public double turnSpeed = .5d;



    RobotHardware robot;

    BNO055IMU imu;

    Orientation lastAngles = new Orientation();
    double globalAngle;



    @Override
    public void runOpMode()
    {
        initializeRobot();
        waitForStart();



        turn(45);
        sleep(2000);
        turn(45);
        sleep(2000);
        turn(90);
        sleep(2000);
        turn(180);
        sleep(2000);
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
        double dif;
        while (Math.abs(dif = (angle - globalAngle)) > 2)
        {
            double sign = Math.signum(dif);
            robot.fL.setPower(-turnSpeed * sign);
            robot.fR.setPower(turnSpeed * sign);

            telemetry.addData("Current angle: ", getAngle());
            telemetry.addData("Target angle: ", angle);
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

        telemetry.addData("Robot Ready! IMU Status: ", imu.getCalibrationStatus());
        telemetry.update();
    }
}