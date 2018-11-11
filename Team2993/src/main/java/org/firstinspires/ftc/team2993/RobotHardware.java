package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.hardware.*;



public class RobotHardware
{
    public DcMotorEx fL, fR;
    public DcMotorEx lift;
    public DcMotorEx eL, eR;
    public DcMotorEx intake, intakeE;



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
        eL = map.get(DcMotorEx.class, "el");
        eR = map.get(DcMotorEx.class, "er");
        intake = map.get(DcMotorEx.class, "intake");
        intakeE = map.get(DcMotorEx.class, "intakee");

        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        fR.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        eL.setDirection(DcMotorSimple.Direction.REVERSE);
        eR.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeE.setDirection(DcMotorSimple.Direction.FORWARD);
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

    public void SetElevator(double power)
    {
        eL.setPower(power);
        eR.setPower(power);
    }



    /*
    public void MoveToPos(double power, double position)
    {
        fIn.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        bIn.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        fIn.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        fIn.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);


    }



    public void WaitForEncoders()
    {
        while (DriveIsBusy());
    }*/



    public void Stop()
    {
        Drive(0d);
    }

    public boolean DriveIsBusy()
    {
        return fL.isBusy() || fR.isBusy();
    }
}
