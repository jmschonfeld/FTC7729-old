package org.ftcbootstrap.components;

import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.ftcbootstrap.ActiveOpMode;

/**
 * OpMode Component for reading and data from the Optical distance sensor
 */

public class TimerComponent extends OpModeComponent{


    private double startTime;
    private boolean isRunning;

    /**
     * Constructor for component
     * Telemetry enabled by default.
     * @param opMode
     */
    public TimerComponent(ActiveOpMode opMode) {

        super(opMode);

    }




    /**
     * @return boolean designated whether the odsReading matches the target value
     * @param
     */
    public boolean targetReached(double targetDelay) {

        if (  isRunning ) {
            boolean reached =  (getOpMode().getRuntime() - startTime) >= targetDelay ;
            if ( reached) {
                isRunning = false;
            }
            return reached;
        }

        startTime = getOpMode().getRuntime();
        isRunning = true;
        return false;
    }



}
