package com.jd.jdassignment.common;

import android.app.Application;

/**
 * Created by Aritra on 27-07-2016.
 */
public class ApplicationInstance extends Application {

    public static final int LOADER_CHECK_LOGIN          = 1;

    public static final int LOADER_FETCH_ALL_DATA       = 101;
    public static final int LOADER_DELETE_ALL_DATA      = 102;
    public static final int LOADER_SYNC_ALL_DATA        = 103;

    public static final int SETTINGS_RESPONSE_CODE  = 1011;

    public static final String LOCK_APP_DB              = "LOCK_APP_DB";
    public static final String LOCK_CLICK               = "LOCK_CLICK";
}
