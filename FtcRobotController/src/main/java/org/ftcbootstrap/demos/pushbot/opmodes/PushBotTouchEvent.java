package org.ftcbootstrap.demos.pushbot.opmodes;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.TankDrive;
import org.ftcbootstrap.demos.pushbot.PushBot;
import org.ftcbootstrap.components.utils.DriveDirection;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "Pushbot" and creating a class by the same name: {@link PushBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 * <p/>
 * Refactored from the original Qualcomm PushBot examples to demonstrate the use of the latest
 * reusable components and operations
 * See:
 * <p/>
 * {@link org.ftcbootstrap.components},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.servos},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.motors}
 * <p/>
 * Also see: {@link PushBot} for the saved configuration
 */
public class PushBotTouchEvent extends ActiveOpMode {

    private PushBot robot;
    private TankDrive tankDrive;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = PushBot.newConfig(hardwareMap, getTelemetryUtil());
        tankDrive  = new TankDrive(this, robot.getLeftDrive(), robot.getLeftDrive());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop()  throws InterruptedException {

        if ( robot.getTouchSensor().isPressed()) {
            tankDrive.stop();
        }
        else {
            tankDrive.drive( 1, DriveDirection.DRIVE_FORWARD);
        }

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }





}
