package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareRobot;

/**
 * Created by Thomas on 9/11/2017.
 */
@TeleOp(name = "TankOp", group = "TeleOp")
@Disabled
public class TankOp extends OpMode {


    private enum DriveMode {

        TANK, STRAFE, TANK_TO_STRAFE, STRAFE_TO_TANK

    }

    private enum DriveSpeed {

        SLOW, FAST, SLOW_TO_FAST, FAST_TO_SLOW

    }

    private enum GrabControl {

        OPEN, CLOSED, RELEASED, TO_CLOSED, TO_RELEASED, TO_OPEN

    }
    private enum JewelArmControl {

        RAISED, LOWERED

    }
    private enum LiftMovement {

        STATIC, COLLECT, FIRST, SECOND, THIRD, FOURTH, TO_COLLECT, TO_FIRST, TO_SECOND, TO_THIRD, TO_FOURTH

    }

    private enum ReleaseMode {

        LEFT, RIGHT, BOTH, TO_BOTH, TO_LEFT, TO_RIGHT

    }

    private HardwareRobot robot;

    private DriveMode driveMode;
    private DriveSpeed driveSpeed;
    private GrabControl grabControl;
    private JewelArmControl jewelArm;
    private LiftMovement liftControl;
    private ReleaseMode releaseMode;

    private final double LIFT_SPEED = 1;
    private final double GRAB_SPEED = 0.2;

    // Runs when the robot is initialized
    @Override
    public void init() {

        telemetry.addData("STATUS", "Initializing");
        telemetry.update();

        robot = new HardwareRobot(hardwareMap);

        driveMode = DriveMode.TANK;
        driveSpeed = DriveSpeed.FAST;
        grabControl = GrabControl.OPEN;
        jewelArm = JewelArmControl.RAISED;
        liftControl = LiftMovement.COLLECT;
        releaseMode = ReleaseMode.BOTH;

        telemetry.addData("STATUS", "Ready, waiting for start");
        telemetry.update();

    }

    // Loops after the start button is pressed
    @Override
    public void loop() {

        //Telemetry();

        //LiftControl();

        //DriveControl();

        //GrabControl();

        //JewelArmControl();

        //ArmServoControl();

        //CheckReleaseMode();


    }

    // Runs when the stop button is pressed.
    @Override
    public void stop() {

        robot.StopAllMotors();
        //robot.ResetServoPositions();

    }
/*
    private void Telemetry() {

        //telemetry.addData("VuMark", robot.vuforia.getVuMark());
        telemetry.addData("STATUS", "Running...")
                //        .addData("Color Sensor", "Red: %d, Blue: %d, Green: %d, Alpha: %d",
                //                robot.colorSensor.red(), robot.colorSensor.blue(), robot.colorSensor.green(), robot.colorSensor.alpha())
                .addData("Drive Control", "Mode: %s Speed: %s", driveMode.toString(), driveSpeed.toString())
                //        .addData("Release Mode", releaseMode.toString())
                //        .addData("Servo Mode", grabControl.toString())
                .addData("Drive Motors", "Left Power: %.3f, Right Power: %.3f",
                        robot.backLeftDrive.getPower(), robot.backRightDrive.getPower());
                //   .addData("Grab Positions", "Left: %d, Right: %d",
                //          robot.leftGrabDrive.getCurrentPosition(), robot.rightGrabDrive.getCurrentPosition())
        //        .addData("Jewel Servo", "Position: %.3f",
        //                robot.jewelServoLift.getPosition())
        //        .addData("Lift Drive", "Power: %.3f, Encoder: %3d",
        //        robot.liftDrive.getPower(), robot.liftDrive.getCurrentPosition());
        telemetry.update();

    }

    private void DriveControl() {

        CheckDriveMode();

        if (driveMode == DriveMode.TANK) {

            if (driveSpeed == DriveSpeed.FAST) {

                // tank mode controls for the left and right motor; each joystick gives power to its respective motor
                robot.backLeftDrive.setPower(gamepad1.left_stick_y);
                robot.backRightDrive.setPower(gamepad1.right_stick_y);
                robot.frontLeftDrive.setPower(gamepad1.left_stick_y);
                robot.frontRightDrive.setPower(gamepad1.right_stick_y);

                //controls for the strafe wheel
                if (gamepad1.left_trigger > 0.1) {

                    //robot.strafeDrive.setPower(-gamepad1.left_trigger);

                } else if (gamepad1.right_trigger > 0.1) {

                    //robot.strafeDrive.setPower(gamepad1.right_trigger);

                } else {

                    //robot.strafeDrive.setPower(0);

                }

            } else if (driveSpeed == DriveSpeed.SLOW) {

                // tank mode controls for the left and right motor; each joystick gives power to its respective motor
                robot.backLeftDrive.setPower(gamepad1.left_stick_y * 0.25);
                robot.backRightDrive.setPower(gamepad1.right_stick_y * 0.25);
                robot.frontLeftDrive.setPower(gamepad1.left_stick_y * 0.25);
                robot.frontRightDrive.setPower(gamepad1.right_stick_y * 0.25);

                //controls for the strafe wheel
                if (gamepad1.left_trigger > 0.1) {

                    //robot.strafeDrive.setPower(-gamepad1.left_trigger);

                } else if (gamepad1.right_trigger > 0.1) {

                    //robot.strafeDrive.setPower(gamepad1.right_trigger);

                } else {

                    //robot.strafeDrive.setPower(0);

                }

            } else if (driveSpeed != DriveSpeed.FAST_TO_SLOW && driveSpeed != DriveSpeed.SLOW_TO_FAST) {

                driveSpeed = DriveSpeed.FAST;

            }

        } else if (driveMode == DriveMode.STRAFE) {

            if (driveSpeed == DriveSpeed.FAST) {

                if (gamepad1.left_stick_y != 0) {

                    robot.backLeftDrive.setPower(gamepad1.left_stick_y);
                    robot.backRightDrive.setPower(gamepad1.left_stick_y);
                    robot.frontLeft.setPower(gamepad1.left_stick_y);
                    robot.frontRight.setPower(gamepad1.left_stick_y);

                } else {

                    robot.backLeft.setPower(gamepad1.right_stick_x);
                    robot.backRight.setPower(-gamepad1.right_stick_x);
                    robot.frontLeft.setPower(gamepad1.right_stick_x);
                    robot.frontRight.setPower(-gamepad1.right_stick_x);

                }

                //robot.strafeDrive.setPower(gamepad1.left_stick_x);

            } else if (driveSpeed == DriveSpeed.SLOW) {

                if (gamepad1.left_stick_y != 0) {

                    robot.backLeft.setPower(gamepad1.left_stick_y * 0.25);
                    robot.backRight.setPower(gamepad1.left_stick_y * 0.25);
                    robot.frontLeft.setPower(gamepad1.left_stick_y * 0.25);
                    robot.frontRight.setPower(gamepad1.left_stick_y * 0.25);

                } else {

                    robot.backLeft.setPower(gamepad1.right_stick_x * 0.25);
                    robot.backRight.setPower(-gamepad1.right_stick_x * 0.25);
                    robot.frontLeft.setPower(gamepad1.right_stick_x * 0.25);
                    robot.frontRight.setPower(-gamepad1.right_stick_x * 0.25);

                }

                //robot.strafeDrive.setPower(gamepad1.left_stick_x);

            } else if (driveSpeed != DriveSpeed.FAST_TO_SLOW && driveSpeed != DriveSpeed.SLOW_TO_FAST) {

                driveSpeed = DriveSpeed.FAST;

            }

        } else if (driveMode != DriveMode.TANK_TO_STRAFE && driveMode != DriveMode.STRAFE_TO_TANK) {

            driveMode = DriveMode.TANK;

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

    }

    private void JewelArmControl() {

        if (gamepad1.b) {

            jewelArm = JewelArmControl.RAISED;

        } else if (gamepad1.y) {

            jewelArm = JewelArmControl.LOWERED;

        }

        if (!gamepad1.b && !gamepad1.y) {

            if (jewelArm == JewelArmControl.RAISED) {

                robot.jewelServoLift.setPosition(0);

            } else if (jewelArm == JewelArmControl.LOWERED) {

                robot.jewelServoLift.setPosition(1);

            }


        }

    }

    private void GrabControl() {

        if (gamepad1.left_trigger != 0) {

            robot.leftGrab.setPower(-0.65);
            robot.rightGrab.setPower(-0.65);

        } else if (gamepad2.right_trigger != 0) {

            robot.leftGrab.setPower(0.5);
            robot.rightGrab.setPower(0.5);

        } else {

            robot.leftGrab.setPower(0);
            robot.rightGrab.setPower(0);

        }

    }

    // --------------------------------
    private void ArmServoControl() {

        if (gamepad2.x && grabControl == GrabControl.OPEN) {

            grabControl = GrabControl.TO_CLOSED;

        } else if (gamepad2.x && grabControl == GrabControl.CLOSED) {

            grabControl = GrabControl.TO_RELEASED;

        } else if (gamepad2.x && grabControl == GrabControl.RELEASED) {

            grabControl = GrabControl.TO_OPEN;

        } else if (gamepad2.a && (grabControl == GrabControl.RELEASED || grabControl == GrabControl.CLOSED)) {

            grabControl = GrabControl.TO_OPEN;

        } else if (gamepad2.a && grabControl == GrabControl.OPEN) {

            grabControl = GrabControl.TO_CLOSED;

        }

        //CheckReleaseMode();

        if (!gamepad2.a && !gamepad2.x) {

            if (grabControl == GrabControl.TO_OPEN) {

                grabControl = GrabControl.OPEN;

            } else if (grabControl == GrabControl.TO_CLOSED) {

                grabControl = GrabControl.CLOSED;

            } else if (grabControl == GrabControl.TO_RELEASED) {

                grabControl = GrabControl.RELEASED;

            }

        }
        /*
        if (grabControl == GrabControl.OPEN) {

            robot.leftGrabDrive.setPosition(1); //robot.leftGrabDrive.setPower(0)
            robot.rightGrabDrive.setPosition(0);

        } else if (grabControl == GrabControl.CLOSED) {

            robot.leftGrabDrive.setPosition(0);
            robot.rightGrabDrive.setPosition(1);

        } else if (grabControl == GrabControl.RELEASED) {

            switch (releaseMode) {

                case LEFT:
                    robot.leftGrabDrive.setPosition(1);
                    robot.rightGrabDrive.setPosition(.6);
                    break;

                case RIGHT:
                    robot.leftGrabDrive.setPosition(.4);
                    robot.rightGrabDrive.setPosition(0);
                    break;

                case BOTH:
                    robot.leftGrabDrive.setPosition(.4);
                    robot.rightGrabDrive.setPosition(.6);
                    break;

                default:
                    robot.leftGrabDrive.setPosition(.4);
                    robot.rightGrabDrive.setPosition(.6);
                    releaseMode = ReleaseMode.BOTH;

            }


        }
        */
    //}

    // ---------------------------
/*
    private void CheckDriveMode() {

        if (gamepad1.left_stick_button && driveMode == DriveMode.STRAFE) {

            driveMode = DriveMode.STRAFE_TO_TANK;

        } else if (gamepad1.right_stick_button && driveMode == DriveMode.TANK) {

            driveMode = DriveMode.TANK_TO_STRAFE;

        }

        if (gamepad1.left_bumper && driveSpeed == DriveSpeed.SLOW) {

            driveSpeed = DriveSpeed.SLOW_TO_FAST;

        } else if (gamepad1.right_bumper && driveSpeed == DriveSpeed.FAST) {

            driveSpeed = DriveSpeed.FAST_TO_SLOW;

        }


        if (!gamepad1.left_stick_button && !gamepad1.right_stick_button) {

            if (driveMode == DriveMode.STRAFE_TO_TANK) {

                driveMode = DriveMode.TANK;

            } else if (driveMode == DriveMode.TANK_TO_STRAFE) {

                driveMode = DriveMode.STRAFE;

            }

        }

        if (!gamepad1.left_bumper && !gamepad1.right_bumper) {

            if (driveSpeed == DriveSpeed.SLOW_TO_FAST) {

                driveSpeed = DriveSpeed.FAST;

            } else if (driveSpeed == DriveSpeed.FAST_TO_SLOW) {

                driveSpeed = DriveSpeed.SLOW;

            }

        }

    }
*/
}
