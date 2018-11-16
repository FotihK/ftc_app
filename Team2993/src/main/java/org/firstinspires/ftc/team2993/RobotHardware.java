package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.hardware.*;



public class RobotHardware
{
    public DcMotorEx fL, fR;
    public DcMotorEx lift;
    public DcMotorEx intakeE;

    public CRServo intake;



    private HardwareMap map;



    public RobotHardware(HardwareMap map)
    {
        this.map = map;

        init();
    }



    public void init()
    {
        fL = map.get(DcMotorEx.class, "fl");
        fR = map.get(DcMotorEx.class, "fr");
        lift = map.get(DcMotorEx.class, "lift");
        intakeE = map.get(DcMotorEx.class, "intakee");

        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        fR.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeE.setDirection(DcMotorSimple.Direction.FORWARD);



        intake = map.get(CRServo.class, "intake");
    }



    public void Drive(double power)
    {
        Drive(power, power);
    }

    public void Drive(double lPower, double rPower)
    {
        fL.setPower(lPower);
        fR.setPower(rPower);
    }



    public void Stop()
    {
        Drive(0d);
    }

    public boolean DriveIsBusy()
    {
        return fL.isBusy() || fR.isBusy();
    }
}
