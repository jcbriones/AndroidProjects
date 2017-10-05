package com.jcbriones.gmu.project1quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HexToDecimalActivity extends AppCompatActivity {

    protected EditText inputUnsigned;
    protected EditText inputSigned;
    protected TextView questionView;
    protected TextView correctAnswerUnsigned;
    protected TextView correctAnswerSigned;
    protected Button buttonCheckAnswer;
    protected Button buttonNewQuestion;
    protected LinearLayout hexToDecimal;
    protected TextView scorecard;

    private String questionType;
    private String bitCount;
    private int score;
    private int tries;
    private int generatedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex_to_decimal);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        questionType = intent.getStringExtra(MainActivity.TYPE_OF_QUESTION);
        bitCount = intent.getStringExtra(MainActivity.BIT_COUNT);
        score = Integer.valueOf(intent.getStringExtra(MainActivity.SCORE));
        tries = Integer.valueOf(intent.getStringExtra(MainActivity.TRIES));

        // Initialize View Variables
        initializeViewVariables();

        // Initialize a random number for the selected type of question
        initializeQuestion();
    }

    /** Called when the user taps the Check My Answers button */
    public void onButtonClickCheckAnswers(View view) {
        // Check first if the inputs are valid inputs. Otherwise, Tell user to enter a valid input
        try {
            correctAnswerSigned.setVisibility(View.VISIBLE);
            correctAnswerUnsigned.setVisibility(View.VISIBLE);

            int signedValue = getSignedBit(generatedValue);

            // Signed Value Checking
            if (inputSigned.getText().toString().equals(Integer.toString(signedValue))) {
                score++;
                correctAnswerSigned.setText(("Signed: Correct!"));
            }
            else
                correctAnswerSigned.setText(("Signed: " + Integer.toString(signedValue)));
            tries++;

            // Unsigned Value Checking
            if (inputUnsigned.getText().toString().equals(Integer.toString(generatedValue))) {
                score++;
                correctAnswerUnsigned.setText(("Unsigned: Correct!"));
            }
            else
                correctAnswerUnsigned.setText(("Unsigned: " + Integer.toString(generatedValue)));
            tries++;

            // Switch buttons
            buttonCheckAnswer.setVisibility(View.GONE);
            buttonNewQuestion.setVisibility(View.VISIBLE);
            updateScore();
        }
        catch (Exception e) {
            // Throw a warning to user
            Toast.makeText(getApplicationContext(), "Please make sure you enter integers in any of the inputs provided.", Toast.LENGTH_SHORT).show();
        }
    }

    /** Called when the user taps the Check My Answers button */
    public void onButtonClickNewQuestion(View view) {
        // Reinitialize question.
        initializeQuestion();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.SCORECARD, scorecard.getText().toString());
        intent.putExtra(MainActivity.SCORE, String.valueOf(score));
        intent.putExtra(MainActivity.TRIES, String.valueOf(tries));
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private int getSignedBit(int generatedValue) {
        int signedBit;
        switch(bitCount) {
            case "6":
                signedBit = (generatedValue >> 5) & 1;
                if (signedBit > 0)
                    return generatedValue - 64;
                return generatedValue;

            case "8":
                signedBit = (generatedValue >> 7) & 1;
                if (signedBit > 0)
                    return generatedValue - 256;
                return generatedValue;

            case "10":
                signedBit = (generatedValue >> 9) & 1;
                if (signedBit > 0)
                    return generatedValue - 1024;
                return generatedValue;

            default:
                return 0;
        }
    }

    private void initializeViewVariables() {
        inputUnsigned = (EditText)findViewById(R.id.inputUnsigned);
        inputSigned = (EditText)findViewById(R.id.inputSigned);
        questionView = (TextView) findViewById(R.id.textViewQuestion);
        correctAnswerUnsigned = (TextView) findViewById(R.id.correctAnswerUnsigned);
        correctAnswerSigned = (TextView) findViewById(R.id.correctAnswerSigned);
        buttonCheckAnswer = (Button) findViewById(R.id.buttonCheckAnswer);
        buttonNewQuestion = (Button) findViewById(R.id.buttonNewQuestion);
        scorecard = (TextView)findViewById(R.id.textViewScore);
        hexToDecimal = (LinearLayout)findViewById(R.id.hexToDecimal);
    }

    private void initializeQuestion() {
        // Enabled/Disable Views
        correctAnswerUnsigned.setVisibility(View.GONE);
        correctAnswerSigned.setVisibility(View.GONE);
        buttonNewQuestion.setVisibility(View.GONE);
        buttonCheckAnswer.setVisibility(View.VISIBLE);

        // Clear the text fields
        inputSigned.setText("");
        inputUnsigned.setText("");

        // Generate new random number
        generatedValue = (int)(Math.random() * Math.pow(2, Integer.valueOf(bitCount)));

        String question = "What are the signed and unsigned decimal values for the 0x" + Integer.toHexString(generatedValue).toUpperCase();
        questionView.setText(question);

        // Update Score
        updateScore();
    }

    private void updateScore() {
        scorecard.setText(score + " / " + tries);
    }
}
