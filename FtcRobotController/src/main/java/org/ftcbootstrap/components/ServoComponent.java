package org.ftcbootstrap.components;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.ftcbootstrap.ActiveOpMode;


/**
 * Component for performing actions on a Servo
 */
public class ServoComponent extends OpModeComponent {

    private Servo servo;
    // position of the servo
    private double servoPosition;
    private boolean reverse;

    /**
     * Constructor for component
     * Telemetry enabled by default.
     *
     * @param opMode
     * @param servo
     * @param initialPosition
     */
    public ServoComponent(ActiveOpMode opMode, Servo servo, double initialPosition) {

        this(opMode, servo, initialPosition, false);

    }

    /**
     * Constructor for component
     * @param opMode
     * @param servo
     * @param initialPosition
     * @param reverseOrientation true if the servo is install in the reverse orientation
     */
    public ServoComponent(ActiveOpMode opMode, Servo servo, double initialPosition, boolean reverseOrientation) {

        super(opMode);

        this.servo = servo;

        this.reverse = reverseOrientation;

        // set the starting position of the servo
        updateServoTargetPosition(initialPosition);

        addTelemetry("adding servo component pos: " , initialPosition);


    }

    /**
     * Assign a new value to the servo's position
     *
     * @param newServoPosition
     */
    public void updateServoTargetPosition(double newServoPosition) {

        // clip the position values so that they never exceed 0..1
        servoPosition = Range.clip(newServoPosition, Servo.MIN_POSITION, Servo.MAX_POSITION);

        if (!reverse) {
            servo.setPosition(servoPosition);
        } else {
            servo.setPosition(1 - servoPosition);
        }

        addTelemetry("servoPosition", servoPosition); ;

    }

    /**
     * Increment the value of the servo with the new value passed in
     *
     * @param newServoPosition
     */
    public void incrementServoTargetPosition(double newServoPosition) {

        updateServoTargetPosition(getServoPosition() + newServoPosition);

    }

    /**
     * Accessor method for servo position
     *
     * @return
     */
    public double getServoPosition() {
        return servoPosition;
    }


}
