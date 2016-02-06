package org.ftcbootstrap.demos.beginner.opmodes;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.servos.GamePadServo;
import org.ftcbootstrap.demos.beginner.MyFirstBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "MyFirstBot" and creating a class by the same name: {@link MyFirstBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary: Use an Operation class to control servo with the gamepad buttons
 * See: {@link GamePadServo}
 */
public class OpMode7ServoWithGamepad extends ActiveOpMode {

    private MyFirstBot robot;
    private GamePadServo gamePadServo;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = MyFirstBot.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();

        // GamePadServo.Control use the Y and A buttons for up and down and the  X and B buttons for left and right
        // Set initial position of server to 0.5
        gamePadServo = new GamePadServo(this, gamepad1, robot.getServo(),GamePadServo.Control.Y_A, 0.5);
        gamePadServo.setOpModeLogLevel(1);
    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the servo from the gamepad button events
        gamePadServo.update();

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }

}
