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

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class driveCode extends OpMode {



    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorWinch;
    DcMotor motorIntake;

    Servo ratchet;
    Servo shifter1;
    Servo shifter2;
    Servo autonomousArm;
    Servo hook1;
    Servo hook2;

    boolean servoPosition;
    double b;
    double shift2;
    double shift1;
    boolean c;
    double k;
    double l;
    public ElapsedTime calibrate = new ElapsedTime();




    /**
     * Constructor
     */
    public driveCode() {

    }


    @Override
    public void init() {

        motorRight = hardwareMap.dcMotor.get("motor_right");
        motorWinch = hardwareMap.dcMotor.get("motor_winch");
        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorIntake = hardwareMap.dcMotor.get("motor_intake");

        autonomousArm = hardwareMap.servo.get("servo_auto");
        ratchet = hardwareMap.servo.get("servo_ratchet");
        shifter1 = hardwareMap.servo.get("servo_shifter1");
        shifter2 = hardwareMap.servo.get("servo_shifter2");
        hook1 = hardwareMap.servo.get("servo_rfang");
        hook2 = hardwareMap.servo.get("servo_lfang");

        calibrate.startTime();

        shifter1.setPosition(0.472);
        shifter2.setPosition(0.590);
        shift1 = 0.605;
        shift2 = 0.455;
        hook1.setPosition(0.5);
        hook2.setPosition(1);
        ratchet.setPosition(0.65);
        autonomousArm.setPosition(1);

        k = 0.472;
        l = 0.590;

        //POS THREE FOR 1: 0.96862
        //POS THREE FOR 2: 0.08627

    }

    @Override
    public void loop() {
        shifter1.setPosition(k);
        shifter2.setPosition(l);

    /*    if (gamepad1.right_bumper){
            autonomousArm.setPosition(0.85);

        }
        else{
            autonomousArm.setPosition(0.15);
        }
        */


        if (gamepad1.right_bumper){
            hook1.setPosition(0.5);
            hook2.setPosition(1);
        }
        else{
            hook1.setPosition(0.8);
            hook2.setPosition(0.4);
        }

        if (gamepad1.left_bumper){
            autonomousArm.setPosition(0);
        }
        else {
            autonomousArm.setPosition(1);
        }

     /*   if (gamepad1.dpad_down){
            ratchet.setPosition(1);
        }
        else {
            ratchet.setPosition(0.65);
        }
*/


        if (gamepad2.a){
            shifter1.setPosition(0.595);
            shifter2.setPosition(0.465);
            shift1 = 0.605;
            shift2 = 0.455;
            servoPosition = true;

        }
        else if(gamepad2.b){
            shifter1.setPosition(0.31);
            shifter2.setPosition(0.74);
            servoPosition =  true;
            shift1 = 0.32;
            shift2 = 0.73  ;


        }
        else if (gamepad2.x){
            shifter1.setPosition(0.11);
            shifter2.setPosition(0.94);
            servoPosition = true;
            shift1 = 0.1;
            shift2 = 0.95;

        }
        else if (gamepad2.y){
            shifter1.setPosition(0.2);
            shifter2.setPosition(0.85);
            servoPosition = true;
            shift1 = 15;
            shift2 = 0.9;

        }
        else{
            servoPosition = false;
        }

        if ((gamepad1.x) && (calibrate.time()>0.25)) {
            k += 0.05;
            l -= 0.05;
            calibrate.reset();
        }




        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);


        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        // write the values to the motors


        motorIntake.setPower(-gamepad2.right_stick_y);

      /*  if ((shifter1.getPosition()>shift1+0.01)||(shifter1.getPosition()<shift1-0.01)){
            motorWinch.setPower(0.3);
        }
         else if ((shifter1.getPosition()>0.79)) {
            motorWinch.setPower(-gamepad2.left_stick_y);
            motorRight.setPower(-gamepad2.left_stick_y);
            motorLeft.setPower(-gamepad2.left_stick_y);
        }
        else {
            motorRight.setPower(right);
            motorLeft.setPower(left);
            motorWinch.setPower(-gamepad2.left_stick_y);
        }
*/

        //if (!servoPosition){
        //    motorWinch.setPower(0.3);
        //}
       /* else if ((shifter1.getPosition()>0.89)&&(shifter1.getPosition()<0.91)){
            motorRight.setPower(-gamepad2.left_stick_y);
            motorLeft.setPower(gamepad2.left_stick_y);
            motorWinch.setPower(-gamepad2.left_stick_y);
        }
        */
        //else {
            motorRight.setPower(right);
            motorLeft.setPower(left);
            motorWinch.setPower(-gamepad2.left_stick_y);
        //}





        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
        telemetry.addData("Autonomous Arm",autonomousArm.getPosition());
        telemetry.addData("c",c);
        telemetry.addData("Shifter Servo Status",servoPosition);
        telemetry.addData("Shifter1 Pos",shifter1.getPosition());
        telemetry.addData("Shifter2 Pos",shifter2.getPosition());
        telemetry.addData("Shifter1 Tar",shift1);
        telemetry.addData("Shifter2 Tar",shift2);

    }

    @Override
    public void stop() {

    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        int index = (int) (dVal * 16.0);

        if (index < 0) {
            index = -index;
        }

        if (index > 16) {
            index = 16;
        }

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
