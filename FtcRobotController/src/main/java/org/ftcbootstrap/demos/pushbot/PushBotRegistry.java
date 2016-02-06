package org.ftcbootstrap.demos.pushbot;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

import org.ftcbootstrap.demos.TelemetryTest;
import org.ftcbootstrap.demos.pushbot.opmodes.PushBotAuto;
import org.ftcbootstrap.demos.pushbot.opmodes.PushBotManual;
import org.ftcbootstrap.demos.pushbot.opmodes.PushBotManual2;
import org.ftcbootstrap.demos.pushbot.opmodes.PushBotAutoSensors;
import org.ftcbootstrap.demos.pushbot.opmodes.PushBotOdsFollowEvent;
import org.ftcbootstrap.demos.pushbot.opmodes.PushBotOdsDetectEvent;
import org.ftcbootstrap.demos.pushbot.opmodes.PushBotTouchEvent;


/**
 * Register Op Modes
 */
public class PushBotRegistry implements OpModeRegister {

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
        manager.register("PushBotAuto", PushBotAuto.class);
        manager.register("PushBotAutoSensors", PushBotAutoSensors.class);
        manager.register("PushBotManual", PushBotManual.class);
        manager.register("PushBotManual2", PushBotManual2.class);
        manager.register("PushBotOdsDetectEvent", PushBotOdsDetectEvent.class);
        manager.register("PushBotOdsFollowEvent", PushBotOdsFollowEvent.class);
        manager.register("PushBotTouchEvent", PushBotTouchEvent.class);

        manager.register("TelemetryTest", TelemetryTest.class);

    }
}
