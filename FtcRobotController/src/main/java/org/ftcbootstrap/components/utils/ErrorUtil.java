package org.ftcbootstrap.components.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Util to prevent unhandled exceptions from crashing the app
 */
public class ErrorUtil {



    public static void handleCatchAllException(Throwable e , TelemetryUtil telemetryUtil) throws InterruptedException {
        telemetryUtil.addData("Opmode Exception", e.getMessage());
        String stckTrace = stackTraceAsString(e);
        telemetryUtil.addData("Opmode Stacktrace", stckTrace.substring(0, 200));
        // DbgLog.msg(e.getLocalizedMessage());
        //if( e instanceof Exception) {
        //DbgLog.error(stckTrace);

        //}


        telemetryUtil.sendTelemetry();
        if (e instanceof InterruptedException) {
            throw (InterruptedException) e;
        }
    }


    private static String stackTraceAsString(Throwable e) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();

    }

}
