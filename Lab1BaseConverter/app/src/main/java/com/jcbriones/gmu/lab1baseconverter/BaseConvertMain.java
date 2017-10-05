package com.jcbriones.gmu.lab1baseconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.text.*;

public class BaseConvertMain extends AppCompatActivity {

    // Used for radio convert selection
    private enum ConvertType { BINARY, OCTAL, DECIMAL, HEX }
    private ConvertType option;
    private int base = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_convert_main);
        TextView inputField = (TextView)findViewById(R.id.inputField);
        inputField.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    /*
    Button Functions
     */
    public void onButtonClick_Clear(View v) {
        TextView inputField = (TextView)findViewById(R.id.inputField);
        inputField.setText("");
    }

    public void onButtonClick_Convert(View v) {
        TextView inputField = (TextView)findViewById(R.id.inputField);
        TextView textBinaryValue = (TextView)findViewById(R.id.textBinaryValue);
        TextView textOctalValue = (TextView)findViewById(R.id.textOctalValue);
        TextView textDecimalValue = (TextView)findViewById(R.id.textDecimalValue);
        TextView textHexValue = (TextView)findViewById(R.id.textHexValue);
        int inputValue = 0;
        
        if (option == null) {
            Toast.makeText(this, "Please select an option first", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Use parseInt to convert from dec,hex,octal,binary to an integer
            switch (option) {
                case BINARY:
                    inputValue = Integer.parseInt(inputField.getText().toString(), 2);
                    break;
                case OCTAL:
                    inputValue = Integer.parseInt(inputField.getText().toString(), 8);
                    break;
                case DECIMAL:
                    inputValue = Integer.parseInt(inputField.getText().toString());
                    break;
                case HEX:
                    inputValue = Integer.parseInt(inputField.getText().toString(), 16);
                    break;
            }

            // Converts the data to each output
            textBinaryValue.setText(Integer.toBinaryString(inputValue));
            textOctalValue.setText(Integer.toOctalString(inputValue));
            textDecimalValue.setText(String.valueOf(inputValue));
            textHexValue.setText(Integer.toHexString(inputValue).toUpperCase());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Illegal number (" + inputField.getText().toString() + ") for base " + base, Toast.LENGTH_SHORT).show();
        }
    }

    public void onRadioButtonClicked_Option(View v) {
        TextView inputField = (TextView)findViewById(R.id.inputField);

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.radioBinary:
                option = ConvertType.BINARY;
                base = 2;
                inputField.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case R.id.radioOctal:
                option = ConvertType.OCTAL;
                base = 8;
                inputField.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case R.id.radioDecimal:
                option = ConvertType.DECIMAL;
                base = 10;
                inputField.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case R.id.radioHex:
                option = ConvertType.HEX;
                base = 16;
                inputField.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
        }
    }

}
