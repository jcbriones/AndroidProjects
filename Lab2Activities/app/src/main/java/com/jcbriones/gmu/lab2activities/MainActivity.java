package com.jcbriones.gmu.lab2activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Default values
    int excellent_tip = 20;
    int average_tip = 18;
    int bad_tip = 14;

    // Static Variables
    public static int EditTipsRequestCode;
    public static final String EXCELLENT_TIP = "com.jcbriones.gmu.EXCELLENT_TIP";
    public static final String AVERAGE_TIP = "com.jcbriones.gmu.AVERAGE_TIP";
    public static final String BAD_TIP = "com.jcbriones.gmu.BAD_TIP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            // Restore previous data
            excellent_tip = Integer.valueOf(savedInstanceState.getString(EXCELLENT_TIP));
            average_tip = Integer.valueOf(savedInstanceState.getString(AVERAGE_TIP));
            bad_tip = Integer.valueOf(savedInstanceState.getString(BAD_TIP));
        }
        updateTipsView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == EditTipsRequestCode && resultCode == RESULT_OK && intent != null) {
            excellent_tip = Integer.valueOf(intent.getStringExtra(EXCELLENT_TIP));
            average_tip = Integer.valueOf(intent.getStringExtra(AVERAGE_TIP));
            bad_tip = Integer.valueOf(intent.getStringExtra(BAD_TIP));

            updateTipsView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save instance state
        savedInstanceState.putString(EXCELLENT_TIP, String.valueOf(excellent_tip));
        savedInstanceState.putString(AVERAGE_TIP, String.valueOf(average_tip));
        savedInstanceState.putString(BAD_TIP, String.valueOf(bad_tip));

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        float bill;
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.excellent_button:
                if (checked) {
                    EditText b = (EditText)findViewById(R.id.bill);
                    if (b.getText().toString().equals(""))
                        bill = 0;
                    else bill = Float.parseFloat(b.getText().toString());
                    compute_tip(bill, excellent_tip);
                }
                break;
            case R.id.average_button:
                if (checked) {
                    EditText b = (EditText)findViewById(R.id.bill);
                    if (b.getText().toString().equals(""))
                        bill = 0;
                    else bill = Float.parseFloat(b.getText().toString());
                    compute_tip(bill, average_tip);
                }
                break;
            case R.id.lacking_button:
                if (checked) {
                    EditText b = (EditText)findViewById(R.id.bill);
                    if (b.getText().toString().equals(""))
                        bill = 0;
                    else bill = Float.parseFloat(b.getText().toString());
                    compute_tip(bill, bad_tip);
                }

                break;
        }
    }
    public static String roundToTwoDigit(float paramFloat) {
        return String.format("%.2f%n", paramFloat);
    }
    void compute_tip(float bill, int percent) {
        float pct= (float)percent/100;
        float tip = bill * pct;
        float total = bill + tip;
        TextView t = (TextView)findViewById(R.id.computed_tip);
        String s = roundToTwoDigit(tip);
        t.setText(s);
        t = (TextView)findViewById(R.id.bill_total);
        s = roundToTwoDigit(total);
        t.setText(s);

    }

    /** Called when the user taps the Update Tips button */
    public void onButtonClickUpdateTips(View view) {
        Intent intent = new Intent(this, EditTips.class);
        intent.putExtra(EXCELLENT_TIP, String.valueOf(excellent_tip));
        intent.putExtra(AVERAGE_TIP, String.valueOf(average_tip));
        intent.putExtra(BAD_TIP, String.valueOf(bad_tip));
        startActivityForResult(intent, EditTipsRequestCode);
    }

    public void onButtonClickImplicitCall(View view) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setType("text/plain");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }

    private void updateTipsView() {
        TextView e_tip = (TextView)findViewById(R.id.excellent_tip);
        TextView a_tip = (TextView)findViewById(R.id.average_tip);
        TextView l_tip = (TextView)findViewById(R.id.lacking_tip);
        e_tip.setText(String.valueOf(excellent_tip));
        a_tip.setText(String.valueOf(average_tip));
        l_tip.setText(String.valueOf(bad_tip));
    }
}
