package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.HardwareRobot;

import static org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity.TeamColor;
import static org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity.TeamLocation;
import static org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity.TimeDelay;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by Dristi Patel on 11/20/2017.
 */

public class AutonomousRobot extends LinearOpMode {

    enum AutoPosition {

        BLUE_CLOSE, BLUE_FAR, RED_CLOSE, RED_FAR, UNKNOWN

    }

    HardwareRobot robot;
    ElapsedTime runTime;
    ImuSensor imu;
    Vuforia vuforia;
    RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;

    AutoPosition location = AutoPosition.UNKNOWN;

    @Override
    public void runOpMode() {

        location = AutoPosition.UNKNOWN;

        robot = new HardwareRobot(hardwareMap);
        runTime = new ElapsedTime();
        imu = new ImuSensor(hardwareMap);
        vuforia = new Vuforia(hardwareMap);

        location = (CalculateLocation());

    }

    AutoPosition CalculateLocation() {

        if (TeamColor.equals("Red")) {

            if (TeamLocation.equals("Close")) {

                return AutoPosition.RED_CLOSE;

            } else if (TeamLocation.equals("Far")) {

                return AutoPosition.RED_FAR;

            }

        } else if (TeamColor.equals("Blue")) {

            if (TeamLocation.equals("Close")) {

                return AutoPosition.BLUE_CLOSE;

            } else if (TeamLocation.equals("Far")) {

                return AutoPosition.BLUE_FAR;

            }

        }

        return AutoPosition.UNKNOWN;

    }

    void StartMovement() {

        runTime.reset();

        WaitAbsolute(TimeDelay);

    }

    void WaitAbsolute(double seconds) {

        while (runTime.seconds() <= seconds && opModeIsActive()) {

            telemetry.addData("Time Remaining", Math.ceil(seconds - runTime.seconds()));
            //telemetry.update();

            idle();

        }

    }

    void WaitFor(double seconds) {

        WaitAbsolute(getNewTime(seconds));

    }

    double getNewTime(double addedSeconds) {

        return runTime.seconds() + addedSeconds;

    }

    private static final double COUNTS_PER_MOTOR_REV = 288;
    private static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_INCHES = 3.54;     // For figuring circumference
    private static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    void CheckJewel() {

        robot.jewelServoLift.setPosition(1);
        robot.jewelServoTurn.setPosition(0.42);

        robot.colorSensor.enableLed(true);

        WaitFor(1.5);

        if (IsOurColor()) {

            robot.jewelServoTurn.setPosition(1);

            WaitFor(0.5);

            robot.ResetServoPositions();

            WaitFor(0.5);

        } else {


            robot.jewelServoTurn.setPosition(0);

            WaitFor(0.5);

            robot.ResetServoPositions();

            WaitFor(0.5);

        }

        robot.colorSensor.enableLed(false);

    }

    boolean IsOurColor() {

        if (robot.colorSensor.red() >= 30 && robot.colorSensor.blue() <= 30) {

            // Red Jewel Detected
            if (location == AutoPosition.RED_CLOSE || location == AutoPosition.RED_FAR) {

                // We are red
                return true;

            } else {

                // We are blue
                return false;

            }

        } else if (robot.colorSensor.blue() >= 15 && robot.colorSensor.red() <= 30) {

            // Blue Jewel Detected
            if (location == AutoPosition.RED_CLOSE || location == AutoPosition.RED_FAR) {

                // We are red
                return false;

            } else {

                // We are blue
                return true;

            }

        }

        robot.jewelServoLift.setPosition(0.55);
        robot.jewelServoTurn.setPosition(0.57);

        WaitFor(0.5);

        robot.jewelServoLift.setPosition(1);

        WaitFor(1.5);

        if (robot.colorSensor.red() >= 30 && robot.colorSensor.blue() <= 30) {

            robot.jewelServoLift.setPosition(0.55);
            robot.jewelServoTurn.setPosition(0.6);

            WaitFor(0.5);

            robot.jewelServoLift.setPosition(1);
            robot.jewelServoTurn.setPosition(0.42);

            WaitFor(0.5);

            // Red Jewel Detected
            if (location == AutoPosition.RED_CLOSE || location == AutoPosition.RED_FAR) {

                return false;

            } else {

                return true;

            }

        } else if (robot.colorSensor.blue() >= 15 && robot.colorSensor.red() <= 30) {

            robot.jewelServoLift.setPosition(0.55);
            robot.jewelServoTurn.setPosition(0.6);

            WaitFor(0.5);

            robot.jewelServoLift.setPosition(1);
            robot.jewelServoTurn.setPosition(0.42);

            WaitFor(0.5);

            // Blue Jewel Detected
            if (location == AutoPosition.RED_CLOSE || location == AutoPosition.RED_FAR) {

                return true;

            } else {

                return false;

            }

        }

        robot.ResetServoPositions();

        WaitFor(0.5);

        return true;

    }

    void EncoderDrive(double distance, double speed, double timeout) {

        robot.ResetDriveEncoders();

        robot.backLeft.setPower(-Math.abs(speed) * (distance / Math.abs(distance)));
        robot.backRight.setPower(-Math.abs(speed) * (distance / Math.abs(distance)));
        robot.frontLeft.setPower(-Math.abs(speed) * (distance / Math.abs(distance)));
        robot.frontRight.setPower(-Math.abs(speed) * (distance / Math.abs(distance)));

        double timeToStop = getNewTime(timeout);

        while (Math.abs((distance * COUNTS_PER_INCH) - ((robot.backLeft.getCurrentPosition() + robot.backRight.getCurrentPosition() +
                robot.frontLeft.getCurrentPosition() + robot.frontRight.getCurrentPosition()) / -4)) > 10
                && runTime.seconds() <= timeToStop && opModeIsActive()) {


            telemetry.addData("Target position", (distance * COUNTS_PER_INCH))
                    .addData("Distance to go", Math.abs((distance * COUNTS_PER_INCH) - ((robot.backLeft.getCurrentPosition() + robot.backRight.getCurrentPosition() +
                            robot.frontLeft.getCurrentPosition() + robot.frontRight.getCurrentPosition()) / -4)))
                    .addData("Average Encoder Position", ((robot.backLeft.getCurrentPosition() + robot.backRight.getCurrentPosition() +
                            robot.frontLeft.getCurrentPosition() + robot.frontRight.getCurrentPosition()) / -4))
                    .addData("Encoders", "Left: %d, Right: %d", robot.backLeft.getCurrentPosition(), robot.backRight.getCurrentPosition())
                    .addData("Encoders", "Front Left: %d, Front Right: %d", robot.frontLeft.getCurrentPosition(), robot.frontRight.getCurrentPosition());
            telemetry.update();

            idle();

        }
/*
        robot.backLeft.setPower(Math.abs(speed) * (distance / Math.abs(distance)));
        robot.backRight.setPower(Math.abs(speed) * (distance / Math.abs(distance)));
        robot.frontLeft.setPower(Math.abs(speed) * (distance / Math.abs(distance)));
        robot.frontRight.setPower(Math.abs(speed) * (distance / Math.abs(distance)));
*/
        WaitFor(0.25);

        robot.StopDriveMotors();

    }

    void AbsoluteTurn(double angle, double speed, double timeout) {

        //TurnMovement();

        robot.backLeft.setPower(speed);
        robot.backRight.setPower(speed);
        robot.frontLeft.setPower(speed);
        robot.frontRight.setPower(speed);

        double timeToStop = getNewTime(timeout);

        while (Math.abs(imu.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - angle) > 5 && runTime.seconds() <= timeToStop && opModeIsActive()) {

            telemetry.addData("Target Angle", angle)
                    .addData("Distance to go", Math.abs(imu.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - angle))
                    .addData("Current Angle", imu.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
            telemetry.update();

            idle();

        }
        /*
        robot.backLeft.setPower(-speed);
        robot.backRight.setPower(-speed);
        robot.frontLeft.setPower(-speed);
        robot.frontRight.setPower(-speed);
*/
        WaitFor(0.25);

        robot.StopDriveMotors();

    }

    void RelativeTurn(double angle, double speed, double timeout) {

        double turnTo = angle + imu.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

        if (turnTo >= 180)
            turnTo -= 360;
        else if (turnTo <= -180)
            turnTo += 360;

        AbsoluteTurn(turnTo, speed, timeout);

    }

    void PlaceGlyph() {


        robot.leftGrab.setPower(.4);
        robot.rightGrab.setPower(.4);

        WaitFor(2);

        robot.leftGrab.setPower(0);
        robot.rightGrab.setPower(0);




    }

    void GrabGlyph() {

        robot.liftDrive.setPower(0.6);

        WaitFor(0.5);

        robot.liftDrive.setPower(-0.4);

        WaitFor(0.25);

        robot.liftDrive.setPower(0);

        WaitFor(1);

        robot.leftGrab.setPower(-0.6);
        robot.rightGrab.setPower(-0.6);

        robot.liftDrive.setPower(0.5);

        WaitFor(0.5);

        robot.leftGrab.setPower(0);
        robot.rightGrab.setPower(0);

        while (robot.liftDrive.getCurrentPosition() < 1350) {

            telemetry.addData("Lift", "Position: %d Target 1050", robot.liftDrive.getCurrentPosition());
            telemetry.update();

        }

        robot.liftDrive.setPower(0);

    }

    void GetVuMark() {

        vuMark = vuforia.getVuMark();

    }

}

