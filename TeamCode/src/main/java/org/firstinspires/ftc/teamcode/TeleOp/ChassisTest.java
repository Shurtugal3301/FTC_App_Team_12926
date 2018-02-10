package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.HardwareRobot;

/**
 * Created by Thomas on 1/13/2018.
 */

@TeleOp(name = "TankOp", group = "TeleOp")
public class ChassisTest extends OpMode {

    private enum DriveSpeed {

        FAST, SLOW, SLOW_TO_FAST, FAST_TO_SLOW

    }

    private enum LiftMovement {

        STATIC, COLLECT, FIRST, SECOND, THIRD, FOURTH, TO_COLLECT, TO_FIRST, TO_SECOND, TO_THIRD, TO_FOURTH

    }

    private LiftMovement liftControl;
    private DriveSpeed driveSpeed;

    private double speedMod;

    HardwareRobot robot;

    @Override
    public void init() {

        robot = new HardwareRobot(hardwareMap);

        robot.ResetServoPositions();

        telemetry.addData("STATUS", "Initializing");
        telemetry.update();

        liftControl = LiftMovement.COLLECT;
        driveSpeed = DriveSpeed.FAST;
        speedMod = 0;

        telemetry.addData("STATUS", "Ready, waiting for start");
        telemetry.update();

    }

    // Loops after the start button is pressed
    @Override
    public void loop() {

        Telemetry();

        CheckSpeed();

        if (driveSpeed == DriveSpeed.SLOW) {

            speedMod = 0.4;

        } else if (driveSpeed == DriveSpeed.FAST) {

            speedMod = 0.75;

        }

        robot.frontLeft.setPower(clamp(-gamepad1.left_stick_y - gamepad1.left_stick_x - (gamepad1.right_stick_x * 0.9), -1, 1) * speedMod);
        robot.frontRight.setPower(clamp(gamepad1.left_stick_y - gamepad1.left_stick_x - (gamepad1.right_stick_x * 0.9), -1, 1) * speedMod);
        robot.backLeft.setPower(clamp(-gamepad1.left_stick_y + gamepad1.left_stick_x - (gamepad1.right_stick_x * 0.9), -1, 1) * speedMod);
        robot.backRight.setPower(clamp(gamepad1.left_stick_y + gamepad1.left_stick_x - (gamepad1.right_stick_x * 0.9), -1, 1) * speedMod);

        LiftControl();
        GrabControl();

        if (gamepad2.a) {

            robot.liftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

        if (gamepad2.x) {

            robot.ResetServoPositions();

        }

    }

    // Runs when the stop button is pressed.
    @Override
    public void stop() {

        robot.StopAllMotors();

    }

    private void Telemetry() {

        telemetry.addData("Drive Speed", driveSpeed.toString())
                .addData("Lift Drive", "Speed %.2f Encoder: %d", robot.liftDrive.getPower(), robot.liftDrive.getCurrentPosition());
        telemetry.update();

    }

    private static double clamp(double val, double min, double max) {

        return Math.max(min, Math.min(max, val));

    }

    private void CheckSpeed() {

        if (gamepad1.left_bumper && driveSpeed == DriveSpeed.FAST) {

            driveSpeed = DriveSpeed.FAST_TO_SLOW;

        } else if (gamepad1.left_bumper && driveSpeed == DriveSpeed.SLOW) {

            driveSpeed = DriveSpeed.SLOW_TO_FAST;

        }

        if (!gamepad1.left_bumper) {

            if (driveSpeed == DriveSpeed.SLOW_TO_FAST) {

                driveSpeed = DriveSpeed.FAST;

            } else if (driveSpeed == DriveSpeed.FAST_TO_SLOW) {

                driveSpeed = DriveSpeed.SLOW;

            }

        }

    }

    private void GrabControl() {

        if (gamepad1.left_trigger != 0) {

            robot.leftGrab.setPower(-gamepad1.left_trigger * 0.7);
            robot.rightGrab.setPower(-gamepad1.left_trigger * 0.7);

        } else if (gamepad1.right_trigger != 0) {

            robot.leftGrab.setPower(gamepad1.right_trigger * 0.6);
            robot.rightGrab.setPower(gamepad1.right_trigger * 0.6);

        } else {

            robot.leftGrab.setPower(0);
            robot.rightGrab.setPower(0);

        }

    }

    private void LiftControl() {

        CheckLiftControl();

        if (gamepad1.right_stick_y != 0) {

            liftControl = LiftMovement.STATIC;

            if (robot.liftDrive.getCurrentPosition() > 300 && -gamepad1.right_stick_y < 0) {

                robot.liftDrive.setPower(-gamepad1.right_stick_y * 0.6);

            } else if (robot.liftDrive.getCurrentPosition() < 3300 && -gamepad1.right_stick_y > 0) {

                robot.liftDrive.setPower(-gamepad1.right_stick_y * 0.6);

            } else {

                robot.liftDrive.setPower(0);

            }

        } else if (gamepad2.left_stick_y != 0) {

            robot.liftDrive.setPower(-gamepad2.left_stick_y * 0.4);

        } else {

            switch (liftControl) {

                case COLLECT:
                    if (robot.liftDrive.getCurrentPosition() > 150) {

                        robot.liftDrive.setPower(-0.6);

                    } else {

                        liftControl = LiftMovement.STATIC;

                    }
                    break;

                case FIRST:
                    if (robot.liftDrive.getCurrentPosition() < 700) {

                        robot.liftDrive.setPower(0.6);

                    } else {

                        liftControl = LiftMovement.STATIC;

                    }
                    break;

                case SECOND:
                    if (robot.liftDrive.getCurrentPosition() < 1500) {

                        robot.liftDrive.setPower(0.6);

                    } else {

                        liftControl = LiftMovement.STATIC;

                    }
                    break;

                case THIRD:
                    if (robot.liftDrive.getCurrentPosition() < 2200) {

                        robot.liftDrive.setPower(0.6);

                    } else {

                        liftControl = LiftMovement.STATIC;

                    }
                    break;

                case FOURTH:
                    if (robot.liftDrive.getCurrentPosition() < 3050) {

                        robot.liftDrive.setPower(0.6);

                    } else {

                        liftControl = LiftMovement.STATIC;

                    }
                    break;

                case STATIC:
                    robot.liftDrive.setPower(0);
                    break;

                default:
                    robot.liftDrive.setPower(0);

            }

        }

    }

    private void CheckLiftControl() {

        if (gamepad1.a) {

            liftControl = LiftMovement.TO_COLLECT;

        } else if (gamepad1.b) {

            liftControl = LiftMovement.TO_FIRST;

        } else if (gamepad1.x) {

            liftControl = LiftMovement.TO_SECOND;

        } else if (gamepad1.y) {

            liftControl = LiftMovement.TO_THIRD;

        } else if (gamepad1.right_bumper) {

            liftControl = LiftMovement.TO_FOURTH;

        }

        if (!gamepad1.a && liftControl == LiftMovement.TO_COLLECT) {

            liftControl = LiftMovement.COLLECT;

        }
        if (!gamepad1.b && liftControl == LiftMovement.TO_FIRST) {

            liftControl = LiftMovement.FIRST;

        }
        if (!gamepad1.x && liftControl == LiftMovement.TO_SECOND) {

            liftControl = LiftMovement.SECOND;

        }
        if (!gamepad1.y && liftControl == LiftMovement.TO_THIRD) {

            liftControl = LiftMovement.THIRD;

        }
        if (!gamepad1.right_bumper && liftControl == LiftMovement.TO_FOURTH) {

            liftControl = LiftMovement.FOURTH;

        }

    }

}
