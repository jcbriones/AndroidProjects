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
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.jcbriones.gmu.project2workit.DatabaseOpenHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    public static final String WORKOUT_ID = "com.jcbriones.gmu.project2workit.WORKOUT_ID";
    public static ArrayList<Integer> iconList = new ArrayList<>();
    // Variables
    private SimpleCursorAdapter adapter;
    private int currentPos;
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;
    private Cursor mCursor;
    private int RequestCode;
    private String[] columns = new String[] {DatabaseOpenHelper._ID, DatabaseOpenHelper.ICON, DatabaseOpenHelper.WORKOUT, DatabaseOpenHelper.WEIGHT, DatabaseOpenHelper.REPS, DatabaseOpenHelper.SETS, DatabaseOpenHelper.NOTES};
    // View Variables
    protected AlertDialog actions;
    protected ListView exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();

        exerciseList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                        currentPos = position;
                                                        actions.show();
                                                        return true;
                                                    }
                                                }
        );

        mCursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        adapter = new SimpleCursorAdapter(this,
                R.layout.list_item_full,
                mCursor,
                new String[] { DatabaseOpenHelper.ICON, DatabaseOpenHelper.WORKOUT, DatabaseOpenHelper.WEIGHT, DatabaseOpenHelper.REPS, DatabaseOpenHelper.SETS },
                new int[] { R.id.lisItemIcon, R.id.firstLine, R.id.weight, R.id.reps, R.id.sets });

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            /** Binds the Cursor column defined by the specified index to the specified view */
            public boolean setViewValue(View view, Cursor cursor, int columnIndex){
                if(view.getId() == R.id.lisItemIcon){
                    ((ImageView)view).setImageResource(iconList.get(cursor.getInt(1)));
                    return true;
                }
                return false;
            }
        });
        exerciseList.setAdapter(adapter);

        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
                String rowId = mCursor.getString(0);
                modifyWorkout(rowId);
            }
        });

        // Alert Pop-up
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What would you like to do with this workout?");
        String[] options = {"Modify","Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        addToList("0", "Yoga", "2", "1", "1", "Notes for workout");
                        addToList("1", "Biceps", "25", "12", "3", "Notes for workout");
                        addToList("1", "Triceps", "20", "12", "3", "Notes for workout");
                        addToList("1", "Shoulders", "50", "12", "3", "Notes for workout");
                        addToList("1", "Back", "150", "12", "3", "Notes for workout");
                        addToList("1", "Legs", "400", "20", "3", "Notes for workout");
                        addToList("2", "Swimming", "400", "1", "1", "Notes for workout");
                        addToList("2", "Running", "5000", "1", "1", "Notes for workout");
                        addToList("2", "Biking", "15000", "1", "1", "Notes for workout");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        // Ask user to add sample workouts if the database is empty
        if (mCursor.getCount() == 0) {
            AlertDialog.Builder generateWorkouts = new AlertDialog.Builder(this);
            generateWorkouts.setMessage("Would you like to generate workouts?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RequestCode && resultCode == RESULT_OK && intent != null) {
            mCursor.requery();
            adapter.notifyDataSetChanged();
        }
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
                            // Make sure to allow writing before inserting
                            if(db == null)
                                db = dbHelper.getWritableDatabase();
                            db.delete(dbHelper.TABLE_NAME,"_id = ?",new String[]{rowId});
                            mCursor.requery();
                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            break;
                    }
                }
            };

    private void addToList(String icon, String workout, String weight, String reps, String sets, String notes) {
        // Make sure to allow writing before inserting
        if(db == null)
            db = dbHelper.getWritableDatabase();
        // Add to Database
        ContentValues cv = new ContentValues(1);
        cv.put(DatabaseOpenHelper.ICON, icon);
        cv.put(DatabaseOpenHelper.WORKOUT, workout);
        cv.put(DatabaseOpenHelper.WEIGHT, weight);
        cv.put(DatabaseOpenHelper.REPS, reps);
        cv.put(DatabaseOpenHelper.SETS, sets);
        cv.put(DatabaseOpenHelper.NOTES, notes);
        db.insert(TABLE_NAME, null, cv);
        mCursor.requery();
        adapter.notifyDataSetChanged();
    }

    private void modifyWorkout(String rowId) {
        Intent intent = new Intent(this, ModifyWorkout.class);
        intent.putExtra(WORKOUT_ID, rowId);
        startActivityForResult(intent, RequestCode);
    }

    private void initializeVariables() {
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        //dbHelper.deleteDatabase();
        RequestCode = 0;

        // View Variables
        exerciseList = (ListView) findViewById(R.id.exerciseList);

        // Add Icon List
        iconList.add(R.drawable.icon0);
        iconList.add(R.drawable.icon1);
        iconList.add(R.drawable.icon2);
    }

    /** View Buttons */
    public void onButtonClickAddWorkout(View view) {
        Intent intent = new Intent(this, AddWorkout.class);
        startActivityForResult(intent, RequestCode);
    }

}
