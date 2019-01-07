package org.firstinspires.ftc.team2981.old.test;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.team2981.old.roadrunner.drive.TrackWidthCalibrationOpMode;
import org.firstinspires.ftc.team2981.old.systems.RobotDrive;

@Autonomous(group="Test")
public class TrackWidthOp extends TrackWidthCalibrationOpMode {
    @Override
    protected Drive initDrive() {
        return new RobotDrive(hardwareMap);
    }

    @Override
    protected BNO055IMU initIMU() {
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
        return imu;
    }
}
