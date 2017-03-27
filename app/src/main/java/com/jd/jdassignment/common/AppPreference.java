package com.jd.jdassignment.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Aritra on 5/17/2016.
 */
public class AppPreference {

    public static final String USERID                  =	"USERID";
    public static final String PROFILEID               =	"PROFILEID";
    public static final String PROFILEIMAGE            =	"PROFILEIMAGE";
    public static final String USERNAME                =	"USERNAME";
    public static final String PASSWORD 			   =	"PASSWORD";
    public static final String USERTYPE 			   =	"USERTYPE";
    public static final String GCM_TOKEN               =	"GCM_TOKEN";
    public static final String IS_RATED 			   =	"IS_RATED";
    public static final String USER_TOKEN 			   =	"USER_TOKEN";

    public static final String ALL_DATA_FETCH 		   =	"ALL_DATA_FETCH";

    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    public static final String EMAILID                 =	"EMAILID";


    public AppPreference(Context context)
    {
        preferences		=	PreferenceManager.getDefaultSharedPreferences(context);
//        preferences		=	context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE);
        edit			=	preferences.edit();
    }

    public void saveStringInPreference(String strKey, String strValue)
    {
        edit.putString(strKey, strValue);
        commitPreference();
    }

    public void removeFromPreference(String strKey)
    {
        edit.remove(strKey);
    }

    public void commitPreference()
    {
        edit.commit();
    }

    public String getStringFromPreference(String strKey, String defaultValue )
    {
        return preferences.getString(strKey, defaultValue);
    }


}
