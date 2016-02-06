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

public class MotorToTime extends OpModeComponent {

    private DcMotor motor;
    private double startTime;
    private double targetTime;
    private boolean running = false;


    /**
     * Constructor for operation
     * Telemetry enabled by default.
     *
     * @param opMode
     * @param motor
     */
    public MotorToTime(String name, ActiveOpMode opMode, DcMotor motor) {

        super(opMode);
        this.motor = motor;
    }

    /**
     * default to forward direction
     *
     * @param power
     * @param targetTime
     * @return boolean targetReached
     * @throws InterruptedException
     */
    public boolean runToTarget(double power, double targetTime) throws InterruptedException {
        return runToTarget(power,targetTime, MotorDirection.MOTOR_FORWARD);
    }


    /**
     * @param power
     * @param targetTime
     * @param direction {@link MotorDirection}
     * @return boolean
     * @throws InterruptedException
     */
    public boolean runToTarget(double power, double targetTime, MotorDirection direction) throws InterruptedException {

        if( isRunning() ) {
            boolean reached =  this.targetReached();
            if ( reached) {
                stop();
            }
            return reached;
        }
        running = true;

        double powerWithDirection = (direction == MotorDirection.MOTOR_FORWARD) ? power : -power;

        this.targetTime = targetTime;
        this.startTime = getOpMode().getRuntime();
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

        boolean result = getOpMode().getRuntime() - startTime >= targetTime;
        addTelemetry("Motor Target Reached", result);

        return result;

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



}

