package org.ftcbootstrap.components;


import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.LED;

import org.ftcbootstrap.ActiveOpMode;


/**
 *  Helper class taken from qualcomm example
 *  see {@link com.qualcomm.ftcrobotcontroller.opmodes.ColorSensorDriver}
 */

public class ColorSensorComponent extends OpModeComponent {

    private ColorSensor colorSensor;
    private ColorSensorDevice device;

    public enum ColorSensorDevice {ADAFRUIT, HITECHNIC_NXT, MODERN_ROBOTICS_I2C}

    /**
     * Constructor for component
     * Telemetry enabled by default.
     * @param opMode
     * @param colorSensor
     * @param device
     */
    public ColorSensorComponent(ActiveOpMode opMode, ColorSensor colorSensor, ColorSensorDevice device ) {

        super(opMode);

        this.colorSensor = colorSensor;
        this.device = device;
        //set to true to illuminate initially
        enableLed(true);

    }




    /**
     * @return the RGB Red value
     */
    public int getR() {

        int color = 0;
        switch (device) {
            case HITECHNIC_NXT:
                color = colorSensor.red();
                break;
            case ADAFRUIT:
                color = (colorSensor.red() * 255) / 800;
                break;
            case MODERN_ROBOTICS_I2C:
                color = colorSensor.red() * 8;
                break;
        }
        return color;
    }

    /**
     * @return the RGB Green value
     */
    public int getG() {

        int color = 0;
        switch (device) {
            case HITECHNIC_NXT:
                color = colorSensor.green();
                break;
            case ADAFRUIT:
                color = (colorSensor.green() * 255) / 800;
                break;
            case MODERN_ROBOTICS_I2C:
                color = colorSensor.green() * 8;
                break;
        }
        return color;
    }

    /**
     * @return the RGB Blue value
     */
    public int getB() {

        int color = 0;
        switch (device) {
            case HITECHNIC_NXT:
                color = colorSensor.blue();
                break;
            case ADAFRUIT:
                color = (colorSensor.blue() * 255) / 800;
                break;
            case MODERN_ROBOTICS_I2C:
                color = colorSensor.blue() * 8;
                break;
        }
        return color;
    }

    /**
     * Convenience method for adding relevant sensors value to telemetry
     */
    public void addTelemetry(String name) {

        getOpMode().getTelemetryUtil().addData(name + ": Clear", colorSensor.alpha());
        getOpMode().getTelemetryUtil().addData(name + ": Red  ", getR());
        getOpMode().getTelemetryUtil().addData(name + ": Green", getG());
        getOpMode().getTelemetryUtil().addData(name + ": Blue ", getB());
        getOpMode().getTelemetryUtil().addData(name + ": Hue", getHSCValues()[0]);

    }


    public float[] getHSCValues() {

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};
        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        Color.RGBToHSV(getR(), getG(), getB(), hsvValues);

        return hsvValues;


    }

    /**
     * @param r sensor red reading must >= param
     * @param g sensor green reading must <= param
     * @param b sensor blue reading must be <= param
     * @return boolean
     */
    public boolean isRed(int r, int g, int b) {

        return (getR() >= r ) && (getG() <= g ) && (getB() <= b);

    }

    /**
     * @param r sensor red reading must <= parqm
     * @param g sensor green reading must >= param
     * @param b sensor blue reading must be <= param
     * @return boolean
     */
    public boolean isGreen(int r, int g, int b) {

        return (getR() <= r) && (getG() >= g) && (getB() <= b);

    }

    /**
     * @param r sensor red reading must <= parqm
     * @param g sensor green reading must <= param
     * @param b sensor blue reading must be >= param
     * @return boolean
     */
    public boolean isBlue(int r, int g, int b) {

        return (getR() <= r) && (getG() <= g) && (getB() >= b);

    }

    /**
     * Active Mode .  LED is enabled so the sensor can measure the light reflected
     * off of the surface of the target
     * Passive Mode.  LED is off.  Sensor focus on ambient light.
     *
     * @param value boolean
     */
    public void enableLed(boolean value) {
        switch (device) {
            case HITECHNIC_NXT:
                colorSensor.enableLed(value);
                break;
            case ADAFRUIT:
                LED led = getOpMode().hardwareMap.led.get("led");
                led.enable(value);
                break;
            case MODERN_ROBOTICS_I2C:
                colorSensor.enableLed(value);
                break;
        }
    }
}
