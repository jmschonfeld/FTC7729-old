package org.ftcbootstrap.components.operations.servos;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.OpModeComponent;
import org.ftcbootstrap.components.ServoComponent;


/**
 * Operation to assist with stopping a servo with a touch sensor
 */
public class ServoToTouch  extends OpModeComponent {

    private TouchSensor touchSensor;
    private ServoComponent servoComponent;
    private String name;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param name String used for debugging
     * @param opMode
     * @param touchSensor TouchSensor
     * @param servo Servo
     * @param initialPosition must set the initial position of the servo before working with it
     */
    public ServoToTouch(String name, ActiveOpMode opMode,  TouchSensor touchSensor ,Servo servo, double initialPosition) {

        super(opMode);
        this.touchSensor = touchSensor;
        this.servoComponent = new ServoComponent(opMode,  servo,   initialPosition);
        this.name = name;
    }


    /**
     * Update the servo position by an incremental value
     * @param servoDelta value to increment the current position by
     */
    public void incrementServoTargetPosition( double servoDelta) {

        servoComponent.incrementServoTargetPosition(servoDelta);
        addTelemetry(name + "servo targeted", servoComponent.getServoPosition());


    }

    /**
     * check if current touch sensor is pressed
     * @return boolean true is touch sensor is pressed
     */
    public boolean targetReached() {

        boolean result =  touchSensor.isPressed();
        addTelemetry(name + ": Servo Target Reached", result);

        return result;
    }

    /**
     * convenience method for provide better abstraction of targetReached
     * @return
     */
    public boolean touchPressed() {

        return targetReached();

    }

}

