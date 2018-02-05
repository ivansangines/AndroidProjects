package com.example.ivan.calculator_2;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView result, numb;
    private int case_num;
    private String variable;
    private Button btn_dec, btn_eq, btn_plus, btn_clear, btn_div, btn_mul, btn_sub, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private double number1, number2, total=0.0;
    private boolean s,su,d,m, point= false; //variables for sum, subs, div and mult


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_calc);


        //initialize variables
        result = (TextView) findViewById(R.id.textView);
        numb = (TextView) findViewById(R.id.textView2);
        btn_eq = (Button) findViewById(R.id.button_eq);
        btn_plus = (Button) findViewById(R.id.button_add);
        btn_clear = (Button) findViewById(R.id.button_clear);
        btn_div = (Button) findViewById(R.id.button_div);
        btn_mul = (Button) findViewById(R.id.button_mul);
        btn_sub = (Button) findViewById(R.id.button_sub);
        btn0 = (Button) findViewById(R.id.button0);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn_dec = (Button) findViewById(R.id.button_dec);



        //set listeners

        btn_eq.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_mul.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn_dec.setOnClickListener(this);

        /*btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("0");
                number1 = 0;
                number2 = 0;
                variable = null;
            }
        });*/




    }

    @Override
    public void onClick(View button) {
        case_num = button.getId();
        variable = result.getText().toString(); //cannot be assigned out of this method
        switch (case_num){
            case R.id.button0:
                result.setText(variable+"0");

                break;
            case R.id.button1:
                result.setText(variable+"1");

                break;
            case R.id.button2:
                result.setText(variable+"2");

                break;
            case R.id.button3:
                result.setText(variable+"3");

                break;
            case R.id.button4:
                result.setText(variable+"4");

                break;
            case R.id.button5:
                result.setText(variable+"5");
                break;
            case R.id.button6:
                result.setText(variable+"6");

                break;
            case R.id.button7:
                result.setText(variable+"7");

                break;
            case R.id.button8:
                result.setText(variable+"8");

                break;
            case R.id.button9:
                result.setText(variable+"9");

                break;
            case R.id.button_add:
                s=true;



                number1 = Double.parseDouble(variable);
                point=false;
                result.setText(""); //Clear the screen

                break;
            case R.id.button_sub:
                su=true;
                number1 = Double.parseDouble(variable);
                point=false;
                result.setText("");

                break;
            case R.id.button_mul:
                m=true;
                number1 = Double.parseDouble(variable);
                point=false;
                result.setText("");

                break;
            case R.id.button_div:
                d=true;
                number1 = Double.parseDouble(variable);
                point=false;
                result.setText("");

                break;
            case R.id.button_dec:
                if(point==false) {
                    result.setText(variable + ".");
                    point=true;
                }else{
                    return;
                }

                break;
            case R.id.button_clear:
                result.setText("");
                number1 = number2 = 0.0;

                break;
            case R.id.button_eq:
                number2 = Double.parseDouble(variable);
                if (s==true){
                    total = number1 + number2;
                    result.setText(""+total);
                    s=false;
                }else if (su == true){
                    total = number1 - number2;
                    result.setText(""+total);
                    su=false;
                }else if (d == true){
                    total = number1 / number2;
                    result.setText(""+total);
                    d=false;
                }else if (m == true){
                    total = number1 * number2;
                    result.setText(""+total);
                    m=false;
                }
                point=false;
                break;

        }


    }





}
