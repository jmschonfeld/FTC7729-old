package org.ftcbootstrap.components.operations.servos;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.OpModeComponent;

/**
 * Operation to assist with operating a claw that consists of two servos
 */

public class GamePadClaw extends OpModeComponent {

    private GamePadServo leftClaw;
    private GamePadServo rightClaw;
    private ActiveOpMode opMode;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param gamepad Gamepad
     * @param leftServo Servo
     * @param rightServo Servo
     * @param control  GamePadServo.Control use the Y and A buttons for up and down and the  X and B buttons for left and right
     * @param initialPosition must set the initial position of the servo before working with it
     */
    public GamePadClaw(ActiveOpMode opMode,  Gamepad gamepad , Servo leftServo, Servo rightServo , GamePadServo.Control control, double initialPosition) {

        super(opMode);
        this.leftClaw = new GamePadServo( opMode,gamepad,leftServo,  control,  initialPosition ,true);
        this.rightClaw = new GamePadServo( opMode,gamepad, rightServo, control,  initialPosition);
    }

    /**
     * Update motors with latest gamepad state
     */
    public void update() {
        leftClaw.update();
        rightClaw.update();
    }

}
