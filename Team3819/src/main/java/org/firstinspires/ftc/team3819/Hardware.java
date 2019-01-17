package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

/**
 * Created by 200462069 on 9/12/2017.
 */

public class Hardware {

    private HardwareMap map = null;

    public DcMotorEx  left = null, right = null, slide = null, intake = null, liftFront = null, liftBack = null, liftSlide = null, armLeft = null, armRight = null;    //DC Motors
    public DcMotorControllerEx motorControllerEx = null;

    public static final double     PI  =  3.14159;
    public static final int       CPR = 1680;                                 //encoder counts per revolution
    private static final double    DIAMETER = 4;                               //encoded drive wheel diameter (in)
    private static final double    GEARING = 1;
    public static final double     CPI = (CPR * GEARING) / (DIAMETER * PI);
    public static final double     CPF = CPI * 12;
    public static final double     TURNING_RADIUS = 7.5;
    public static final double     CIRCUMFRENCE = TURNING_RADIUS * 2 * PI;


    public Hardware(HardwareMap map){
        this.map = map;

        left = (DcMotorEx)map.get(DcMotor.class, "left");
        right = (DcMotorEx)map.get(DcMotor.class, "right");
        slide = (DcMotorEx)map.get(DcMotor.class, "slide");
        intake = (DcMotorEx)map.get(DcMotor.class, "intake");
        liftFront = (DcMotorEx)map.get(DcMotor.class, "liftFront");
        liftBack = (DcMotorEx)map.get(DcMotor.class, "liftBack");
        liftSlide = (DcMotorEx)map.get(DcMotorEx.class, "liftSlide");
        armLeft = (DcMotorEx)map.get(DcMotorEx.class, "armLeft");
        armRight = (DcMotorEx)map.get(DcMotorEx.class, "armRight");

        right.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        liftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection((DcMotorSimple.Direction.REVERSE));
        armRight.setDirection((DcMotorSimple.Direction.REVERSE));

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    public void drive(int num) {
        left.setPower(num);
        right.setPower(num);
    }

    public void winch(float f) {
        liftFront.setPower(f);
        liftBack.setPower(f);
    }

    public void drive(Gamepad gp) {
        double turn = 0;
        if(Math.abs(gp.right_stick_x)>=.05 || Math.abs(gp.right_stick_y)>=.05) {
            turn = gp.right_stick_x;
            left.setPower(-1 * gp.right_stick_y - turn);
            right.setPower(-1 * gp.right_stick_y + turn);
        }
        else {
            left.setPower(0);
            right.setPower(0);
        }
    }

    public void driveInches(double pow, int in) {
        resetEnc();
        motorControllerEx = (DcMotorControllerEx)left.getController();
        PIDFCoefficients pidfNew = new PIDFCoefficients(128, 40, 192, 57);
        int motorIndexL = ((DcMotorEx)left).getPortNumber();
        int motorIndexR = ((DcMotorEx)left).getPortNumber();

        int target = (int)(in*CPI);

        //motorControllerEx.setPIDFCoefficients(motorIndexL,DcMotor.RunMode.RUN_TO_POSITION,pidfNew);
        //motorControllerEx.setPIDFCoefficients(motorIndexR,DcMotor.RunMode.RUN_TO_POSITION,pidfNew);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setTargetPosition(target);
        right.setTargetPosition(target);

        //left.setDirection(DcMotorSimple.Direction.REVERSE);
        //right.setDirection(DcMotorSimple.Direction.FORWARD);

        //while( (left.getCurrentPosition()>target + 10||left.getCurrentPosition()<target-10) &&
          //      (right.getCurrentPosition()>target + 10||right.getCurrentPosition()<target-10) ) {
            left.setPower(pow);
            right.setPower(pow);

        //left.setDirection(DcMotorSimple.Direction.FORWARD);
        //right.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void turn(double pow, double degrees) {
        resetEnc();

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setTargetPosition(-1*(int)((360.0/degrees)*CIRCUMFRENCE*CPI));
        right.setTargetPosition((int)((360.0/degrees)*CIRCUMFRENCE*CPI));

        left.setPower(pow);
        right.setPower(pow);
    }

    public void armUp() {
        armLeft.setPower(.75);
        armRight.setPower(.75);
    }

    public void armDown() {
        armLeft.setPower(-.1);
        armRight.setPower(-.1);
    }

    public  void armStop() {
        armLeft.setPower(0);
        armRight.setPower(0);
    }

    public void intake() {intake.setPower(.75); }

    public void intake(float f) {intake.setPower(f); }

    public void outtake() {intake.setPower(-.75); }

    public void outtake(float f) {intake.setPower(-f); }

    public void donttake() {
        intake.setPower(0);
    }

    public void stop() {
        drive(0);
    }

    public boolean driveIsBusy(){
        return left.isBusy() || right.isBusy();
    }

    public boolean isBusy(){
        return left.isBusy() || right.isBusy();
    }

    public void resetEnc(){
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


}
