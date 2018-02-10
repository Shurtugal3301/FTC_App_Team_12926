package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by Dristi on 9/15/2017.
 */
@Autonomous(name = "Autonomous", group = "Autonomous")
public class Auto1 extends AutonomousRobot {





    //  RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;

    ElapsedTime runTime = new ElapsedTime();
    ElapsedTime waitTime = new ElapsedTime();
    @Override
    public void runOpMode() {

        super.runOpMode();

        //TELEMETRY SETUP ------------------------

        //display the timer on the telemetry
        if (location == AutoPosition.UNKNOWN) {

            telemetry.addData("ERROR", "Location not set, please verify that the location has been chosen");

        } else {

            telemetry.addData("Location", location.toString());

        }

        telemetry.update();

        robot.jewelServoLift.setPosition(0);

        //turn off the color sensor LED
        robot.colorSensor.enableLed(false);

        GetVuMark();

        //wait for the start button to be pressed
        waitForStart();

        if (location == AutoPosition.UNKNOWN) {

            location = (CalculateLocation());

        }

        GetVuMark();

        robot.jewelServoLift.setPosition(0.4);

        GrabGlyph();

        robot.ResetServoPositions();

        //Place the JewelServo down, check the jewel color, knock off the correct jewel, and put the JewelServo back up
        CheckJewel();

        WaitFor(1);

        if (location == AutoPosition.RED_FAR || location == AutoPosition.RED_CLOSE) {

            // Move forward
            robot.frontLeft.setPower(0.5);
            robot.frontRight.setPower(-0.5);
            robot.backLeft.setPower(0.5);
            robot.backRight.setPower(-0.5);

            WaitFor(0.7);

            robot.StopDriveMotors();

        } else if (location == AutoPosition.BLUE_CLOSE || location == AutoPosition.BLUE_FAR) {

            // Move forward
            robot.frontLeft.setPower(-0.5);
            robot.frontRight.setPower(0.5);
            robot.backLeft.setPower(-0.5);
            robot.backRight.setPower(0.5);

            WaitFor(0.75);

            robot.StopDriveMotors();

            WaitFor(0.7);

            // Turn right
            robot.frontLeft.setPower(-0.4);
            robot.frontRight.setPower(-0.4);
            robot.backLeft.setPower(-0.4);
            robot.backRight.setPower(-0.4);

            WaitFor(1.25);

            robot.StopDriveMotors();

        }

        WaitFor(1);

        switch (location) {

            case BLUE_CLOSE:
                blueCloseDrive();
                break;
            case BLUE_FAR:
                blueFarDrive();
                break;
            case RED_CLOSE:
                redCloseDrive();
                break;
            case RED_FAR:
                redFarDrive();
                break;

        }

        WaitFor(0.7);

        if (vuMark == RelicRecoveryVuMark.LEFT) {

            // Strafe left
            robot.frontLeft.setPower(0.4);
            robot.frontRight.setPower(0.4);
            robot.backLeft.setPower(-0.4);
            robot.backRight.setPower(-0.4);

            WaitFor(0.2);

            robot.StopDriveMotors();

            WaitFor(0.7);

        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {

            // Strafe right
            robot.frontLeft.setPower(-0.4);
            robot.frontRight.setPower(-0.4);
            robot.backLeft.setPower(0.4);
            robot.backRight.setPower(0.4);

            WaitFor(0.2);

            robot.StopDriveMotors();

            WaitFor(0.7);

        }

        // Move forward
        robot.frontLeft.setPower(0.5);
        robot.frontRight.setPower(-0.5);
        robot.backLeft.setPower(0.5);
        robot.backRight.setPower(-0.5);

        WaitFor(0.3);

        robot.StopDriveMotors();

        // Release glyph
        robot.leftGrab.setPower(0.6);
        robot.rightGrab.setPower(0.6);

        WaitFor(0.4);

        robot.frontLeft.setPower(-0.5);
        robot.frontRight.setPower(0.5);
        robot.backLeft.setPower(-0.5);
        robot.backRight.setPower(0.5);

        WaitFor(0.3);

        robot.leftGrab.setPower(0);
        robot.rightGrab.setPower(0);

        robot.StopDriveMotors();

        WaitFor(0.5);

        // Turn right
        robot.frontLeft.setPower(-0.4);
        robot.frontRight.setPower(-0.4);
        robot.backLeft.setPower(-0.4);
        robot.backRight.setPower(-0.4);

        WaitFor(1.35);

        robot.StopDriveMotors();

        WaitFor(0.5);

        robot.frontLeft.setPower(-0.5);
        robot.frontRight.setPower(0.5);
        robot.backLeft.setPower(-0.5);
        robot.backRight.setPower(0.5);

        if (location != AutoPosition.BLUE_CLOSE && location != AutoPosition.RED_CLOSE) {

            WaitFor(0.3);

        } else {

            WaitFor(0.37);

        }

        robot.StopDriveMotors();

        WaitFor(0.5);

        if (vuMark != RelicRecoveryVuMark.LEFT && vuMark != RelicRecoveryVuMark.RIGHT) {

            robot.frontLeft.setPower(0.4);
            robot.frontRight.setPower(0.4);
            robot.backLeft.setPower(-0.4);
            robot.backRight.setPower(-0.4);

            WaitFor(0.2);

            robot.StopDriveMotors();

        }

        while (robot.liftDrive.getCurrentPosition() > 50) {

            robot.liftDrive.setPower(-0.3);

        }

        robot.liftDrive.setPower(0);

    }
    private void blueCloseDrive() {

        robot.frontLeft.setPower(-0.4);
        robot.frontRight.setPower(0.4);
        robot.backLeft.setPower(-0.4);
        robot.backRight.setPower(0.4);

        WaitFor(0.2);

        robot.StopDriveMotors();

        WaitFor(0.7);

        // Move diagonal (front right)
        robot.frontLeft.setPower(0.2);
        robot.frontRight.setPower(-0.6);
        robot.backLeft.setPower(0.6);
        robot.backRight.setPower(-0.2);

        WaitFor(0.55);

        robot.StopDriveMotors();

        WaitFor(1);

        // Turn right
        robot.frontLeft.setPower(0.4);
        robot.frontRight.setPower(0.4);
        robot.backLeft.setPower(0.4);
        robot.backRight.setPower(0.4);

        WaitFor(0.5);

        robot.StopDriveMotors();
/*
        WaitFor(0.5);

        // Strafe right
        robot.frontLeft.setPower(-0.4);
        robot.frontRight.setPower(-0.4);
        robot.backLeft.setPower(0.4);
        robot.backRight.setPower(0.4);

        WaitFor(0.12);

        robot.StopDriveMotors();
*/
        WaitFor(0.7);

        robot.frontLeft.setPower(0.4);
        robot.frontRight.setPower(-0.4);
        robot.backLeft.setPower(0.4);
        robot.backRight.setPower(-0.4);

        WaitFor(0.35);

        robot.StopDriveMotors();

        WaitFor(0.7);

    }


    private void blueFarDrive() {

        robot.frontLeft.setPower(-0.4);
        robot.frontRight.setPower(0.4);
        robot.backLeft.setPower(-0.4);
        robot.backRight.setPower(0.4);

        WaitFor(0.15);

        robot.StopDriveMotors();

        WaitFor(0.7);

        // Strafe right
        robot.frontLeft.setPower(-0.4);
        robot.frontRight.setPower(-0.4);
        robot.backLeft.setPower(0.4);
        robot.backRight.setPower(0.4);

        WaitFor(0.25);

        robot.StopDriveMotors();

        WaitFor(0.7);

    }


    private void redCloseDrive() {

        // Move diagonal (front left)
        robot.frontLeft.setPower(0.6);
        robot.frontRight.setPower(-0.2);
        robot.backLeft.setPower(0.2);
        robot.backRight.setPower(-0.6);

        WaitFor(0.7);

        robot.StopDriveMotors();

        WaitFor(1);

        // Turn right
        robot.frontLeft.setPower(-0.4);
        robot.frontRight.setPower(-0.4);
        robot.backLeft.setPower(-0.4);
        robot.backRight.setPower(-0.4);

        WaitFor(0.675);

        robot.StopDriveMotors();

        WaitFor(0.5);

        // Move forward
        robot.frontLeft.setPower(0.5);
        robot.frontRight.setPower(-0.5);
        robot.backLeft.setPower(0.5);
        robot.backRight.setPower(-0.5);

        WaitFor(0.2);

        robot.StopDriveMotors();

        WaitFor(0.5);

    }

    private void redFarDrive() {

        // Strafe left
        robot.frontLeft.setPower(0.4);
        robot.frontRight.setPower(0.4);
        robot.backLeft.setPower(-0.4);
        robot.backRight.setPower(-0.4);

        WaitFor(0.3);

        robot.StopDriveMotors();

        WaitFor(0.7);

    }


}




