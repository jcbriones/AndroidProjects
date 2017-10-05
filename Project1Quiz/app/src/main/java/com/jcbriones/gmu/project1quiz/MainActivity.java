package com.jcbriones.gmu.project1quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int IntentRequestCode;
    public static final String TYPE_OF_QUESTION = "com.jcbriones.gmu.TYPE_OF_QUESTION";
    public static final String BIT_COUNT = "com.jcbriones.gmu.BIT_COUNT";
    public static final String SCORECARD = "com.jcbriones.gmu.SCORECARD";
    public static final String SCORE = "com.jcbriones.gmu.SCORE";
    public static final String TRIES = "com.jcbriones.gmu.TRIES";

    // View Variables
    protected Spinner question;
    protected Spinner bit_count;
    protected TextView scorecard;

    // Class Variables
    private int score = 0;
    private int tries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        question = (Spinner)findViewById(R.id.spinnerQuestion);
        bit_count = (Spinner)findViewById(R.id.spinnerBitCount);
        scorecard = (TextView)findViewById(R.id.textViewScore);

        // Setup the question and answers
        List<String> questionType = new ArrayList<>();
        questionType.add("Hex to Decimal");
        questionType.add("Decimal to Unsigned Hex");
        questionType.add("Decimal to Signed Hex");
        ArrayAdapter<String> dataQuestionTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, questionType);
        dataQuestionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question.setAdapter(dataQuestionTypeAdapter);

        // Setup the question and answers
        List<String> bitCount = new ArrayList<>();
        bitCount.add("6");
        bitCount.add("8");
        bitCount.add("10");
        ArrayAdapter<String> dataBitCountAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bitCount);
        dataBitCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bit_count.setAdapter(dataBitCountAdapter);

        if (savedInstanceState != null) {
            // Restore previous data
            score = savedInstanceState.getInt(SCORE);
            tries = savedInstanceState.getInt(TRIES);
        }
        updateScore();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save instance state
        savedInstanceState.putInt(SCORE, score);
        savedInstanceState.putInt(TRIES, tries);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IntentRequestCode && resultCode == RESULT_OK && intent != null) {
            scorecard.setText(intent.getStringExtra(SCORECARD));
            score = Integer.valueOf(intent.getStringExtra(SCORE));
            tries = Integer.valueOf(intent.getStringExtra(TRIES));
        }
    }

    /** Called when the user taps the Go button */
    public void onButtonClickGo(View view) {
        Class activityName;
        if (question.getSelectedItemPosition() == 0)
            activityName = HexToDecimalActivity.class;
        else if (question.getSelectedItemPosition() == 1 || question.getSelectedItemPosition() == 2)
            activityName = DecimalToHexActivity.class;
        else {
            // Throw a warning to user (It may not really reach this point but just to be hack free)
            Toast.makeText(getApplicationContext(), "Please make sure you have selected a valid question type.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, activityName);
        intent.putExtra(TYPE_OF_QUESTION, question.getSelectedItem().toString());
        intent.putExtra(BIT_COUNT, bit_count.getSelectedItem().toString());
        intent.putExtra(SCORE, String.valueOf(score));
        intent.putExtra(TRIES, String.valueOf(tries));
        startActivityForResult(intent, IntentRequestCode);
    }

    private void updateScore() {
        scorecard.setText(score + " / " + tries);
    }
}
