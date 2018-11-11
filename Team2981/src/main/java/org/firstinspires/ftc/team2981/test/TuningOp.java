package org.firstinspires.ftc.team2981.test;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.team2981.roadrunner.drive.FeedforwardTuningOpMode;
import org.firstinspires.ftc.team2981.systems.RobotDrive;

@Autonomous(group="Test")
public class TuningOp extends FeedforwardTuningOpMode {
    public TuningOp() {
        // TODO: change the following to match your drive
        super(100.0, RobotDrive.MOTOR_CONFIG.getMaxRPM(), 2*RobotDrive.WHEEL_RADIUS);
    }

    @Override
    protected Drive initDrive() {
        return new RobotDrive(hardwareMap);
    }
}
