package com.jcbriones.gmu.project2workit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ModifyWorkout extends AppCompatActivity {

    // Variables
    private SimpleCursorAdapter adapter;
    private int currentPos;
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;
    private Cursor mCursor;
    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_workout);

        initializeVariables();
    }

    private void initializeVariables() {
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        requestCode = 0;

        // View Variables

    }

    private void update(String rowId) {
        ContentValues cv = new ContentValues(1);
        db.update(dbHelper.TABLE_NAME, cv,"_id = ?",new String[]{rowId});
        mCursor.requery();
        adapter.notifyDataSetChanged();
    }

}
