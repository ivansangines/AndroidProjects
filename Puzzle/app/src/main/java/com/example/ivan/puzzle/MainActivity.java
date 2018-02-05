package com.example.ivan.puzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView img1;
    private TextView tittle, info;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tittle = (TextView) findViewById(R.id.textView);
        info = (TextView) findViewById(R.id.textView2);
        img1 = (ImageView) findViewById(R.id.imageView);
        btn = (Button) findViewById(R.id.button);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        FrameLayout.LayoutParams text1 = new FrameLayout.LayoutParams((int)(width*.5),(int)(height*.1));
        text1.setMargins((int)(width*.25),(int)(height*.1),0,0);
        tittle.setLayoutParams(text1);
        tittle.setText("HOMEWORK 4: PUZZLE");

        FrameLayout.LayoutParams text2 = new FrameLayout.LayoutParams((int)(width*.5),(int)(height*.1));
        text2.setMargins((int)(width*.25),(int)(height*.55),0,0);
        info.setLayoutParams(text2);
        info.setText("Ivan Sangines\n id:968606");

        FrameLayout.LayoutParams image = new FrameLayout.LayoutParams((int)(width*.4),(int)(height*.4));
        image.setMargins((int)(width*.30),(int)(height*.15),0,0);
        img1.setLayoutParams(image);

        FrameLayout.LayoutParams button = new FrameLayout.LayoutParams((int)(width*.25),(int)(height*.1));
        button.setMargins((int)(width*.4),(int)(height*.65),0,0);
        btn.setLayoutParams(button);
        btn.setText("Start Game");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Game.class));

            }
        });



    }
}
