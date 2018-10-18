package org.firstinspires.ftc.team2981.roadrunner.samples;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.team2981.roadrunner.drive.FeedforwardTuningOpMode;

@Autonomous
@Disabled
public class SampleFeedforwardTuningOpMode extends FeedforwardTuningOpMode {
    public SampleFeedforwardTuningOpMode() {
        // TODO: change the following to match your drive
        super(100.0, SampleMecanumDrive.MOTOR_CONFIG.getMaxRPM(), 4.0);
    }

    @Override
    protected Drive initDrive() {
        return new SampleMecanumDrive(hardwareMap);
    }
}
