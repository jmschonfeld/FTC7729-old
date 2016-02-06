package org.ftcbootstrap.demos.beginner;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

import org.ftcbootstrap.demos.TelemetryTest;
import org.ftcbootstrap.demos.beginner.opmodes.OpMode1RunForTime;
import org.ftcbootstrap.demos.beginner.opmodes.OpMode2RunForTime;
import org.ftcbootstrap.demos.beginner.opmodes.OpMode3RunForTime;
import org.ftcbootstrap.demos.beginner.opmodes.OpMode4RunUntilTouch;
import org.ftcbootstrap.demos.beginner.opmodes.OpMode5StateMachine;
import org.ftcbootstrap.demos.beginner.opmodes.OpMode6DriveWithGamepad;
import org.ftcbootstrap.demos.beginner.opmodes.OpMode7ServoWithGamepad;


/**
 * Register Op Modes
 */
public class MyFirstBotRegistry implements OpModeRegister {

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

    manager.register("OpMode1RunForTime", OpMode1RunForTime.class);
    manager.register("OpMode2RunForTime", OpMode2RunForTime.class);
    manager.register("OpMode3RunForTime", OpMode3RunForTime.class);
    manager.register("OpMode4RunUntilTouch", OpMode4RunUntilTouch.class);
    manager.register("OpMode5StateMachine", OpMode5StateMachine.class);
    manager.register("OpMode6DriveWithGamepad", OpMode6DriveWithGamepad.class);
    manager.register("OpMode7ServoWithGamepad", OpMode7ServoWithGamepad.class);

    manager.register("TelemetryTest", TelemetryTest.class);

    
  }
}
