package org.firstinspires.ftc.team2981.old.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class MCUnit {
    private Servo claw;
    private DcMotorEx zAxis;
    private DcMotorEx rotate;
    private DcMotorEx slide;

    public MCUnit(HardwareMap map){
        claw = map.get(Servo.class, "Clawboi");
        zAxis = map.get(DcMotorEx.class, "Zaxbys");
        rotate = map.get(DcMotorEx.class, "Phiboi");
        slide = map.get(DcMotorEx.class, "Extendo");
        rotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        zAxis.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void openClaw(){
        claw.setPosition(0.45);
    }

    public void closeClaw(){
        claw.setPosition(0.8);
    }

    public void extend() {
        slide.setPower(-1);
    }

    public void slack(){
        slide.setPower(1);
    }

    public void stopSlack(){
        slide.setPower(0);
    }

    public void swivel(double val){
        //zAxis.setPower(.5 * Math.signum(val));
        zAxis.setPower(val);
    }

    public void rotate(double val) {
        //if(val != 0){
        //    rotate.setPower(.6 * Math.signum(val));
        //} else {
        //    //rotate.setPower(0.2);
        //    rotate.setPower(0);
        //}

        rotate.setPower(val);
    }

}
