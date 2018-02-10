package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareRobot;

/**
 * Created by Thomas on 9/11/2017.
 */
@TeleOp(name = "Reset Motors", group = "TeleOp")
public class FreeMovement extends OpMode {

    HardwareRobot robot;

    @Override
    public void init() {

        telemetry.addData("STATUS", "Initializing");
        telemetry.update();

        robot = new HardwareRobot(hardwareMap);

        telemetry.addData("STATUS", "Ready, waiting for start");
        telemetry.update();

    }

    // Loops after the start button is pressed
    @Override
    public void loop() {

        Telemetry();

        robot.backLeft.setPower(gamepad1.left_stick_y * 0.5);
        robot.backRight.setPower(gamepad1.right_stick_y * 0.5);
        robot.frontLeft.setPower(gamepad1.left_stick_y * 0.5);
        robot.frontRight.setPower(gamepad1.right_stick_y * 0.5);
/*
        if (gamepad1.a) {

            robot.leftServo.setPosition(0);
            robot.rightServo.setPosition(50);

        } else if (gamepad1.x) {

            robot.leftServo.setPosition(50);
            robot.rightServo.setPosition(0);

        }

        if (gamepad1.b) {

            robot.jewelDrive.setPower(-0.025);

        } else if (gamepad1.y) {

            robot.jewelDrive.setPower(0.025);

        } else {

            robot.jewelDrive.setPower(0);

        }
*/
        if (gamepad1.left_bumper) {

            robot.liftDrive.setPower(-0.25);

        } else if (gamepad1.right_bumper) {

            robot.liftDrive.setPower(0.25);

        } else {

            robot.liftDrive.setPower(0);

        }

    }

    // Runs when the stop button is pressed
    @Override
    public void stop() {

        robot.StopAllMotors();
        //robot.ResetServoPositions();

    }

    private void Telemetry() {

        telemetry.addData("STATUS", "Running...");
        telemetry.addData("Drive Motors", "Left Power: %.3f, Right Power: %.3f",
                robot.backLeft.getPower(), robot.backRight.getPower());
        //telemetry.addData("Servo Positions", "Left: %.3f, Right: %.3f",
        //        robot.leftServo.getPosition(), robot.rightServo.getPosition());
        //telemetry.addData("Jewel Drive", "Power: %.3f, Encoder: %3d",
        //        robot.jewelDrive.getPower(), robot.jewelDrive.getCurrentPosition());
        telemetry.addData("Lift Drive", "Power: %.3f, Encoder: %3d",
                robot.liftDrive.getPower(), robot.liftDrive.getCurrentPosition());
        telemetry.update();

    }

}
