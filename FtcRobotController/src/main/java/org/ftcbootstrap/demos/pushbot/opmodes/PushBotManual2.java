package org.ftcbootstrap.demos.pushbot.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.servos.GamePadClaw;
import org.ftcbootstrap.components.operations.servos.GamePadServo;
import org.ftcbootstrap.demos.pushbot.PushBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "Pushbot" and creating a class by the same name: {@link PushBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary: Use one gamepad's joysticks to Tank Drive and another gamepad to operate Arm with the left joystick
 * and operate the claw with left/right buttons
 * <p/>
 * Refactored from the original Qualcomm PushBot examples to demonstrate the use of the latest
 * reusable components and operations
 * See:
 * <p/>
 * {@link org.ftcbootstrap.components},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.servos},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.motors}
 * <p/>
 * Also see: {@link PushBot} for the saved configuration
 */
public class PushBotManual2 extends ActiveOpMode {

  private GamePadTankDrive tankDrive;
  private GamePadMotor armMotorStick;
  private GamePadClaw claw;

  private PushBot robot;

  /**
   * Implement this method to define the code to run when the Init button is pressed on the Driver station.
   */
  @Override
  protected void onInit() {

    robot = PushBot.newConfig(hardwareMap, getTelemetryUtil());

    getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
    getTelemetryUtil().sendTelemetry();

  }

  @Override
  protected void onStart() throws InterruptedException  {
    super.onStart();

    //set up tank drive operation to use the gamepad joysticks
    tankDrive =  new GamePadTankDrive( this,gamepad1, robot.getLeftDrive(), robot.getRightDrive());
    tankDrive.startRunMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

    //set up arm motor operation using the gamepad's left joystick
    armMotorStick = new GamePadMotor(this, gamepad2, robot.getLeftArm(),  GamePadMotor.Control.LEFT_STICK_Y  );

    //operate the claw with left/right buttons
    claw = new GamePadClaw(this, gamepad2, robot.getLeftHand(),robot.getRightHand() , GamePadServo.Control.X_B  , 0.5);


  }

  @Override
  protected void activeLoop()  throws InterruptedException {

    //multiple gamepads
    tankDrive.update();
    armMotorStick.update();
    claw.update();

    //send any telemetry that may have been added in the above operations
    getTelemetryUtil().sendTelemetry();

  }




}
