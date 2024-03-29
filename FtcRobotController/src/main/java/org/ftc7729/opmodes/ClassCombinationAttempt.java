/* Copyright (c) 2014 Qualcomm Technologies Inc
All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.ftc7729.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class ClassCombinationAttempt extends OpMode {

    final static double MOTOR_POWER = .50;

    DcMotor motorRight;
    DcMotor motorLeft;

    DcMotor motorTape;
    DcMotor motorAim;

    DcMotor motorHinges;

    //Servo hook;

    double tapeSpeedPercentage = 0.35;

    double position = 1.00;
    double motorMaxPercentage = 0.25;

    double totalPower = 0.00;

    /**
     * Constructor
     */
    public ClassCombinationAttempt() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        motorTape = hardwareMap.dcMotor.get("tape_lift");
        motorAim = hardwareMap.dcMotor.get("tape_aim");

        motorHinges = hardwareMap.dcMotor.get("hit_climbers");

        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorAim.setDirection(DcMotor.Direction.REVERSE);
        motorHinges.setDirection(DcMotor.Direction.REVERSE);

        tapeSpeedPercentage = 0.35;

        //hook = hardwareMap.servo.get("servo");

        //hook.setPosition(position);

    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */
        float left = -gamepad2.left_stick_y;
        float right = -gamepad2.right_stick_y;

        float tapeMeasure = -gamepad1.left_stick_y;
        float angle = -gamepad1.right_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        tapeMeasure = Range.clip(tapeMeasure, -1, 1);
        angle = Range.clip(angle, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        tapeMeasure = (float)scaleInput(tapeMeasure);
        angle = (float)scaleInput(angle);

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);

        motorAim.setPower((angle * motorMaxPercentage));

        if (gamepad2.dpad_right){
            //motorHinges.setPower(-.25);
            totalPower = -0.50;
        }
        else if (gamepad2.dpad_left){
            //motorHinges.setPower(.25);
            totalPower = 0.50;
        }
        else {
            totalPower = 0.00;
        }

        motorHinges.setPower(totalPower);

        if (gamepad1.a){
            tapeSpeedPercentage = 1.00;
        }
        if (gamepad1.y){
            tapeSpeedPercentage = 0.35;
        }

        motorTape.setPower(tapeMeasure * tapeSpeedPercentage);

		/*if (gamepad1.a){
			if (position >= .99){
				position = 1.00;
			}
			else {
				position += 0.001;
			}
		}
		if (gamepad1.b){
			if (position <= .26){
				position = .25;
			}
			else {
				position -= 0.001;
			}
		}
		hook.setPosition(position);
		*/
        // tank drived
        // note that if y equal -1 then joystick is pushed all of the way forward.

        //Working Version of the Servo Controller

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", tapeMeasure));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", position));
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}