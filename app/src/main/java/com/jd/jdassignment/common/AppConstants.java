package com.jd.jdassignment.common;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;

import com.arpaul.utilitieslib.CalendarUtils;
import com.arpaul.utilitieslib.LogUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Aritra on 5/17/2016.
 */
public class AppConstants {

    public static final String EXTERNAL_FOLDER                  = "/JDAssignment/";
    public static final String EXTERNAL_FILENAME                = "JDAssignment.txt";
    public static String EXTERNAL_FOLDER_PATH                   = "";

    public static final String TEXT_NULL                        = "null";

    public static final String STATUS_MODIFIED                 = "Modified";

    public static final String STATUS_NOT_MODIFIED              = "Not Modified";

    public static final String UPLOAD_STATUS                    = "UPLOAD_STATUS";
    public static final String UPLOAD_RESPONSE                  = "UPLOAD_RESPONSE";
    public static final String STATUS_SUCCESS                   = "Success";
    public static final String STATUS_FAILURE                   = "Failure";

    public static final String ACTION_CHECK_LOGIN               = "ACTION_CHECK_LOGIN";

    public static final String BUNDLE_DASHBOARD                 = "BUNDLE_DASHBOARD";

    public static final int ACTION_REQUEST_GALLERY              = 1024;
    public static final int ACTION_REQUEST_CAMERA               = 1025;


    public static final int UPLOAD_STATUS_UNUPLOAD              = 0;
    public static final int UPLOAD_STATUS_UPLOAD                = 1;
    public static final int UPLOAD_STATUS_EDITED                = 2;

    public final static String LOCAL_BC_HOME_REFRESH = "LOCAL_BC_HOME_REFRESH";

    //    public final static int EVENT_SCHEDULE_HOUR = 8;
//    public final static int EVENT_SCHEDULE_MINUTE = 30;
    public final static String EVENT_SCHEDULE_TIME = "05:00 AM";
    public final static int EVENT_SCHEDULE_PERIOD = 24 * 60 * 60 * 1000;

    public final static int ALL_EVENT_SCHEDULE_RC = 9999999;

    public final static String ONE_TIME = "ONE_TIME";

    public static int getDay(String day) {
        int selDay = Calendar.SUNDAY;

        if(day.equalsIgnoreCase("MO"))
            selDay = Calendar.MONDAY;
        else if(day.equalsIgnoreCase("TU"))
            selDay = Calendar.TUESDAY;
        else if(day.equalsIgnoreCase("WE"))
            selDay = Calendar.WEDNESDAY;
        else if(day.equalsIgnoreCase("TH"))
            selDay = Calendar.THURSDAY;
        else if(day.equalsIgnoreCase("FR"))
            selDay = Calendar.FRIDAY;
        else if(day.equalsIgnoreCase("SA"))
            selDay = Calendar.SATURDAY;
        return selDay;
    }

    public static ClipDrawable setProgressbar(int corner, int color) {
        final float[] roundedCorners = new float[] {corner, corner, corner, corner, corner, corner, corner, corner};
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners, null,null));
        pgDrawable.getPaint().setColor(color);
        ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);

        return progress;
    }

    public static void writeFile(String mValue) {

        try {
            if(TextUtils.isEmpty(EXTERNAL_FOLDER_PATH))
                EXTERNAL_FOLDER_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + EXTERNAL_FOLDER;

            String filename = EXTERNAL_FOLDER_PATH + EXTERNAL_FILENAME;
            FileWriter fw = new FileWriter(filename, true);
            String printableText = mValue + " -> " + CalendarUtils.getDateinPattern(CalendarUtils.DATE_TIME_FORMAT_T);
            LogUtils.debugLog("writeFile", printableText);
            fw.write("\n" + printableText + "\n");
            fw.close();
        } catch (IOException ioe) {
        }

    }
}
