package org.firstinspires.ftc.team2981.old;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="telecomms online uwu")
public class tele extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap);
        double barsense = 2.5;
        double dsense = 2;
        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            double left = -gamepad1.left_stick_y;
            double right = -gamepad1.right_stick_y;

            left = Math.abs(left) > 0.05 ? left/dsense : 0;
            right = Math.abs(right) > 0.05 ? right/dsense : 0;

            robot.drive.setMotorPowers(left, right);

            if(gamepad2.a) robot.fbar.closeClaw();
            else if(gamepad2.b) robot.fbar.openClaw();

            double barP = -gamepad2.left_stick_y;
            if(Math.abs(barP) <= 0.95){
                barP = (Math.abs(barP) > 0.05 ? barP/barsense : 0);
            }
            robot.fbar.powerBar(barP);
        }
    }
}
