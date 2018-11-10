package org.firstinspires.ftc.team2981.systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private static final double NOODLE_POWER = 0.8;

    private CRServo noodle;

    public Intake(HardwareMap map){
        noodle = map.get(CRServo.class, "Noodle");
    }


    public void noodleForward(){
        noodle.setPower(NOODLE_POWER);
    }

    public void noodleReverse(){
        noodle.setPower(-NOODLE_POWER);
    }

    public void noodleStop(){
        noodle.setPower(0);
    }
}
