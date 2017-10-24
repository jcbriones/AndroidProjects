package com.jcbriones.gmu.project2workit;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import static com.jcbriones.gmu.project2workit.DatabaseOpenHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    // Variables
    private SimpleCursorAdapter adapter;
    private int currentPos;
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;
    private Cursor mCursor;
    // View Variables
    protected AlertDialog actions;
    protected TextView inputText;
    protected ListView exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();

        // Alert Pop-up
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
    }

    DialogInterface.OnClickListener actionListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // Delete
                            mCursor.moveToPosition(currentPos);
                            String rowId = mCursor.getString(0);  // get the id
                            db.delete(dbHelper.TABLE_NAME,"_id = ?",new String[]{rowId});
                            mCursor.requery();
                            adapter.notifyDataSetChanged();
                            // remove item from DB
                            break;
                        default:
                            break;
                    }
                }
            };

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

    public int doDelete(String workout) {
        return db.delete(dbHelper.TABLE_NAME, "workout = ?", new String[] { workout });
    }

    private void initializeVariables() {
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();

        // View Variables
        exerciseList = (ListView) findViewById(R.id.toDoList);
    }


}
