package org.ftcbootstrap.demos.navbot.opmodes;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.operations.servos.GamePadClaw;
import org.ftcbootstrap.components.operations.servos.GamePadServo;
import org.ftcbootstrap.components.phone.AccelerometerComponent;
import org.ftcbootstrap.demos.navbot.NavBot;


/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "NavBot" and creating a class by the same name: {@link NavBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 * <p/>
 * See:
 * <p/>
 * {@link org.ftcbootstrap.components},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.servos},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.motors}
 * <p/>
 * Also see: {@link NavBot} for the saved configuration
 */
public class NavBotTelemetry extends ActiveOpMode {

    private NavBot robot;

    private MotorToEncoder leftMotorToEncoder;
    private MotorToEncoder rightMotorToEncoder;
    private GamePadTankDrive tankDrive;

    private GamePadMotor armMotor;
    private GamePadClaw claw;


    private ColorSensorComponent colorSensorComponent;
    private int step;
    private int stepCounter;

    private AccelerometerComponent accelerometerComponent;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = NavBot.newConfig(hardwareMap, getTelemetryUtil());

        leftMotorToEncoder = new MotorToEncoder( this, robot.getLeftDrive());
        leftMotorToEncoder.setName("left runToTarget");
        rightMotorToEncoder = new MotorToEncoder( this, robot.getRightDrive());
        rightMotorToEncoder.setName("right runToTarget");

        colorSensorComponent = new ColorSensorComponent(this, robot.getMrColor(), ColorSensorComponent.ColorSensorDevice.MODERN_ROBOTICS_I2C);
        colorSensorComponent.enableLed(false);

        accelerometerComponent =
                ((FtcRobotControllerActivity) hardwareMap.appContext).getAccelerometerComponent();


        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();

        step = 1;

        //set up tank runToTarget operation to use the gamepad joysticks
        tankDrive =  new GamePadTankDrive( this, gamepad1,robot.getLeftDrive(), robot.getRightDrive());
        tankDrive.startRunMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //set up arm motor operation using the up/down buttons
        armMotor = new GamePadMotor(this, gamepad1, robot.getArm(), GamePadMotor.Control.UP_DOWN_BUTTONS);

        //operate the claw with GamePadServo.Control. Use the X and B buttons for up and down and the  X and B buttons for left and right
        claw = new GamePadClaw(this, gamepad1, robot.getLeftClaw(), robot.getRightClaw(), GamePadServo.Control.X_B, 0.8);

        // calibrate the gyro.
        robot.getGyro().calibrate();

        // make sure the gyro is calibrated.
        while (robot.getGyro().isCalibrating())  {
            this.waitForNextHardwareCycle();
        }


    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        tankDrive.update();
        armMotor.update();
        claw.update();

        getTelemetryUtil().addData("01: Encoder Left Pos", robot.getLeftDrive().getCurrentPosition());
        getTelemetryUtil().addData("02: Encoder Right Pos", robot.getRightDrive().getCurrentPosition());

        getTelemetryUtil().addData("03a: Gyro heading:", robot.getGyro().getHeading());
        getTelemetryUtil().addData("03b: Gyro rawX:", robot.getGyro().rawX());
        getTelemetryUtil().addData("03c: Gyro rawY:", robot.getGyro().rawY());
        getTelemetryUtil().addData("03d: Gyro rawZ:", robot.getGyro().rawZ());
        getTelemetryUtil().addData("03e: Gyro getIntegratedZValue:", ((ModernRoboticsI2cGyro)robot.getGyro()).getIntegratedZValue());

        getTelemetryUtil().addData("04a accel x" , accelerometerComponent.getX());
        getTelemetryUtil().addData("04b accel y" ,accelerometerComponent.getY());
        getTelemetryUtil().addData("04c accel z", accelerometerComponent.getZ());

        getTelemetryUtil().addData("05a: Left Claw Positon:", robot.getLeftClaw().getPosition());
        getTelemetryUtil().addData("05b: Right Claw Positon:", robot.getRightClaw().getPosition());

        getTelemetryUtil().addData("06: ODS Bumper:", robot.getOdsBumper().getLightDetected());

        getTelemetryUtil().addData("07: ODS Ground:", robot.getOdsGround().getLightDetected());

        boolean isRed = colorSensorComponent.isRed(5, 2, 2);
        getTelemetryUtil().addData("08: isRed 5, 2, 2 ", isRed);
        boolean isBlue = colorSensorComponent.isBlue(10, 2, 2);
        getTelemetryUtil().addData("09: isBlue 10, 2, 2 ", isBlue);
        colorSensorComponent.addTelemetry("10: Color Sensor Values");

        getTelemetryUtil().addData("10 arm power", robot.getArm().getPower());




        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }

}
