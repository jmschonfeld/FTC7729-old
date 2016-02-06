package org.ftcbootstrap.components.operations.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.OpModeComponent;
import org.ftcbootstrap.components.utils.MotorDirection;


/**
 * Operation to assist with running a DcMotor with a touch sensor
 */

public class MotorToTouch extends OpModeComponent {

    private DcMotor motor;

    private boolean running = false;

    private TouchSensor touchSensor;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     *
     * @param opMode
     * @param motor
     * @param touchSensor
     */
    public MotorToTouch(String name, ActiveOpMode opMode, DcMotor motor, TouchSensor touchSensor) {

        super(opMode);
        this.motor = motor;
        this.touchSensor = touchSensor;
    }

    /**
     * default to forward direction
     *
     * @param power
     * @return boolean targetReached
     * @throws InterruptedException
     */
    public boolean runToTarget(double power) throws InterruptedException {
        return runToTarget(power, MotorDirection.MOTOR_FORWARD);
    }


    /**
     * @param power
     * @param direction {@link MotorDirection}
     * @return boolean
     * @throws InterruptedException
     */
    public boolean runToTarget(double power, MotorDirection direction) throws InterruptedException {

        if( isRunning() ) {
            boolean reached =  this.targetReached();
            if ( reached) {
                stop();
            }
            return reached;
        }
        running = true;

        double powerWithDirection = (direction == MotorDirection.MOTOR_FORWARD) ? power : -power;

        addTelemetry("Starting motor ", powerWithDirection);


        if (motor != null) {
            motor.setPower(powerWithDirection);
        }

        return false;
    }

    /**
     * check if current touch sensor is pressed
     *
     * @return boolean true is touch sensor is pressed
     */
    public boolean targetReached() {

        boolean result = touchSensor.isPressed();
        addTelemetry("Servo Target Reached", result);

        return result;

    }

    /**
     * convenience method for provide better abstraction of targetReached
     *
     * @return
     */
    public boolean touchPressed() {

        return targetReached();

    }

    /**
     * Stop the motor
     */
    public void stop() {
        addTelemetry("Stopping motor ", "stop");

        running = false;
        motor.setPower(0);

    }

    /**
     * Check if the operation is running.   Do this before calling runToTarget()
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    public void startRunMode(DcMotorController.RunMode runMode) throws InterruptedException {
        motor.setMode(runMode);
        getOpMode().waitOneFullHardwareCycle();

    }


}

