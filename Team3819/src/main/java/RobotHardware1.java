package org.firstinspires.ftc.team3819.Structural;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by 200462069 on 9/12/2017.
 */

public class RobotHardware1 {

    private HardwareMap map = null;

    public DcMotor  fL = null, fR = null, bL = null, bR = null;    //DC Motors

    private static final int       CPR = 1120;                                 //encoder counts per revolution
    private static final double    DIAMETER = 4;                               //encoded drive wheel diameter (in)
    private static final double    GEARING = 1;
    public static final double     CPI = (CPR * GEARING) / (DIAMETER * 3.14);
    public static final double     CPF = CPI * 12;

    public RobotHardware1(HardwareMap map){
        this.map = map;
    }

    public void init() {
        fL.setDirection(DcMotorSimple.Direction.FORWARD);
        fR.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.FORWARD);
        bR.setDirection(DcMotorSimple.Direction.REVERSE);

        fL = map.get(DcMotor.class, "fL");
        fR = map.get(DcMotor.class, "fR");
        bL = map.get(DcMotor.class, "bL");
        bR = map.get(DcMotor.class, "bR");

    /*
        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        resetEnc();
        */
    }

    public void drive(int num) {
        fL.setPower(num);
        bL.setPower(num);
        fR.setPower(num);
        bR.setPower(num);
    }

    public void drive(Gamepad gp) {
        fL.setPower(gp.left_stick_y);
        bL.setPower(gp.left_stick_y);
        fR.setPower(gp.right_stick_y);
        bR.setPower(gp.right_stick_y);
    }

    public void stop() {
        drive(0);
    }

    public boolean driveIsBusy(){
        return fL.isBusy() || fR.isBusy();
    }

    public void resetEnc(){
        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }



}
