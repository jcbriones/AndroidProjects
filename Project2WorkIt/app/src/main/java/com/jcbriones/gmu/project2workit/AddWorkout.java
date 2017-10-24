package com.jcbriones.gmu.project2workit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import static com.jcbriones.gmu.project2workit.DatabaseOpenHelper.TABLE_NAME;

public class AddWorkout extends AppCompatActivity {

    // Variables
    //private SimpleCursorAdapter adapter;
    //private int currentPos;
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;
    //private Cursor mCursor;
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
        setContentView(R.layout.activity_add_workout);

        initializeVariables();

        seekBarWeight.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
        seekBarReps.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
        seekBarSets.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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

    }

    private void addToList(String workout, String weight, String reps, String sets) {
        // Add to Database
        ContentValues cv = new ContentValues(1);
        cv.put(DatabaseOpenHelper.WORKOUT, workout);
        cv.put(DatabaseOpenHelper.WEIGHT, weight);
        cv.put(DatabaseOpenHelper.REPS, reps);
        cv.put(DatabaseOpenHelper.SETS, sets);
        db.insert(TABLE_NAME, null, cv);
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
    public void onButtonClickAdd(View view) {
        // Check first if the inputs are valid inputs. Otherwise, Tell user to enter a valid input
        if (!textViewWorkout.getText().toString().equals("") && isNumeric(textViewWeight.getText().toString()) && isNumeric(textViewReps.getText().toString()) && isNumeric(textViewSets.getText().toString())) {
            //addToList(textViewWorkout.getText().toString(), textViewWeight.getText().toString(), textViewReps.getText().toString(), textViewSets.getText().toString());

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.WORKOUT, textViewWorkout.getText().toString());
            intent.putExtra(MainActivity.WEIGHT, textViewWeight.getText().toString());
            intent.putExtra(MainActivity.REPS, textViewReps.getText().toString());
            intent.putExtra(MainActivity.SETS, textViewSets.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            // Throw a warning to user
            Toast.makeText(getApplicationContext(), "Please make sure you enter the name of the workout and all other inputs provided.", Toast.LENGTH_SHORT).show();
        }
    }


}