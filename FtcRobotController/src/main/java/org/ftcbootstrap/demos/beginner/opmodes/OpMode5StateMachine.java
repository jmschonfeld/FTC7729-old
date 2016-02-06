package org.ftcbootstrap.demos.beginner.opmodes;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.TankDrive;
import org.ftcbootstrap.components.operations.motors.TankDriveToTime;
import org.ftcbootstrap.components.utils.DriveDirection;
import org.ftcbootstrap.demos.beginner.MyFirstBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "MyFirstBot" and creating a class by the same name: {@link MyFirstBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity}
 * <p/>
 * Summary:
 *  <p/> Use an Operation class to perform a tank drive on two motors for a duration of time.
 *  <p/> Introduce state machine to haandle several drive steps
 * See:  {@link TankDrive}
 */
public class OpMode5StateMachine extends ActiveOpMode {

    private MyFirstBot robot;
    private TankDriveToTime tankDriveToTime;
    private int step;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = MyFirstBot.newConfig(hardwareMap, getTelemetryUtil());

        //create an operation to perform a tank drive
        tankDriveToTime = new TankDriveToTime(this, robot.getMotor1(), robot.getMotor2());
        tankDriveToTime.setOpModeLogLevel(1);

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        step = 1;

    }






    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        switch (step) {
            case 1:
                getTelemetryUtil().addData("step" + step + ": ", "Drive forward for power:1 for 4 seconds");
                if (tankDriveToTime.runToTarget( 1, 4 , DriveDirection.DRIVE_FORWARD)) {
                    step++;
                }
                break;

            case 2:
                getTelemetryUtil().addData("step" + step + ": ", "Pivoting right for power:.5 for 2 seconds");
                if (tankDriveToTime.runToTarget(0.5, 2 , DriveDirection.PIVOT_FORWARD_RIGHT)) {
                    step++;
                }
                break;

            case 3:
                getTelemetryUtil().addData("step" + step + ": ", "Drive forward for power:1 for 6 seconds");
                if (tankDriveToTime.runToTarget( 1, 6 , DriveDirection.DRIVE_FORWARD)) {
                    step++;
                }
                break;

            default:
                setOperationsCompleted();
                break;

        }



        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }

}
