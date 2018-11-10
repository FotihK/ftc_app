package org.firstinspires.ftc.team2981.systems;

import com.acmerobotics.roadrunner.drive.TankDrive;
import com.qualcomm.hardware.motors.NeveRest20Gearmotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RobotDrive extends TankDrive {

    private static final double TRACK_WIDTH = 1;
    public static final double WHEEL_RADIUS = 2;
    private static final double GEAR_RATIO = 1;         //wheel/motor

    public static final MotorConfigurationType MOTOR_CONFIG = MotorConfigurationType.getMotorType(NeveRest20Gearmotor.class);
    private static final double TICKS_PER_REV = MOTOR_CONFIG.getTicksPerRev();

    public static final PIDFCoefficients NORMAL_PID = new PIDFCoefficients(20, 8, 12, 0);

    private DcMotorEx left1, left2, right1, right2;
    private List<DcMotorEx> motors;

    public RobotDrive(HardwareMap map){
        super(TRACK_WIDTH);

        left1 = map.get(DcMotorEx.class, "L1");
        right1 = map.get(DcMotorEx.class, "R1");
        left2 = map.get(DcMotorEx.class, "L2");
        right2 = map.get(DcMotorEx.class, "R2");

        motors = Arrays.asList(left1, left2, right1, right2);

        for(DcMotorEx motor : motors){
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, NORMAL_PID);
        }

        right1.setDirection(DcMotorSimple.Direction.REVERSE);
        right2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private static double ticksToInches(int ticks){
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks/TICKS_PER_REV;
    }


    @NotNull
    @Override
    public List<Double> getWheelPositions() {
        List<Double> wheelPositions = new ArrayList<>();
        for(DcMotorEx motor : motors){
            wheelPositions.add(ticksToInches(motor.getCurrentPosition()));
        }
        return wheelPositions;
    }

    @Override
    public void setMotorPowers(double v1, double v2) {
        left1.setPower(v1);
        left2.setPower(v1);
        right1.setPower(v2);
        right2.setPower(v2);
    }
}
