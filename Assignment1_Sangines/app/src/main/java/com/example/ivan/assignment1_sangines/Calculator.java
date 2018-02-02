package com.example.ivan.assignment1_sangines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class Calculator extends AppCompatActivity {
    private SeekBar bar;
    private EditText total, tax;
    private TextView tip1, tip2, customTip, percent;
    private Button btn;
    private double amount, amount1, amount2, custom, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        bar = (SeekBar) findViewById(R.id.seekBar);
        total = (EditText) findViewById(R.id.editText);
        tax =   (EditText) findViewById(R.id.editText2);
        tip1 = (TextView) findViewById(R.id.textView8);
        tip2 = (TextView) findViewById(R.id.textView9);
        customTip = (TextView) findViewById(R.id.textView10);
        btn = (Button) findViewById(R.id.button);
        percent = (TextView)findViewById(R.id.textView11);
        //amount = (Double.parseDouble( total.getText().toString()) + (Double.parseDouble( tax.getText().toString())) );

        bar.setMax(39); //max is 50
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress + 11; //minimum to 11
                percent.setText(""+progress);
                custom = (double)progress/100;
                price = (Double.parseDouble( total.getText().toString()) + (Double.parseDouble( tax.getText().toString())) );
                price = price + (price*custom);
                customTip.setText(""+price);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = (Double.parseDouble( total.getText().toString()) + (Double.parseDouble( tax.getText().toString())) );
                amount1 = amount + (amount*0.05);
                tip1.setText(""+amount1);

                amount2 = amount + (amount*0.1);
                tip2.setText(""+amount2);


            }
        });






    }
}
