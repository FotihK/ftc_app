package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.hardware.*;



public class RobotHardware
{
    public DcMotorEx fL, fR;
    public DcMotorEx lift;
    public DcMotorEx intake, intakeE;



    public static final int        CPR = 1680;                                 //encoder counts per revolution
    private static final double    DIAMETER = 4;                               //encoded drive wheel diameter (in)
    private static final double    GEARING = .5;
    public static final double     CPI = (CPR * GEARING) / (DIAMETER * Math.PI);
    public static final double     TURNING_RADIUS = 11.5;
    public static final double     CIRCUMFRENCE = TURNING_RADIUS * 2 * Math.PI;



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
        intake = map.get(DcMotorEx.class, "intake");
        intakeE = map.get(DcMotorEx.class, "intakee");

        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        fR.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeE.setDirection(DcMotorSimple.Direction.FORWARD);

        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        fL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }



    public void Drive(double power)
    {
        Drive(power, power);
    }

    public void Drive(double lPower, double rPower)
    {
        fL.setPower(-lPower);
        fR.setPower(-rPower);
    }



    public void stop()
    {
        Drive(0d);
    }

    public void resetEnc()
    {
        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public boolean DriveIsBusy()
    {
        return fL.isBusy() || fR.isBusy();
    }
}
