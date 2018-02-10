package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by expressuser on 9/15/2017.
 */

public class HardwareRobot {

    public DcMotor frontLeft,
            frontRight,
            backLeft,
            backRight,
            leftGrab,
            rightGrab,
            liftDrive;

    public Servo jewelServoLift,
            jewelServoTurn;

    public ColorSensor colorSensor;

    public DistanceSensor distanceSensor;

    //CONSTRUCTOR - Map the hardware to the physical components configured on the robot
    public HardwareRobot(HardwareMap hwmp) {

        frontLeft = hwmp.dcMotor.get("Front Left");
        frontRight = hwmp.dcMotor.get("Front Right");
        backLeft = hwmp.dcMotor.get("Back Left");
        backRight = hwmp.dcMotor.get("Back Right");
        leftGrab = hwmp.dcMotor.get("Left Grab");
        rightGrab = hwmp.dcMotor.get("Right Grab");
        liftDrive = hwmp.dcMotor.get("Lift Drive");

        rightGrab.setDirection(DcMotorSimple.Direction.REVERSE);
        liftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        jewelServoLift = hwmp.servo.get("Jewel Servo Lift");
        jewelServoTurn = hwmp.servo.get("Jewel Servo Turn");

        colorSensor = hwmp.colorSensor.get("Color Sensor");
        distanceSensor = hwmp.get(DistanceSensor.class, "Color Sensor");

        ResetAllEncoders();
        ResetServoPositions();

    }

    public void ResetAllEncoders() {

        StopAllMotors();

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void ResetDriveEncoders() {

        StopDriveMotors();

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void ResetServoPositions() {

        jewelServoLift.setPosition(0.1);
        jewelServoTurn.setPosition(0.42);

    }

    public void StopAllMotors() {

        StopDriveMotors();

        leftGrab.setPower(0);
        rightGrab.setPower(0);
        liftDrive.setPower(0);

    }

    //Method to stop all drive motors on the robot (when time is up)
    public void StopDriveMotors() {

        backLeft.setPower(0);
        backRight.setPower(0);
        frontRight.setPower(0);
        frontLeft.setPower(0);

    }

}
