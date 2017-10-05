package com.jcbriones.gmu.project1quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DecimalToHexActivity extends AppCompatActivity {

    protected Spinner hexSpinner0;
    protected Spinner hexSpinner1;
    protected Spinner hexSpinner2;
    protected RadioGroup radioValueSize;
    protected TextView questionView;
    protected TextView correctAnswerDecimal;
    protected Button buttonCheckAnswer;
    protected Button buttonNewQuestion;
    protected LinearLayout decimalToHex;
    protected TextView scorecard;

    private String questionType;
    private String bitCount;
    private int score;
    private int tries;
    private int generatedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decimal_to_hex);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        questionType = intent.getStringExtra(MainActivity.TYPE_OF_QUESTION);
        bitCount = intent.getStringExtra(MainActivity.BIT_COUNT);
        score = Integer.valueOf(intent.getStringExtra(MainActivity.SCORE));
        tries = Integer.valueOf(intent.getStringExtra(MainActivity.TRIES));

        // Initialize View Variables
        initializeViewVariables();

        // Setup the Hex Input Values
        initializeHexInput();

        // Initialize a random number for the selected type of question
        initializeQuestion();
    }

    /** Called when the user taps the Check My Answers button */
    public void onButtonClickCheckAnswers(View view) {
        // Check first if the inputs are valid inputs. Otherwise, Tell user to enter a valid input
        try {
            RadioButton tooSmall = (RadioButton) findViewById(R.id.radioTooSmall);
            RadioButton tooBig = (RadioButton) findViewById(R.id.radioTooBig);
            RadioButton answer = (RadioButton) findViewById(R.id.radioAnswer);

            String hexVal = hexSpinner2.getSelectedItem().toString() + hexSpinner1.getSelectedItem().toString() + hexSpinner0.getSelectedItem().toString();
            if (Integer.parseInt(hexVal, 16) == generatedValue) {
                score++;
                correctAnswerDecimal.setText(("Unsigned: Correct!"));
            }
            else
                correctAnswerDecimal.setText(("Unsigned: 0x" + Integer.toHexString(generatedValue).toUpperCase()));
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

    private void initializeViewVariables() {
        hexSpinner0 = (Spinner)findViewById(R.id.hex0);
        hexSpinner1 = (Spinner)findViewById(R.id.hex1);
        hexSpinner2 = (Spinner)findViewById(R.id.hex2);
        radioValueSize = (RadioGroup)findViewById(R.id.radioValueSize);
        questionView = (TextView) findViewById(R.id.textViewQuestion);
        correctAnswerDecimal = (TextView) findViewById(R.id.correctAnswerDecimal);
        buttonCheckAnswer = (Button) findViewById(R.id.buttonCheckAnswer);
        buttonNewQuestion = (Button) findViewById(R.id.buttonNewQuestion);
        scorecard = (TextView)findViewById(R.id.textViewScore);
        decimalToHex = (LinearLayout)findViewById(R.id.decimalToHex);
    }

    private void initializeHexInput() {
        List<String> hexVals = new ArrayList<>();
        hexVals.add("0");
        hexVals.add("1");
        hexVals.add("2");
        hexVals.add("3");
        hexVals.add("4");
        hexVals.add("5");
        hexVals.add("6");
        hexVals.add("7");
        hexVals.add("8");
        hexVals.add("9");
        hexVals.add("A");
        hexVals.add("B");
        hexVals.add("C");
        hexVals.add("D");
        hexVals.add("E");
        hexVals.add("F");
        ArrayAdapter<String> dataHex0Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hexVals);
        dataHex0Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hexSpinner0.setAdapter(dataHex0Adapter);

        ArrayAdapter<String> dataHex1Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hexVals);
        dataHex1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hexSpinner1.setAdapter(dataHex1Adapter);

        ArrayAdapter<String> dataHex2Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hexVals);
        dataHex2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (Integer.valueOf(bitCount) <= 8) {
            hexSpinner2.setEnabled(false);
            hexSpinner2.setClickable(false);
        }
        hexSpinner2.setAdapter(dataHex2Adapter);
    }
    private void initializeQuestion() {
        // Enabled/Disable Views
        correctAnswerDecimal.setVisibility(View.GONE);
        buttonNewQuestion.setVisibility(View.GONE);
        buttonCheckAnswer.setVisibility(View.VISIBLE);

        // Reset the selection back to zeros
        hexSpinner0.setSelection(0);
        hexSpinner1.setSelection(0);
        hexSpinner2.setSelection(0);

        // Generate new number
        generatedValue = (int)(Math.random() * Math.pow(2, Integer.valueOf(bitCount) + 1));

        String question;
        switch (questionType) {
            case "Decimal to Unsigned Hex":
                question = "In unsigned, what is the " + bitCount + "-bit hex value for " + Integer.toString(generatedValue);
                questionView.setText(question);
                break;

            case "Decimal to Signed Hex":
                if (generatedValue > 31 && bitCount.equals("6"))
                    question = "In two's complement, what is the " + bitCount + "-bit hex value for " + Integer.toString(generatedValue - 64);
                else if (generatedValue > 127 && bitCount.equals("8"))
                    question = "In two's complement, what is the " + bitCount + "-bit hex value for " + Integer.toString(generatedValue - 256);
                else if (generatedValue > 511 && bitCount.equals("10"))
                    question = "In two's complement, what is the " + bitCount + "-bit hex value for " + Integer.toString(generatedValue - 1024);
                else
                    question = "In two's complement, what is the " + bitCount + "-bit hex value for " + Integer.toString(generatedValue);
                questionView.setText(question);
                break;

            default:
                question = "Error getting question type.";
                questionView.setText(question);
                decimalToHex.setVisibility(View.GONE);
                break;
        }

        // Update Score
        updateScore();
    }

    private void updateScore() {
        scorecard.setText(score + " / " + tries);
    }
}
