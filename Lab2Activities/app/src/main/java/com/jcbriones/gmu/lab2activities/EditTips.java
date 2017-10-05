package com.jcbriones.gmu.lab2activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class EditTips extends AppCompatActivity {
    // Variables
    private SeekBar seekBarExcellent;
    private TextView textViewExcellent;
    private SeekBar seekBarAverage;
    private TextView textViewAverage;
    private SeekBar seekBarLacking;
    private TextView textViewLacking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tips);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String excellent_tip = intent.getStringExtra(MainActivity.EXCELLENT_TIP);
        String average_tip = intent.getStringExtra(MainActivity.AVERAGE_TIP);
        String bad_tip = intent.getStringExtra(MainActivity.BAD_TIP);

        // Initialize the variables
        initializeVariables();

        // Capture the layout's TextView and set the string as its text
        textViewExcellent.setText(excellent_tip);
        textViewAverage.setText(average_tip);
        textViewLacking.setText(bad_tip);
        seekBarExcellent.setProgress(Integer.parseInt(excellent_tip));
        seekBarAverage.setProgress(Integer.parseInt(average_tip));
        seekBarLacking.setProgress(Integer.parseInt(bad_tip));

        // SEEK BARS
        seekBarExcellent.setMax(100);
        seekBarAverage.setMax(100);
        seekBarLacking.setMax(100);

        seekBarExcellent.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                textViewExcellent.setText(String.valueOf(progresValue));
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
        seekBarAverage.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                textViewAverage.setText(String.valueOf(progresValue));
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
        seekBarLacking.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                textViewLacking.setText(String.valueOf(progresValue));
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

    /** Called when the user taps the Update Tips button */
    public void onButtonClickUpdate(View view) {
        // Check first if the inputs are valid inputs. Otherwise, Tell user to enter a valid input
        if (isNumeric(textViewExcellent.getText().toString()) && isNumeric(textViewAverage.getText().toString()) && isNumeric(textViewLacking.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.EXCELLENT_TIP, textViewExcellent.getText().toString());
            intent.putExtra(MainActivity.AVERAGE_TIP, textViewAverage.getText().toString());
            intent.putExtra(MainActivity.BAD_TIP, textViewLacking.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            // Throw a warning to user
            Toast.makeText(getApplicationContext(), "Please make sure you enter integers in any of the inputs provided.", Toast.LENGTH_SHORT).show();
        }
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

    private void initializeVariables() {
        seekBarExcellent = (SeekBar) findViewById(R.id.seekBarExcellent);
        textViewExcellent = (TextView) findViewById(R.id.inputExcellent);
        seekBarAverage = (SeekBar) findViewById(R.id.seekBarAverage);
        textViewAverage = (TextView) findViewById(R.id.inputAverage);
        seekBarLacking = (SeekBar) findViewById(R.id.seekBarLacking);
        textViewLacking = (TextView) findViewById(R.id.inputLacking);
    }

}
