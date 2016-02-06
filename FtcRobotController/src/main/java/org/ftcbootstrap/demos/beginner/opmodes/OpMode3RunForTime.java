package org.ftcbootstrap.demos.beginner.opmodes;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToTime;
import org.ftcbootstrap.demos.beginner.MyFirstBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "MyFirstBot" and creating a class by the same name: {@link MyFirstBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary
 * Demonstrates the use of a reusable "bootstrap" operation (MotorToTime) to reduce the code in the opmode.
 */
public class OpMode3RunForTime extends ActiveOpMode {

    private MyFirstBot robot;
    private MotorToTime motorToTime;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = MyFirstBot.newConfig(hardwareMap, getTelemetryUtil());

        motorToTime = new MotorToTime("motor with timer" , this ,robot.getMotor1() );

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //run motor for 3 seconds or until kill switch is pressed.

        //kill switch can be pressed  at any time
        if ( robot.getTouch().isPressed() ) {
            motorToTime.stop();
            setOperationsCompleted();
        }
        else {
            //run full power (1)  for 3 seconds
            if (motorToTime.runToTarget(1, 3)) {
                setOperationsCompleted();
            }
        }

    }


}
