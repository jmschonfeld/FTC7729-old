package org.ftcbootstrap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.ftcbootstrap.components.TimerComponent;
import org.ftcbootstrap.components.utils.ErrorUtil;
import org.ftcbootstrap.components.utils.TelemetryUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * OpMode Abstract class that offers specialized telemetry and error handling to ensure that a LinearOpMode
 * runs smoothly in its "active" hardware state
 */
public abstract class ActiveOpMode extends LinearOpMode {

    private TimerComponent timerComponent;
    private TelemetryUtil telemetryUtil = new TelemetryUtil(this);
    private boolean operationsCompleted;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    abstract protected void onInit();

    /**
     * override to this method to perform one time operations after start is pressed
     */
    protected void onStart() throws InterruptedException {
        this.resetStartTime();
        getTelemetryUtil().reset();
    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    abstract protected void activeLoop() throws InterruptedException;

    /**
     * @return
     */
    public TelemetryUtil getTelemetryUtil() {
        return telemetryUtil;
    }


    /**
     * Override this method only if you need to do something outside of onInit() and activeLoop()
     *
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {

        try {
            setup();
            onInit();
        } catch (Throwable e) {
            ErrorUtil.handleCatchAllException(e,getTelemetryUtil());
        }

        waitForStart();

        onStart();


        while (opModeIsActive() && !operationsCompleted) {

            try {
                activeLoop();
            } catch (Throwable e) {
                ErrorUtil.handleCatchAllException(e, getTelemetryUtil());;
            }

            waitOneFullHardwareCycle();
        }

        //wait for user to hit stop
        while (opModeIsActive()) {
            waitOneFullHardwareCycle();
        }


    }

    private void setup() {

        timerComponent = new TimerComponent(this);

    }


    /**
     * Clear data from the telemetry cache
     */
    public void clearTelemetryData() {
        telemetry.clearData();
        try {
            if (opModeIsActive()) {
                waitForNextHardwareCycle();
            }
        } catch (InterruptedException e) {
        }
    }


    /**
     * Send telemetry to the Driver Station App
     *
     * @param key
     * @param message Note.  {@link TelemetryUtil#addData(String, String)}  only puts the data in a queue for later ordering.
     * <p/>
     * To send the data on to the  Driver Station use: {@link TelemetryUtil#sendTelemetry()}
     */
    public void sendTelemetryData(String key, String message) {
        telemetry.addData(key, message);

    }


    /**
     * call to prevent leave the loop calling activeLoop()
     */
    protected void setOperationsCompleted() {
        this.operationsCompleted = true;
        getTelemetryUtil().addData("Opmode Status", "Operations completed");
    }

    public TimerComponent getTimer() {
        return timerComponent;
    }
}
