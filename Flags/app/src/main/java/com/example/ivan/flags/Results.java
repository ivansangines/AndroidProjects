package com.example.ivan.flags;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Results extends AppCompatActivity {
    TextView title,res1,res2,res3,res4,res5,res6,res7,res8,res9,res10;
    Button start;
    Intent results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        title = (TextView)findViewById(R.id.textView6);
        res1 = (TextView)findViewById(R.id.textView7);
        res2 = (TextView)findViewById(R.id.textView8);
        res3 = (TextView)findViewById(R.id.textView9);
        res4 = (TextView)findViewById(R.id.textView10);
        res5 = (TextView)findViewById(R.id.textView11);
        res6 = (TextView)findViewById(R.id.textView12);
        res7 = (TextView)findViewById(R.id.textView13);
        res8 = (TextView)findViewById(R.id.textView14);
        res9 = (TextView)findViewById(R.id.textView15);
        res10 = (TextView)findViewById(R.id.textView16);
        start = (Button)findViewById(R.id.button6);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        results = getIntent();

        FrameLayout.LayoutParams top = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        top.setMargins((int)(width*.25),(int)(height*.1),0,0);
        title.setLayoutParams(top);
        title.setText("RESULTS");

        FrameLayout.LayoutParams r1 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r1.setMargins((int)(width*.25),(int)(height*.2),0,0);
        res1.setText("Question 1:       "+results.getIntExtra("1", 0)+ " incorrect choices.");
        res1.setLayoutParams(r1);

        FrameLayout.LayoutParams r2 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r2.setMargins((int)(width*.25),(int)(height*.25),0,0);
        res2.setText("Question 2:       "+results.getIntExtra("2", 0)+ " incorrect choices.");
        res2.setLayoutParams(r2);

        FrameLayout.LayoutParams r3 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r3.setMargins((int)(width*.25),(int)(height*.3),0,0);
        res3.setText("Question 3:       "+results.getIntExtra("3", 0)+ " incorrect choices.");
        res3.setLayoutParams(r3);

        FrameLayout.LayoutParams r4 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r4.setMargins((int)(width*.25),(int)(height*.35),0,0);
        res4.setText("Question 4:       "+results.getIntExtra("4", 0)+ " incorrect choices.");
        res4.setLayoutParams(r4);

        FrameLayout.LayoutParams r5 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r5.setMargins((int)(width*.25),(int)(height*.4),0,0);
        res5.setText("Question 5:       "+results.getIntExtra("5", 0)+ " incorrect choices.");
        res5.setLayoutParams(r5);

        FrameLayout.LayoutParams r6 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r6.setMargins((int)(width*.25),(int)(height*.45),0,0);
        res6.setText("Question 6:       "+results.getIntExtra("6", 0)+ " incorrect choices.");
        res6.setLayoutParams(r6);

        FrameLayout.LayoutParams r7 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r7.setMargins((int)(width*.25),(int)(height*.5),0,0);
        res7.setText("Question 7:       "+results.getIntExtra("7", 0)+ " incorrect choices.");
        res7.setLayoutParams(r7);

        FrameLayout.LayoutParams r8 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r8.setMargins((int)(width*.25),(int)(height*.55),0,0);
        res8.setText("Question 8:       "+results.getIntExtra("8", 0)+ " incorrect choices.");
        res8.setLayoutParams(r8);

        FrameLayout.LayoutParams r9 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r9.setMargins((int)(width*.25),(int)(height*.6),0,0);
        res9.setText("Question 9:       "+results.getIntExtra("9", 0)+ " incorrect choices.");
        res9.setLayoutParams(r9);

        FrameLayout.LayoutParams r10 = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        r10.setMargins((int)(width*.25),(int)(height*.65),0,0);
        res10.setText("Question 10:     "+results.getIntExtra("10",0)+ " incorrect choices.");
        res10.setLayoutParams(r10);

        FrameLayout.LayoutParams btn = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.05));
        btn.setMargins((int)(width*.25),(int)(height*.8),0,0);
        start.setLayoutParams(btn);
        start.setText("Go to start");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Results.this,MainActivity.class));
                finish();

            }
        });

    }
}
