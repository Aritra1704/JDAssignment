package com.jd.jdassignment.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Aritra on 5/17/2016.
 */
public class AppPreference {

    public static final String USERID                  =	"USERID";
    public static final String USERNAME                =	"USERNAME";

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
