package com.example.ivan.flags;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String []regions;
    boolean [] selectedRegions = new boolean[5];
    Toast msg;
    Context thisone;
    AlertDialog.Builder choices;

    TextView title, id;
    ImageView me;
    Button btn;
    boolean next;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        regions = getResources().getStringArray(R.array.regionsList);
        selectedRegions = new boolean[regions.length];
        choices = new AlertDialog.Builder(MainActivity.this);
        thisone = this;
        msg = Toast.makeText(thisone, "You must choose at least 4 regions!", Toast.LENGTH_LONG );
        msg.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
        next=false;

        title =(TextView) findViewById(R.id.textView);
        me = (ImageView) findViewById(R.id.imageView);
        id = (TextView) findViewById(R.id.textView2);
        btn = (Button) findViewById(R.id.button);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        FrameLayout.LayoutParams text1 = new FrameLayout.LayoutParams((int)(width*.5),(int)(height*.1));
        text1.setMargins((int)(width*.25),(int)(height*.1),0,0);
        title.setLayoutParams(text1);

        FrameLayout.LayoutParams image = new FrameLayout.LayoutParams((int)(width*.4),(int)(height*.4));
        image.setMargins((int)(width*.30),(int)(height*.15),0,0);
        me.setLayoutParams(image);

        FrameLayout.LayoutParams text2 = new FrameLayout.LayoutParams((int)(width*.5),(int)(height*.1));
        text2.setMargins((int)(width*.35),(int)(height*.55),0,0);
        id.setLayoutParams(text2);

        FrameLayout.LayoutParams button = new FrameLayout.LayoutParams((int)(width*.2),(int)(height*.05));
        button.setMargins((int)(width*.4),(int)(height*.6),0,0);
        btn.setLayoutParams(button);
        btn.setAlpha(0.5f);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next) {
                    Intent gotoTwo = (new Intent(MainActivity.this, Game.class));
                    gotoTwo.putExtra("regi", selectedRegions);
                    startActivity(gotoTwo);


                }else{
                    msg.show();
                }

            }
        });

        choices.setMultiChoiceItems(R.array.regionsList,null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    selectedRegions[which]=true;
                }else{
                    selectedRegions[which]=false;
                }

            }
        });

        choices.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                count=0;
                for(int i=0; i<selectedRegions.length; i++){
                   if(selectedRegions[i]) {
                       count++;
                   }
                }
                if(count<4){
                    msg.show();
                    next=false;
                }else{
                    btn.setAlpha(1f);
                    next=true;
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        choices.show();

        return super.onOptionsItemSelected(item);
    }
}
