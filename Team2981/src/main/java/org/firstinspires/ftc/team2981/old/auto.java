package org.firstinspires.ftc.team2981.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name="FeelsAutoMan")
public class auto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap);
        //robot.fbar.powerBar(-0.5);
        waitForStart();
        //robot.fbar.powerBar(0.5);
        //sleep(1000);
        //robot.fbar.powerBar(0);
        //robot.drive.setMotorPowers(-0.5,0.5);
        //sleep(1000);
        //robot.drive.stop();
        //robot.drive.setMotorPowers(0.5,-0.5);
        //sleep(1000);
        //robot.drive.stop();
        robot.drive.setMotorPowers(-0.5,-0.5);
        sleep(5000);
        robot.drive.stop();

    }
}
