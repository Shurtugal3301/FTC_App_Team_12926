package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.HardwareRobot;

import java.util.Locale;

/**
 * Created by Thomas on 9/11/2017.
 */
@TeleOp(name = "Distance Test", group = "TeleOp")
public class TankOp2 /*extends OpMode*/ {
/*
    private enum DriveMode {

        TANK

    }

    private enum DriveSpeed {

        SLOW, FAST, SLOW_TO_FAST, FAST_TO_SLOW

    }

    private enum GrabControl {

        RELEASE, GRAB, LEFT_GRAB, RIGHT_GRAB, STOPPED

    }

    private enum LiftMovement {

        STATIC, COLLECT, FIRST, SECOND, THIRD, FOURTH, TO_COLLECT, TO_FIRST, TO_SECOND, TO_THIRD, TO_FOURTH

    }


    private HardwareRobot robot;

    private DriveMode driveMode;
    private DriveSpeed driveSpeed;
    private GrabControl grabControl;
    private LiftMovement liftControl;



    @Override
    public void init() {

        telemetry.addData("STATUS", "Initializing");
        telemetry.update();

        robot = new HardwareRobot(hardwareMap);

        driveMode = DriveMode.TANK;
        driveSpeed = DriveSpeed.FAST;
        grabControl = GrabControl.STOPPED;
        liftControl = LiftMovement.COLLECT;

        telemetry.addData("STATUS", "Ready, waiting for start");
        telemetry.update();

    }

    // Loops after the start button is pressed
    @Override
    public void loop() {


        DriveControl();

        GrabControl();

        LiftControl();

    }

    @Override
    public void stop() {

        robot.StopAllMotors();
        //robot.ResetServoPositions();

    }

    private void DriveControl() {

        CheckDriveSpeed();

        if (driveMode == DriveMode.TANK) {

            if (driveSpeed == DriveSpeed.FAST) {

                // tank mode controls for the left and right motor; each joystick gives power to its respective motor
                robot.backLeftDrive.setPower(gamepad1.left_stick_y);
                robot.backRightDrive.setPower(gamepad1.right_stick_y);
                robot.frontLeftDrive.setPower(gamepad2.left_stick_y);
                robot.frontRightDrive.setPower(gamepad2.right_stick_y);


            } else if (driveSpeed == DriveSpeed.SLOW) {

                // tank mode controls for the left and right motor; each joystick gives power to its respective motor
                robot.backLeftDrive.setPower(gamepad1.left_stick_y * 0.25);
                robot.backRightDrive.setPower(gamepad1.right_stick_y * 0.25);
                robot.frontLeftDrive.setPower(gamepad2.left_stick_y * 0.25);
                robot.frontRightDrive.setPower(gamepad2.right_stick_y * 0.25);


            } else if (driveSpeed != DriveSpeed.FAST_TO_SLOW && driveSpeed != DriveSpeed.SLOW_TO_FAST) {

                driveSpeed = DriveSpeed.FAST;

            }

        }

    }

    private void CheckDriveSpeed() {

        if (gamepad1.dpad_down) {
            driveSpeed = DriveSpeed.SLOW;
        }
        if (gamepad1.dpad_up) {
            driveSpeed = DriveSpeed.FAST;
        }
    }


    private void GrabControl() {

        CheckGrabControl();

        if (grabControl == GrabControl.RELEASE) {
            robot.leftGrabDrive.setPower(.5);
            robot.rightGrabDrive.setPower(.5);

        } else if (grabControl == GrabControl.GRAB) {
            robot.leftGrabDrive.setPower(-.5);
            robot.rightGrabDrive.setPower(-.5);

        } else if (grabControl == GrabControl.LEFT_GRAB) {
            robot.leftGrabDrive.setPower(.75);
            robot.rightGrabDrive.setPower(.5);

        } else if (grabControl == GrabControl.RIGHT_GRAB) {
            robot.leftGrabDrive.setPower(.5);
            robot.rightGrabDrive.setPower(.75);

        } else if (grabControl == GrabControl.STOPPED) {
            robot.leftGrabDrive.setPower(0);
            robot.rightGrabDrive.setPower(0);
        }
    }

    private void CheckGrabControl() {


        if (gamepad1.a) {
            grabControl = GrabControl.RELEASE;
        } else if (gamepad1.b) {
            grabControl = GrabControl.GRAB;

        } else if (gamepad1.x) {
            grabControl = GrabControl.LEFT_GRAB;

        } else if (gamepad1.y) {
            grabControl = GrabControl.RIGHT_GRAB;
        } else {
            grabControl = GrabControl.STOPPED;

        }


    }

    private void LiftControl() {

        CheckLiftControl();

        if (gamepad2.right_stick_y != 0) {

            liftControl = LiftMovement.STATIC;
            robot.liftDrive.setPower(-gamepad2.right_stick_y * 0.4);

        } else {

            switch (liftControl) {

                case COLLECT:
                    if (robot.liftDrive.getCurrentPosition() > 20) {

                        robot.liftDrive.setPower(-0.4);

                    } else {

                        robot.liftDrive.setPower(0);

                    }
                    break;

                case FIRST:
                    if (robot.liftDrive.getCurrentPosition() < 400) {

                        robot.liftDrive.setPower(0.4);

                    } else {

                        robot.liftDrive.setPower(0);

                    }
                    break;

                case SECOND:
                    if (robot.liftDrive.getCurrentPosition() < 1320) {

                        robot.liftDrive.setPower(0.4);

                    } else {

                        robot.liftDrive.setPower(0);

                    }
                    break;

                case THIRD:
                    if (robot.liftDrive.getCurrentPosition() < 2200) {

                        robot.liftDrive.setPower(0.4);

                    } else {

                        robot.liftDrive.setPower(0);

                    }
                    break;

                case FOURTH:
                    if (robot.liftDrive.getCurrentPosition() < 3250) {

                        robot.liftDrive.setPower(0.4);

                    } else if (robot.liftDrive.getCurrentPosition() > 3500) {

                        robot.liftDrive.setPower(-.4);

                    } else {

                        robot.liftDrive.setPower(0);

                    }
                    break;

                default:
                    robot.liftDrive.setPower(0);

            }

        }

    }


    private void CheckLiftControl() {

        if (gamepad2.a) {

            liftControl = LiftMovement.TO_COLLECT;

        } else if (gamepad2.b) {

            liftControl = LiftMovement.TO_FIRST;

        } else if (gamepad2.x) {

            liftControl = LiftMovement.TO_SECOND;

        } else if (gamepad2.y) {

            liftControl = LiftMovement.TO_THIRD;

        } else if (gamepad2.right_bumper) {

            liftControl = LiftMovement.TO_FOURTH;

        }

        if (!gamepad2.a && liftControl == LiftMovement.TO_COLLECT) {

            liftControl = LiftMovement.COLLECT;

        }
        if (!gamepad2.b && liftControl == LiftMovement.TO_FIRST) {

            liftControl = LiftMovement.FIRST;

        }
        if (!gamepad2.x && liftControl == LiftMovement.TO_SECOND) {

            liftControl = LiftMovement.SECOND;

        }
        if (!gamepad2.y && liftControl == LiftMovement.TO_THIRD) {

            liftControl = LiftMovement.THIRD;

        }
        if (!gamepad2.right_bumper && liftControl == LiftMovement.TO_FOURTH) {

            liftControl = LiftMovement.FOURTH;

        }

    }*/
}
