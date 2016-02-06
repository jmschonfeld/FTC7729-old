package org.ftcbootstrap.components.operations.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.OpModeComponent;

/**
 * Operation to assist with basic tank drive using a gamepad
 */
public class GamePadTankDrive extends OpModeComponent {

    private GamePadMotor leftMotorStick;
    private GamePadMotor rightMotorStick;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param leftMotor DcMotor
     * @param rightMotor DcMotor
     */
    public GamePadTankDrive(ActiveOpMode opMode, Gamepad gamepad, DcMotor leftMotor, DcMotor rightMotor) {

        super(opMode);
        this.leftMotorStick = new GamePadMotor(opMode,  gamepad, leftMotor, GamePadMotor.Control.LEFT_STICK_Y);
        this.rightMotorStick = new GamePadMotor(opMode,gamepad, rightMotor,  GamePadMotor.Control.RIGHT_STICK_Y);
    }

    /**
     * Update motors with current gamepad state
     */
    public void update() {
        leftMotorStick.update();
        rightMotorStick.update();
    }


    public void startRunMode(DcMotorController.RunMode runMode)  throws InterruptedException {
        leftMotorStick.startRunMode( runMode);
        rightMotorStick.startRunMode( runMode);
    }

}
