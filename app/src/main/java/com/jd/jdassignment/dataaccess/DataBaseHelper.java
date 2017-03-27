package com.jd.jdassignment.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jd.jdassignment.dataobject.UserDO;


/**
 * Created by ARPaul on 09-05-2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;

    static final String CREATE_USER_DB_TABLE =
            " CREATE TABLE IF NOT EXISTS " + LSCPConstants.USERS_TABLE_NAME +
                    " (" + LSCPConstants.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UserDO.USERID               + " VARCHAR  NOT NULL, " +
                    UserDO.USERNAME             + " VARCHAR  , " +
                    UserDO.EMAIL                + " VARCHAR  NOT NULL, " +
                    UserDO.FIRSTNAME            + " VARCHAR  NOT NULL, " +
                    UserDO.LASTNAME             + " VARCHAR  NOT NULL, " +
                    UserDO.PASSWORD             + " VARCHAR  , " +
                    UserDO.PHONE                + " VARCHAR   , " +
                    UserDO.DOB                  + " VARCHAR   " +
                    ");";

    DataBaseHelper(Context context){
        super(context, LSCPConstants.DATABASE_NAME, null, LSCPConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LSCPConstants.USERS_TABLE_NAME);
        onCreate(db);
    }
}
