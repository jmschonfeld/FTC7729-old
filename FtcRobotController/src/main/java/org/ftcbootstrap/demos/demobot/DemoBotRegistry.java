package org.ftcbootstrap.demos.demobot;

import com.qualcomm.ftcrobotcontroller.opmodes.NullOp;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

import org.ftcbootstrap.demos.TelemetryTest;
import org.ftcbootstrap.demos.demobot.opmodes.DemoBotAdvancedOpMode;
import org.ftcbootstrap.demos.demobot.opmodes.DemoBotOpMode1;
import org.ftcbootstrap.demos.demobot.opmodes.DemoBotTeleOpMode;
import org.ftcbootstrap.demos.demobot.opmodes.EncoderMotorOpMode;
import org.ftcbootstrap.demos.demobot.opmodes.EncoderTankDriveOpMode;

/**
 * Register Op Modes
 */
public class DemoBotRegistry implements OpModeRegister {

    /**
     * The Op Mode Manager will call this method when it wants a list of all
     * available op modes. Add your op mode to the list to enable it.
     *
     * @param manager op mode manager
     */
    public void register(OpModeManager manager) {

    /*
     * register your op modes here.
     * The first parameter is the name of the op mode
     * The second parameter is the op mode class property
     *
     * If two or more op modes are registered with the same name, the app will display an error.
     */

        manager.register("DemoBotOpMode1", DemoBotOpMode1.class);
        manager.register("DemoBotTeleOpMode", DemoBotTeleOpMode.class);
        manager.register("EncoderMotorOpMode", EncoderMotorOpMode.class);
        manager.register("EncoderTankDriveOpMode", EncoderTankDriveOpMode.class);
        manager.register("DemoBotAdvancedOpMode", DemoBotAdvancedOpMode.class);

        manager.register("TelemetryTest", TelemetryTest.class);


    }
}
