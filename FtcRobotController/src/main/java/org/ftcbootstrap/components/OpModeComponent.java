package org.ftcbootstrap.components;

import org.ftcbootstrap.ActiveOpMode;

/**
 * OpModeComponent for reading and data from the Optical distance sensor
 */

public class OpModeComponent {

    private ActiveOpMode opMode;
    private String name;
    private int opModeLogLevel;
    private int componentLogLevel = 10;




    /**
     * Constructor for component
     * @param opMode
     */
    public OpModeComponent(ActiveOpMode opMode) {

        this.opMode = opMode;

    }


    /**
     *
     * @return opModeLogLevel <= componentLogLevel
     */
    public boolean isTelemetryEnabled( ) {
        return opModeLogLevel <= componentLogLevel;
    }

    /**
     *
     * @param logLevel   any number to control the level of telemetry
     */
    public void setOpModeLogLevel(int logLevel) {
        this.opModeLogLevel = logLevel;
    }

    /**
     *
     * @param logLevel  any number to control the level of telemetry
     */
    public void setComponentLogLevel(int logLevel) {
        this.componentLogLevel = logLevel;
    }



    /**
     * Note:  Adding data does not automatically send the data to the Driver Station Display.
     * Use sendTelemetry() for that.
     * @param key
     * @param message
     */
    public void addTelemetry(String key , double message) {
        addTelemetry(key, Double.toString(message));
    }

    /**
     * Note:  Adding data does not automatically send the data to the Driver Station Display.
     * Use sendTelemetry() for that.
     * @param key
     * @param message
     */
    public void addTelemetry(String key , float message) {
        addTelemetry(key, Float.toString(message));
    }

    /**
     * Note:  Adding data does not automatically send the data to the Driver Station Display.
     * Use sendTelemetry() for that.
     * @param key
     * @param message
     */
    public void addTelemetry(String key, boolean message) {
        addTelemetry(key, Boolean.toString(message));
    }

    /**
     * Note:  Adding data does not automatically send the data to the Driver Station Display.
     * Use sendTelemetry() for that.
     * @param key
     * @param message
     */
    public void addTelemetry(String key , String message) {

        if (isTelemetryEnabled()) {
            String displayName = name != null ? name + " : " : "";
            getOpMode().getTelemetryUtil().addData(displayName + key , message);
        }

    }


    /**
     *
     * @return name of component
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name of component used for debugging
     */
    public void setName(String name) {
        this.name = name;
    }

    protected ActiveOpMode getOpMode() {
        return opMode;
    }


}
