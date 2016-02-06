package org.ftcbootstrap.demos.beginner.opmodes;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.demos.beginner.MyFirstBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "MyFirstBot" and creating a class by the same name: {@link MyFirstBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary
 * Run motor for 3 seconds.   Note the sleep() command.  The program will hold there for 3 seconds.
 * Limitation:  The motor will continue to run during the 3 second sleep period but you cannot do anything else in the
 * code during that period.   For example , you may want to allow for a kill switch to turn the motor off
 * at any time ( even during the 3 seconds sleep period).
 * Therefore, a more appropriate solution would be here: {@link OpMode2RunForTime} .
 */
public class OpMode1RunForTime extends ActiveOpMode {

    private MyFirstBot robot;


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

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //NOTE:  activeLoop() should be called repeatedly  after each each hardware cycle period.
        //However the below code is designed to only run once:  start motor, wait 3 seconds, stop motor.
        //This is going to be a problem if you want to add a kill switch to stop the motors before the
        // end of the 3 seconds

        //Run the motor for 3 seconds


        //PROBLEM L  kill switch code will ony be checked once
        if ( robot.getTouch().isPressed() ) {
            robot.getMotor1().setPower(0);
            setOperationsCompleted();
        }
        else {

            //run motor for 3 seconds
            robot.getMotor1().setPower(1);

            //NOTE: This operation is generally not recommended because it causes the Opmode code to hold here
            //while the thread sleeps.   See the next example where an alternative soultion will be required
            sleep(3000);
            //waiting 3 seconds here (3000 miliseconds)

            robot.getMotor1().setPower(0);

            //Note: the above is a linear one time operation so you need to call
            // setOperationsCompleted() to prevent activeLoop() from being called again
            setOperationsCompleted();
        }


    }

}
