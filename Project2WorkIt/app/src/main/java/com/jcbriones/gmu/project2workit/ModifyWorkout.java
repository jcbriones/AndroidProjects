package com.jcbriones.gmu.project2workit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.jcbriones.gmu.project2workit.DatabaseOpenHelper.TABLE_NAME;

public class ModifyWorkout extends AppCompatActivity {

    // Variables
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;
    private String rowId;
    // ViewVariables
    protected TextView textViewWorkout;
    protected SeekBar seekBarWeight;
    protected TextView textViewWeight;
    protected SeekBar seekBarReps;
    protected TextView textViewReps;
    protected SeekBar seekBarSets;
    protected TextView textViewSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_workout);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        rowId = intent.getStringExtra(MainActivity.WORKOUT_ID);

        initializeVariables();

        seekBarWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                textViewWeight.setText(String.valueOf(progresValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Empty
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Empty
            }
        });
        seekBarReps.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                textViewReps.setText(String.valueOf(progresValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Empty
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Empty
            }
        });
        seekBarSets.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                textViewSets.setText(String.valueOf(progresValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Empty
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Empty
            }
        });

    }

    private void initializeVariables() {
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();

        // View Variables
        textViewWorkout = (TextView) findViewById(R.id.inputWorkout);
        seekBarWeight = (SeekBar) findViewById(R.id.seekBarWeight);
        textViewWeight = (TextView) findViewById(R.id.inputWeight);
        seekBarReps = (SeekBar) findViewById(R.id.seekBarReps);
        textViewReps = (TextView) findViewById(R.id.inputReps);
        seekBarSets = (SeekBar) findViewById(R.id.seekBarSets);
        textViewSets = (TextView) findViewById(R.id.inputSets);

        // SEEK BARS
        seekBarWeight.setMax(100);
        seekBarReps.setMax(50);
        seekBarSets.setMax(10);

        // Get Database Information for the given id
        Cursor cursor = db.query(TABLE_NAME, new String[] {"_id", "workout", "weight", "reps", "sets"},
                "_id = ?", new String[]{rowId}, null, null, null);
        cursor.moveToPosition(0);
        // Set Seek Bars data
        seekBarWeight.setProgress(Integer.parseInt(cursor.getString(2)));
        seekBarReps.setProgress(Integer.parseInt(cursor.getString(3)));
        seekBarSets.setProgress(Integer.parseInt(cursor.getString(4)));

        // Set Name and Values
        textViewWorkout.setText(cursor.getString(1));
        textViewWeight.setText(cursor.getString(2));
        textViewReps.setText(cursor.getString(3));
        textViewSets.setText(cursor.getString(4));
    }

    private void updateListItem(String workout, String weight, String reps, String sets) {
        // Add to Database
        ContentValues cv = new ContentValues(1);
        cv.put(DatabaseOpenHelper.WORKOUT, workout);
        cv.put(DatabaseOpenHelper.WEIGHT, weight);
        cv.put(DatabaseOpenHelper.REPS, reps);
        cv.put(DatabaseOpenHelper.SETS, sets);
        db.update(TABLE_NAME, cv, "_id = ?",new String[]{rowId});
    }

    public static boolean isNumeric(String str)
    {
        try {
            Integer.parseInt(str);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    /** Buttons */
    public void onButtonClickUpdate(View view) {
        // Check first if the inputs are valid inputs. Otherwise, Tell user to enter a valid input
        if (!textViewWorkout.getText().toString().equals("") && isNumeric(textViewWeight.getText().toString()) && isNumeric(textViewReps.getText().toString()) && isNumeric(textViewSets.getText().toString())) {
            updateListItem(textViewWorkout.getText().toString(), textViewWeight.getText().toString(), textViewReps.getText().toString(), textViewSets.getText().toString());

            Intent intent = new Intent(this, MainActivity.class);
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            // Throw a warning to user
            Toast.makeText(getApplicationContext(), "Please make sure you enter the name of the workout and all other inputs provided.", Toast.LENGTH_SHORT).show();
        }
    }


}
