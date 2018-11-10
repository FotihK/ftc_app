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

    public DcMotorEx  left = null, right = null, slide = null, intake = null;    //DC Motors

    public static final double     PI  =  3.14159;
    private static final int       CPR = 1680;                                 //encoder counts per revolution
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


        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    public void drive(int num) {
        left.setPower(num);
        right.setPower(num);
    }

    public void drive(Gamepad gp) {
        float turn = 0;
        if(Math.abs((int)(gp.right_stick_x))>=5)
            turn = gp.right_stick_x;
        if(Math.abs((int)(gp.left_stick_y))>=5) {
            left.setPower(gp.left_stick_y + turn);
            right.setPower(gp.left_stick_y - turn);
        }
    }

    public void driveInches(double pow, int in) {
        resetEnc();
        PIDFCoefficients pidfNew = new PIDFCoefficients(128, 40, 192, 57);

        int target = (int)(in*CPI);
        int dir = in >= 0 ? 1 : -1;

        left.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION,pidfNew);
        right.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION,pidfNew);

        left.setTargetPosition(target);
        right.setTargetPosition(target);

        //while( (left.getCurrentPosition()>target + 10||left.getCurrentPosition()<target-10) &&
          //      (right.getCurrentPosition()>target + 10||right.getCurrentPosition()<target-10) ) {
            left.setPower(pow*dir);
            right.setPower(pow*dir);

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


    public void encoderTest() {
        //resetEnc();
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        right.setTargetPosition(360);

        right.setPower(.75);
    }

    public void slideUp() {
       slide.setPower(.75);
    }

    public void slideDown() {
       slide.setPower(-.75);
    }

    public void slideDownEnc() {
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slide.setTargetPosition(-100);

        slide.setPower(-.1);
    }

    public void slideUpEnc() {
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slide.setTargetPosition(0);

        slide.setPower(.1);
    }

    public void slideStop() {
        slide.setPower(0);
    }

    public void intake() {intake.setPower(.75); }

    public void outtake() {intake.setPower(-.75); }

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
