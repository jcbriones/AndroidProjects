package com.jcbriones.gmu.project2workit;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.jcbriones.gmu.project2workit.DatabaseOpenHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    public static final String WORKOUT_ID = "com.jcbriones.gmu.project2workit.WORKOUT_ID";

    // Variables
    private SimpleCursorAdapter adapter;
    private int currentPos;
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;
    private Cursor mCursor;
    private int requestCode;
    // View Variables
    protected AlertDialog actions;
    protected TextView inputText;
    protected ListView exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();

        exerciseList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Toast.makeText(getApplicationContext(), "Removing " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                                                        currentPos = position;
                                                        actions.show();
                                                        return true;
                                                    }
                                                }
        );

        // Alert Pop-up
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What would you like to do with this workout?");
        String[] options = {"Modify","Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
    }

    DialogInterface.OnClickListener actionListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCursor.moveToPosition(currentPos);
                    String rowId = mCursor.getString(0);  // get the id
                    switch (which) {
                        case 0: // Modify
                            modifyWorkout(rowId);
                            break;
                        case 1: // Delete
                            db.delete(dbHelper.TABLE_NAME,"_id = ?",new String[]{rowId});
                            mCursor.requery();
                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            break;
                    }
                }
            };

    private void modifyWorkout(String rowId) {
        Intent intent = new Intent(this, ModifyWorkout.class);
        intent.putExtra(WORKOUT_ID, rowId);
        startActivityForResult(intent, requestCode);
    }

    private void initializeVariables() {
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        requestCode = 0;

        // View Variables
        exerciseList = (ListView) findViewById(R.id.exerciseList);
    }
//
//    private void addToList(String input) {
//        if (!input.equals("")) {
//            // Add to Database
//            ContentValues cv = new ContentValues(1);
//            cv.put(DatabaseOpenHelper.WORKOUT, input);
//            db.insert(TABLE_NAME, null, cv);
//            mCursor.requery();
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    public int doDelete(String workout) {
//        return db.delete(dbHelper.TABLE_NAME, "workout = ?", new String[] { workout });
//    }
//
    /** View Buttons */
    public void onButtonClickAddWorkout(View view) {
        Intent intent = new Intent(this, AddWorkout.class);
        startActivityForResult(intent, requestCode);
    }

}
