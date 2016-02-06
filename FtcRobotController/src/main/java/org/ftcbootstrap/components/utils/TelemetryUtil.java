package org.ftcbootstrap.components.utils;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import org.ftcbootstrap.ActiveOpMode;

/**
 * Helper class for standard opmode.telemetry feature
 * Utilities include:
 * 1) Adding data does not send to the Driver station display until issuing a second sendData command.
 * This allows you to organize all of your telemetry data before sending them out
 * 2) adds timestamps to the data'
 *
 */
public class TelemetryUtil  {

    private long startTime;
    private Map<String, TelemetryModel> teleMap ;
    private ActiveOpMode opMode;
    boolean sortByTime;

    /**
     * Constructor for class
     * @param opMode
     */
    public TelemetryUtil(ActiveOpMode opMode) {
        this.opMode = opMode;
        reset();
    }

    /**
     * Clear data from the telemetry cache
     */
    public void reset() {
        Date d = new Date();
        startTime = d.getTime();
        teleMap = new HashMap<String, TelemetryModel>();
        sortByTime = false;
        opMode.clearTelemetryData();


    }

    /**
     * Note:  Adding data does not automatically send the data to the Driver Station Display.
     * Use sendTelemetry() for that.
     * @param key
     * @param message
     */
    public void addData(String key , double message) {
        addData(key ,  Double.toString(message));
    }

    /**
     * Note:  Adding data does not automatically send the data to the Driver Station Display.
     * Use sendTelemetry() for that.
     * @param key
     * @param message
     */
    public void addData(String key , float message) {
        addData(key ,  Float.toString(message));
    }

    /**
     * Note:  Adding data does not automatically send the data to the Driver Station Display.
     * Use sendTelemetry() for that.
     * @param key
     * @param message
     */
    public void addData(String key, boolean message) {
        addData(key, Boolean.toString(message));
    }

    /**
     * Note:  Adding data does not automatically send the data to the Driver Station Display.
     * Use sendTelemetry() for that.
     * @param key
     * @param message
     */
    public void addData(String key , String message) {

        long calcTime = calcElapsedTime();
        //pad with leading zeros so it sorts
        String formattedTime = String.format("%06d",calcTime);

        TelemetryModel model;
        if ( this.isSortByTime()) {
            model = new TelemetryModel();
            model.setKey(formattedTime + " " + key);
            model.setMessage(message);

        }
        else {
            model = teleMap.get(key);
            if ( model == null ) {
                model = new TelemetryModel();
                model.setKey(key);
            }
            model.setMessage(message + " ( " + calcTime + " ) ");

        }

        model.setMillsecondsElapsed(calcTime);
        teleMap.put(model.getKey(),model);



    }

    /**
     * Send previously added data to the Telemetry Display
     */
    public void sendTelemetry() {

        List<TelemetryModel> models = new ArrayList<TelemetryModel>();

        Iterator entries = teleMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            models.add((TelemetryModel) thisEntry.getValue());
        }

        Collections.sort(models);
        for (TelemetryModel model : models) {

            opMode.sendTelemetryData( model.getKey(), model.getMessage());
        }

    }


    private long calcElapsedTime() {
        Date d = new Date();
        return d.getTime() - startTime;
    }



    private class TelemetryModel  implements Comparable  {
        private long millsecondsElapsed;
        private String key;
        private String message;

        public long getMillsecondsElapsed() {
            return millsecondsElapsed;
        }

        public void setMillsecondsElapsed(long millsecondsElapsed) {
            this.millsecondsElapsed = millsecondsElapsed;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public int compareTo(Object another) {
            TelemetryModel anotherModel = (TelemetryModel)another;
            //sort reverse cron / asc key
            int val = (int) (anotherModel.getMillsecondsElapsed()- getMillsecondsElapsed());
            if (val != 0 ){
                return val < 1 ? -1 : 1;
            }
            return getKey().compareTo(anotherModel.getKey());

        }
    }

    public boolean isSortByTime() {
        return sortByTime;
    }

    public void setSortByTime(boolean sortByTime) {
        this.sortByTime = sortByTime;
    }
}
