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

    private DcMotorEx left, right;
    private List<DcMotorEx> motors;

    public RobotDrive(HardwareMap map){
        super(TRACK_WIDTH);

        left = map.get(DcMotorEx.class, "MotorL");
        right = map.get(DcMotorEx.class, "MotorR");

        motors = Arrays.asList(left, right);

        for(DcMotorEx motor : motors){
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, NORMAL_PID);
        }

        right.setDirection(DcMotorSimple.Direction.REVERSE);
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
        left.setPower(v1);
        right.setPower(v2);
    }
}
