package org.firstinspires.ftc.team2981.old.systems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FourBar {
    private DcMotorEx motor;
    private Servo claw;
    private double clawOpen = 0.05;
    private double clawClose = 0.9;

    public FourBar(HardwareMap map){
        motor = map.get(DcMotorEx.class, "4bar");
        claw = map.get(Servo.class, "claw");
    }

    public void powerBar(double val){
        motor.setPower(val);
    }

    public void openClaw(){
        claw.setPosition(clawOpen);
    }

    public void closeClaw(){
        claw.setPosition(clawClose);
    }



}
