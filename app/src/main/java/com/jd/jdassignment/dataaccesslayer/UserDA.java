package com.jd.jdassignment.dataaccesslayer;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.jd.jdassignment.common.AppConstants;
import com.jd.jdassignment.dataaccess.LSCPConstants;
import com.jd.jdassignment.dataobject.UserDO;

/**
 * Created by Aritra on 27-03-2017.
 */

public class UserDA {

    public String insertUser(Context context, UserDO objUserDO) {
        Uri CONTENT_URI = LSCPConstants.CONTENT_URI_USER;
        Uri uri = null;

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDO.USERID, objUserDO.UserId);
        contentValues.put(UserDO.USERNAME, objUserDO.UserName);
        contentValues.put(UserDO.FIRSTNAME, objUserDO.FirstName);
        contentValues.put(UserDO.LASTNAME, objUserDO.LastName);
        contentValues.put(UserDO.EMAIL, objUserDO.Email);
        contentValues.put(UserDO.PASSWORD, objUserDO.Password);
        contentValues.put(UserDO.PHONE, objUserDO.Phone);
        contentValues.put(UserDO.DOB, objUserDO.Dob);

        try {
            uri = context.getContentResolver().insert(CONTENT_URI, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            uri = null;
        } finally {String isCreated
             = AppConstants.STATUS_FAILURE;
            if(uri != null)
                isCreated = AppConstants.STATUS_SUCCESS;

            return isCreated;
        }
    }
}
