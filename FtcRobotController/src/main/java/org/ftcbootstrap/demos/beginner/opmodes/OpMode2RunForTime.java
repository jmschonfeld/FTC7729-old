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
 * Use a touch sensor to kill the motor at any time during the program
 */
public class OpMode2RunForTime extends ActiveOpMode {

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
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //Improved Implementation.  The activeLoop() should now be called repeatedly  after
        // each hardware cycle period.  There is no sleep method that prvents the kill swithc from being
        // rechecked as in the prior example.
        //kill switch can be pressed  at any time
        if ( robot.getTouch().isPressed() ) {
            robot.getMotor1().setPower(0);
            setOperationsCompleted();
        }
        else {
            // we are not using sleep() but rather checking an elapsed time
            // therefore the the above kill switch will work whenever you press it/
            //See the next example that incorportates the timer into the motor operation
            if (getTimer().targetReached(3)) {
                robot.getMotor1().setPower(0);
                setOperationsCompleted();
            }
            else {
                robot.getMotor1().setPower(1);
            }
        }


    }



}
