package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 200462069 on 9/12/2017.
 */

public class Hardware {

    private HardwareMap map = null;

    public DcMotorEx  left = null, right = null, slide = null, intake = null;    //DC Motors

    private static final int       CPR = 1120;                                 //encoder counts per revolution
    private static final double    DIAMETER = 4;                               //encoded drive wheel diameter (in)
    private static final double    GEARING = 1;
    public static final double     CPI = (CPR * GEARING) / (DIAMETER * 3.14);
    public static final double     CPF = CPI * 12;

    public Hardware(HardwareMap map){
        this.map = map;

        left = map.get(DcMotorEx.class, "left");
        right = map.get(DcMotorEx.class, "right");
        slide = map.get(DcMotorEx.class, "slide");
        intake = map.get(DcMotorEx.class, "intake");


        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }


    public void drive(int num) {
        left.setPower(num);
        right.setPower(num);
    }

    public void drive(Gamepad gp) {
        float turn = gp.right_stick_x/2;
        left.setPower(gp.left_stick_y + turn);
        right.setPower(gp.left_stick_y - turn);
    }

    public void driveInches(int pow, int in) {
        resetEnc();

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setTargetPosition((int)(in*CPI));
        right.setTargetPosition((int)(in*CPI));

        left.setPower(pow);
        right.setPower(pow);
    }


    public void encoderTest() {
        //resetEnc();
        right.setTargetPosition(10000);

        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        right.setPower(75);
    }

    public void slideUp() {


       slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

       slide.setTargetPosition(100);

       slide.setPower(10);
    }

    public void slideDown() {

       slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

       slide.setTargetPosition(-100);

       slide.setPower(-10);
    }

    public void up() {
        slide.setPower(10);
    }

    public void down() {
        slide.setPower(-10);
    }

    public void slideStop() {
        slide.setPower(0);
    }

    public void intake() {
        intake.setPower(75);
    }

    public void outtake() {
        intake.setPower(-75);
    }

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
