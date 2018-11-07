package org.firstinspires.ftc.team2981.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.followers.TankPIDVAFollower;
import com.acmerobotics.roadrunner.path.Path;
import com.acmerobotics.roadrunner.trajectory.PointTurn;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;
import com.acmerobotics.roadrunner.trajectory.constraints.TankConstraints;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team2981.roadrunner.trajectory.AssetsTrajectoryLoader;
import org.firstinspires.ftc.team2981.roadrunner.trajectory.DashboardUtil;
import org.firstinspires.ftc.team2981.systems.RobotDrive;

import java.util.Collections;

@Autonomous(group="Test")
public class TrajectoryOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        RobotDrive drive = new RobotDrive(hardwareMap);

        // change these constraints to something reasonable for your drive
        DriveConstraints baseConstraints = new DriveConstraints(20.0, 30.0, Math.PI / 2, Math.PI / 2);
        TankConstraints constraints = new TankConstraints(baseConstraints, drive.getTrackWidth());
//        Trajectory trajectory = new TrajectoryBuilder(new Pose2d(0, 0, 0), constraints)
//                .turnTo(Math.PI)
//                .waitFor(2)
//                .turnTo(0)
//                .waitFor(2)
//                .lineTo(new Vector2d(60, 0))
//                .waitFor(2)
//                .splineTo(new Pose2d(0, 40, 0))
//                .build();
        Trajectory trajectory;
        try {
            trajectory = AssetsTrajectoryLoader.load("TestTraj");
        } catch(Exception ignored) {
            trajectory = new Trajectory(Collections.singletonList(new PointTurn(
                    new Pose2d(0, 0, 0),
                    Math.toRadians(90),
                    constraints)));
        }

        // TODO: tune kV, kA, and kStatic in the following follower
        // then tune the PID coefficients after you verify the open loop response is roughly correct
        TankPIDVAFollower follower = new TankPIDVAFollower(
                drive,
                new PIDCoefficients(0, 0, 0),
                new PIDCoefficients(0, 0, 0),
                0,
                0,
                0);

        waitForStart();

        follower.followTrajectory(trajectory);
        while (opModeIsActive() && follower.isFollowing()) {
            Pose2d currentPose = drive.getPoseEstimate();

            TelemetryPacket packet = new TelemetryPacket();
            Canvas fieldOverlay = packet.fieldOverlay();

            packet.put("x", currentPose.getX());
            packet.put("y", currentPose.getY());
            packet.put("heading", currentPose.getHeading());
            packet.put("left enc:", drive.getWheelPositions().get(0));
            packet.put("right enc:", drive.getWheelPositions().get(1));

            fieldOverlay.setStroke("green");
            DashboardUtil.drawSampledTrajectory(fieldOverlay, trajectory);

            fieldOverlay.setFill("blue");
            fieldOverlay.fillCircle(currentPose.getX(), currentPose.getY(), 3);

            dashboard.sendTelemetryPacket(packet);

            follower.update(currentPose);
            drive.updatePoseEstimate();
        }
    }
}
