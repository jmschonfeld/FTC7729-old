package org.ftcbootstrap.demos.beginner.opmodes;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToTouch;
import org.ftcbootstrap.demos.beginner.MyFirstBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "MyFirstBot" and creating a class by the same name: {@link MyFirstBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:  Use an Operation class to keep your opmode as small as possible.
 * In the last example: {@link OpMode3RunForTime}, A special bootstrap operation was introduced to all
 * the user to run a motor for a certian amount of time. In addition,   the class was responsible for
 * checking the touch sensor and setting the motor power.
 * In this example,  we use a special operation to handle all of that
 * see {@link org.ftcbootstrap.components.operations.motors.MotorToTouch}
 */
public class OpMode4RunUntilTouch extends ActiveOpMode {

    private MyFirstBot robot;
    private MotorToTouch motorToTouch;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = MyFirstBot.newConfig(hardwareMap, getTelemetryUtil());

        //create an operation to control a motor from a touch sensor
        motorToTouch = new MotorToTouch( "motor1" , this, robot.getMotor1(), robot.getTouch());
        motorToTouch.setOpModeLogLevel(1);

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //run motor full power (1) until the touch sensor is pressed
        if ( motorToTouch.runToTarget(1)) {
            setOperationsCompleted();
        }

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }

}
