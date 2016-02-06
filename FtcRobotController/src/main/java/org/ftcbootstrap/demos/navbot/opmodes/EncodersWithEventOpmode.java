package org.ftcbootstrap.demos.navbot.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


public class EncodersWithEventOpmode extends OpMode {

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    int hc = 0;


    @Override
    public void init() {
        //specify configuration name save from scan operation
        leftDrive = (DcMotor) hardwareMap.dcMotor.get("mc1_1_left_drive");
        rightDrive = (DcMotor) hardwareMap.dcMotor.get("mc1_2_right_drive");
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        leftDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    @Override
    public void start() {
        super.start();

        leftDrive.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

    }


    @Override
    public void loop() {


        if ( hc++ == 4 ) {
            leftDrive.setPower(1);
            rightDrive.setPower(1);
            leftDrive.setTargetPosition(20000);
            rightDrive.setTargetPosition(20000);

        }
        if ( hc++ >= 5 ) {

            int lp = leftDrive.getCurrentPosition();
            int rp = rightDrive.getCurrentPosition();
            telemetry.addData("left motor position: ", lp);
            telemetry.addData("right motor position: ", rp);
            telemetry.addData("dif: ", lp - rp);

        }

        if ( leftDrive.getCurrentPosition() >= 20000 ||  rightDrive.getCurrentPosition() >= 20000) {
            leftDrive.setPower(0.5);
            rightDrive.setPower(0.5);
            leftDrive.setTargetPosition(30000);
            rightDrive.setTargetPosition(30000);
        }
    }



}
