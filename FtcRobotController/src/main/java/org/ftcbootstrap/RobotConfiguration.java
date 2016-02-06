package org.ftcbootstrap;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftcbootstrap.components.utils.ErrorUtil;
import org.ftcbootstrap.components.utils.TelemetryUtil;

import java.io.StringWriter;
import java.io.PrintWriter;



/**
 * Abstract class to use as parent to the class you will define to mirror a "saved configuration" on the Robot controller
 */
public abstract class RobotConfiguration {

    private TelemetryUtil telemetryUtil;

    /**
     * assign your class instance variables to the saved device names in the hardware map
     * @param hardwareMap
     * @param telemetryUtil
     */
    abstract protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil);

    /**
     * accessor for the telemetry utility
     *
     * @return {@link TelemetryUtil}
     */
    protected TelemetryUtil getTelemetryUtil() {
        return telemetryUtil;
    }

    /**
     * setter method for telemetry utility
     * @param {@link TelemetryUtil}
     */
    protected void setTelemetry(TelemetryUtil telemetryUtil) {
        this.telemetryUtil = telemetryUtil;
    }

    /**
     * Convenience method for reading the devce from the hardwareMap without having to check for exceptions
     * @param name name of device saved in the configuration file
     * @param hardwareDeviceMapping
     * @return
     */
    protected  HardwareDevice getHardwareOn(String name, Object hardwareDeviceMapping) {

        HardwareDevice hardwareDevice = null;
        try {
            HardwareMap.DeviceMapping<HardwareDevice> deviceMapping  =  (HardwareMap.DeviceMapping<HardwareDevice>) hardwareDeviceMapping;
            hardwareDevice = (HardwareDevice)deviceMapping.get(name);
        }
        catch (Throwable e)
        {
            try {
                ErrorUtil.handleCatchAllException(e, getTelemetryUtil());
            } catch (InterruptedException e1) {
                DbgLog.msg(e.getLocalizedMessage());
            }

        }

        return hardwareDevice;
    }





}
