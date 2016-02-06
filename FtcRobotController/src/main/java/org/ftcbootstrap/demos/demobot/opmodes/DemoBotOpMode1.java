package org.ftcbootstrap.demos.demobot.opmodes;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
import org.ftcbootstrap.components.operations.servos.GamePadServo;
import org.ftcbootstrap.components.operations.servos.ServoToTouch;
import org.ftcbootstrap.demos.demobot.DemoBot;
import org.ftcbootstrap.ActiveOpMode;

import com.qualcomm.ftcrobotcontroller.R;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link DemoBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 * <p/>
 * OpMode that uses operations to control various motors and servos using sensors and the gamepad.
 * There is also the an example of reading a color sensor to change the background color of the Robot
 * Controller App.
 * <p/>
 * See: {@link GamePadMotor} ,  {@link GamePadServo}  , {@link ServoToTouch}
 */
public class DemoBotOpMode1 extends ActiveOpMode {

    private DemoBot robot;
    private GamePadMotor gamePadMotor1;
    private GamePadMotor gamePadMotor2;
    private GamePadServo gamePadServo;
    private ServoToTouch servoToTouch;
    private ColorSensorComponent colorSensorComponent;
    private View relativeLayout;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name saved from scan operation
        robot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

         //create an operation to stop the servo with a touch sensor
        servoToTouch = new ServoToTouch("touch sensor for servo1", this, robot.getTouch1(), robot.getServo1(), 0.5);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        colorSensorComponent = new ColorSensorComponent(this, robot.getMrColor1(), ColorSensorComponent.ColorSensorDevice.MODERN_ROBOTICS_I2C);
        colorSensorComponent.enableLed(false);

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();

        //create operations that turn the motors by moving the joysticks left and right
        gamePadMotor1 = new GamePadMotor(this,gamepad1, robot.getMotor1(), GamePadMotor.Control.LEFT_STICK_X);
        gamePadMotor2 = new GamePadMotor(this, gamepad1, robot.getMotor2(), GamePadMotor.Control.RIGHT_STICK_X);

        //create an operation to move the servo with gamepad button y,a (updown)
        gamePadServo = new GamePadServo(this,gamepad2, robot.getServo1(), GamePadServo.Control.Y_A, 0.5);

        colorSensorComponent.enableLed(true);
    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the motors with the  gamepad joystick values
        gamePadMotor1.update();
        gamePadMotor2.update();

        //update the servo from the  gamepad button events
        gamePadServo.update();

        //another way to change the servo position
        if (servoToTouch.touchPressed()) {
            servoToTouch.incrementServoTargetPosition(0.01);
        }

        getTelemetryUtil().addData("ods  ld", robot.getOds1().getLightDetected());
        getTelemetryUtil().addData("ods  ld raw", robot.getOds1().getLightDetectedRaw());

        addColorTelemetry();

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }

    /**
     * Taken from qualcomm example
     * See: {@link com.qualcomm.ftcrobotcontroller.opmodes.ColorSensorDriver}
     * <p/>
     * Send relevant color sensor telemetry to the driver station.  Also change the background of the
     * robot control app to reflect the color seen by the sensor
     */
    private void addColorTelemetry() {

        colorSensorComponent.addTelemetry("color sensor values");


        // change the background color to match the color detected by the RGB sensor.
        // pass a reference to the hue, saturation, and value array as an argument
        // to the HSVToColor method.
        final float[] values = colorSensorComponent.getHSCValues();
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });


    }

}
