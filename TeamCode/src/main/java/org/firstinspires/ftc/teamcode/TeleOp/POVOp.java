package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.HardwareRobot;

/**
 * Created by Dristi on 10/4/17.
 */

@TeleOp(name="POVOp", group="Linear Opmodes")
@Disabled
public class POVOp extends OpMode {

    HardwareRobot robot;

    ElapsedTime runtime  = new ElapsedTime();

    Servo colorServo;

    // Runs when the robot is initialized
    @Override
    public void init() {

        robot = new HardwareRobot(hardwareMap);

    }

    // Loops after the start button is pressed
    @Override
    public void loop() {

        runtime.reset();
        telemetry.addLine()
                .addData("Runtime:", "%.2f", runtime.time());

        //Left joystick drives the robot forward and backward; right joystick controls the turn
        //Easier controls compared to TankOp; driving straight is exact
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;
        double leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        double rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

        //send power to motors
        robot.backLeft.setPower(leftPower);
        robot.backRight.setPower(rightPower);

    }

    // Runs when the stop button is pressed
    @Override
    public void stop() {

        robot.StopDriveMotors();

    }


}
