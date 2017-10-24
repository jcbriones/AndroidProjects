package com.jcbriones.gmu.project2workit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import static com.jcbriones.gmu.project2workit.DatabaseOpenHelper.TABLE_NAME;

public class AddWorkout extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_workout);

        initializeVariables();
    }

    private void initializeVariables() {
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        requestCode = 0;

        // View Variables

    }

    private void addToList(String input) {
        if (!input.equals("")) {
            // Add to Database
            ContentValues cv = new ContentValues(1);
            cv.put(DatabaseOpenHelper.WORKOUT, input);
            db.insert(TABLE_NAME, null, cv);
            mCursor.requery();
            adapter.notifyDataSetChanged();
        }
    }

}
