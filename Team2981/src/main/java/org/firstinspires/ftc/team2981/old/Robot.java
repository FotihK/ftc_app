package org.firstinspires.ftc.team2981.old;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team2981.old.systems.FourBar;
import org.firstinspires.ftc.team2981.old.systems.RobotDrive;
import org.firstinspires.ftc.team2981.systems.*;

public class Robot {
    public RobotDrive drive;
    //public MCUnit mc;
    public Servo marker;
    public FourBar fbar;

    public Robot(HardwareMap map){
        drive = new RobotDrive(map);
    //  mc = new MCUnit(map);
        marker = map.get(Servo.class, "marker");
        fbar = new FourBar(map);
    }


}
