package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by jzerez17 on 4/16/16.
 */
public class MR_RGB_Opmode extends OpMode{

    ColorSensor mr;
    public ElapsedTime toggleCoolDown = new ElapsedTime();
    boolean toggle = true;
    boolean LED = true;

    public MR_RGB_Opmode(){

    }

    @Override
    public void init(){

        mr = hardwareMap.colorSensor.get("mr");
    }

    @Override
    public void start(){
        toggleCoolDown.startTime();
    }

    @Override
    public void loop(){
        if (gamepad1.x && toggle) {
            LED = !LED;
            toggle=false;
            mr.enableLed(LED);
            toggleCoolDown.reset();
        }

        if (toggleCoolDown.time() > 0.5) {
            toggle = true;

            telemetry.addData("toggle", toggle);
            telemetry.addData("toggleCoolDown", toggleCoolDown.time());
            telemetry.addData("Red", mr.red());
            telemetry.addData("Green", mr.green());
            telemetry.addData("Blue", mr.blue());
            telemetry.addData("Brightness", mr.alpha());
            telemetry.addData("Hue", mr.argb());
        }
    }

    @Override
    public void stop(){

    }
}
