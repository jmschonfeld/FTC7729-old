package org.ftc7729.opmodes;

import org.ftc7729.FTCTeamRobot;
import org.ftcbootstrap.ActiveOpMode;

/**
 * Created by Jeremy on 2/9/2016.
 */
public class OutputODS extends ActiveOpMode {
    private FTCTeamRobot robot;



    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = FTCTeamRobot.newConfig(hardwareMap, getTelemetryUtil());
        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        robot.getOpticalDistanceSensor().enableLed(true);

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("ods", robot.getOpticalDistanceSensor().getLightDetected());
        getTelemetryUtil().addData("odsRaw", robot.getOpticalDistanceSensor().getLightDetectedRaw());
        getTelemetryUtil().sendTelemetry();
    }
}
