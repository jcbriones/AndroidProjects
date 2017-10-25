package com.jcbriones.gmu.project2workit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jbrione3 on 10/22/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    final static String _ID = "_id";
    final static String TABLE_NAME = "workouts";
    final static String ICON = "icon";
    final static String WORKOUT = "workout";
    final static String WEIGHT = "weight";
    final static String REPS = "reps";
    final static String SETS = "sets";
    final static String NOTES = "notes";
    final private static String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" (" + _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + ICON + " TEXT NOT NULL, " + WORKOUT + " TEXT NOT NULL, " + WEIGHT + " TEXT NOT NULL, " + REPS + " TEXT NOT NULL, " + SETS + " TEXT NOT NULL, " + NOTES + " TEXT NOT NULL)";

    final private static String NAME = "workout_db";
    final private static Integer VERSION = 2;
    final private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    void deleteDatabase ( ) {
        context.deleteDatabase(NAME);
    }
}
