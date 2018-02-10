package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by Thomas on 11/21/2017.
 */

@Autonomous(name = "PICK ME", group = "Autonomous")
@Disabled
public class AutoTesting extends AutonomousRobot {

    @Override
    public void runOpMode() {

        telemetry.addData("STATUS", "Initializing");
        telemetry.update();

        // Setup stuff
        super.runOpMode();

        telemetry.addData("STATUS", "Ready, waiting for start");
        telemetry.update();

        waitForStart();

        telemetry.addData("STATUS", "Running...");
        telemetry.update();

        // Do delay
        StartMovement();

        //  GrabJewel();

        CheckJewel();

        EncoderDrive(3, 0.3, 2);

        WaitFor(1);

        EncoderDrive(3, 0.3, 2);

        WaitFor(0.5);

        // vuMark = vuforia.getVuMark();

        EncoderDrive(12, 0.5, 3);

        WaitFor(0.5);

     /*   if (vuMark == RelicRecoveryVuMark.CENTER) {

            EncoderDrive(6, 0.5, 4);

        } else if (vuMark == RelicRecoveryVuMark.LEFT) {

            EncoderDrive(12, 0.5, 8);

        }
        */
        WaitFor(0.5);

        RelativeTurn(90, 0.4, 3);

    }


    private void Close_Red() {

        /*

        Grab glyph

        Check Jewel

        Move forward 3 inches

        Scan vuMark

        Move forward 21 inches

        Move forward more based on vuMark
            Right - 0
            Center - 7
            Left - 14

        Turn 90 degrees to the right

        Move forward 14-16 inches

        Release Glyph

        Move backwards 3 inches

         */

    }

    private void Far_Red() {

        /*

        Grab glyph

        Check Jewel

        Move forward 3 inches

        Scan vuMark

        Move forward 21 inches

        Turn 90 to the left

        Move forward more based on vuMark
            Right - 0
            Center - 7
            Left - 14

        Turn 90 degrees to the right

        Move forward 14-16 inches

        Release Glyph

        Move backwards 3 inches

         */


    }

    private void Close_Blue() {

        /*

        Grab glyph

        Check Jewel

        Move forward 3 inches

        Scan vuMark

        Move backwards 6 inches

        Move backwards 21 inches

        Move backwards more based on vuMark
            Right - 0
            Center - 7
            Left - 14

        Turn 90 to the right

        Move forward 14-16 inches

        Release Glyph

        Move backwards 3 inches

         */

    }

    private void Far_Blue() {

        /*

        Grab glyph

        Check Jewel

        Move forward 3 inches

        Scan vuMark

        Move backwards 6 inches

        Move backwards 21 inches

        Turn 90 to the left

        Move forward more based on vuMark
            Right - 0
            Center - 7
            Left - 14

        Turn 90 to the left

        Move forward 14-16 inches

        Release Glyph

        Move backwards 3 inches

         */

    }


}
