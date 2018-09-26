package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.hardware.*;



public class RobotHardware
{
    public DcMotor l;
    public DcMotor r;

    private HardwareMap map;



    public RobotHardware(HardwareMap map)
    {
        this.map = map;

        init();
    }



    public void init()
    {
        l = map.get(DcMotor.class, "l");
        r = map.get(DcMotor.class, "r");

        l.setDirection(DcMotorSimple.Direction.FORWARD);
        r.setDirection(DcMotorSimple.Direction.FORWARD);
    }



    public void Drive(double power)
    {
        Drive(power, power);
    }

    public void Drive(double lPower, double rPower)
    {
        l.setPower(lPower);
        r.setPower(rPower);
    }



    public void Stop()
    {
        Drive(0d);
    }

    public boolean DriveIsBusy()
    {
        return l.isBusy() || r.isBusy();
    }
}
