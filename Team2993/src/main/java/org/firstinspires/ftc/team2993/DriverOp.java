package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.*;



@TeleOp(name = "TeleOp - 2993", group="main")
public class DriverOp extends OpMode
{
    RobotHardware robot;

    public final double threshold = .1d;

    public final Power liftPower = new Power(.7d, -1d);

    public final double intakePower = 1d;



    @Override
    public void init()
    {
        robot = new RobotHardware(hardwareMap);
    }



    private void driverOne()
    {
        // Drive train

        if (Math.abs(gamepad1.left_stick_y) > threshold)
            robot.fL.setPower(gamepad1.left_stick_y > .8 ? 1 : gamepad1.left_stick_y);
        else
            robot.fL.setPower(0);

        if (Math.abs(gamepad1.right_stick_y) > threshold)
            robot.fR.setPower(gamepad1.right_stick_y > .8 ? 1 : gamepad1.right_stick_y);
        else
            robot.fR.setPower(0);



        // Lift

        if (gamepad1.dpad_up)
            robot.lift.setPower(liftPower.up);
        else if (gamepad1.dpad_down)
            robot.lift.setPower(liftPower.down);
        else
            robot.lift.setPower(0);
    }

    private void driverTwo()
    {
        // Intake

        if (gamepad2.y)
            robot.intake.setPower(intakePower);
        else if (gamepad2.a)
            robot.intake.setPower(-intakePower);
        else if (gamepad2.b || gamepad2.x)
            robot.intake.setPower(0);



        // Intake elevator

        if (Math.abs(gamepad2.left_stick_y) > threshold)
            robot.intakeE.setPower(gamepad2.left_stick_y);
        else
            robot.intakeE.setPower(0);
    }



    @Override
    public void loop() {
        init();
        driverOne();
        driverTwo();
    }
}



