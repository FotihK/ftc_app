package org.firstinspires.ftc.team2981;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends LinearOpMode {
    Robot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        boolean toggle = false;
        boolean toggle1 = false;
        boolean on = false;
        boolean forward = false;
        boolean reverse = false;
        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            double left = -gamepad1.left_stick_y;
            double right = -gamepad1.right_stick_y;

            left = Math.abs(left) > 0.05 ? left : 0;
            right = Math.abs(right) > 0.05 ? right : 0;

            robot.drive.setMotorPowers(left, right);

            if(gamepad2.dpad_up && !toggle1){
                if(!on || !forward){
                    robot.intake.noodleForward();
                    on = true;
                    forward = true;
                } else {
                    robot.intake.noodleStop();
                    on = false;
                    forward = false;
                }
                toggle1 = true;
            } else if(!gamepad2.dpad_up){
                toggle1 = false;
            }

            if(gamepad2.dpad_down && !toggle){
                if(!on || !reverse){
                    robot.intake.noodleReverse();
                    reverse = true;
                    on = true;
                } else {
                    robot.intake.noodleStop();
                    reverse  = false;
                    on = false;
                }
                toggle = true;
            } else if(!gamepad2.dpad_down){
                toggle = false;
            }

        }
    }
}
