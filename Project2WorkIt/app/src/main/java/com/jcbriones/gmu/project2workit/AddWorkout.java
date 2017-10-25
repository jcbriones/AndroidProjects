package com.jcbriones.gmu.project2workit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.jcbriones.gmu.project2workit.DatabaseOpenHelper.TABLE_NAME;

public class AddWorkout extends AppCompatActivity {

    // Variables
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;
    // ViewVariables
    protected Spinner spinnerIcon;
    protected TextView textViewWorkout;
    protected SeekBar seekBarWeight;
    protected TextView textViewWeight;
    protected SeekBar seekBarReps;
    protected TextView textViewReps;
    protected SeekBar seekBarSets;
    protected TextView textViewSets;
    protected TextView textViewNotes;

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
        try {
            dbHelper = new DatabaseOpenHelper(this);
            db = dbHelper.getWritableDatabase();

            // View Variables
            spinnerIcon = (Spinner) findViewById(R.id.spinnerIcon);
            textViewWorkout = (TextView) findViewById(R.id.inputWorkout);
            seekBarWeight = (SeekBar) findViewById(R.id.seekBarWeight);
            textViewWeight = (TextView) findViewById(R.id.inputWeight);
            seekBarReps = (SeekBar) findViewById(R.id.seekBarReps);
            textViewReps = (TextView) findViewById(R.id.inputReps);
            seekBarSets = (SeekBar) findViewById(R.id.seekBarSets);
            textViewSets = (TextView) findViewById(R.id.inputSets);
            textViewNotes = (TextView) findViewById(R.id.textViewNotes);

            // SEEK BARS
            seekBarWeight.setMax(100);
            seekBarReps.setMax(50);
            seekBarSets.setMax(10);

            // Setup the question and answers
            List<String> iconList = new ArrayList<>();
            iconList.add("Energy");
            iconList.add("Strength");
            iconList.add("Endurance");
            ArrayAdapter<String> iconListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, iconList);
            iconListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerIcon.setAdapter(iconListAdapter);
        }
        catch (Exception e) {}
    }

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
            addToList("".valueOf(spinnerIcon.getSelectedItemId()), textViewWorkout.getText().toString(), textViewWeight.getText().toString(), textViewReps.getText().toString(), textViewSets.getText().toString(), textViewNotes.getText().toString());

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
