package com.example.ivan.flags;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game extends AppCompatActivity {
    TextView counter, result, choice;
    ImageView flag1, flag2, flag3, flag4, bigFlag;
    Button btn1, btn2, btn3, btn4;
    boolean [] selections;
    Intent it;
    ArrayList <String> regPlayed = new ArrayList<>();
    ArrayList <Button> butons = new ArrayList<>();
    String answer;
    Animation rotate,shakeit, move;
    int tries=0;
    int count=1;
    //Animation.AnimationListener rotationListener;
    Handler delay = new Handler();
    Intent toResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        shakeit = AnimationUtils.loadAnimation(this,R.anim.shake);
        move = AnimationUtils.loadAnimation(this,R.anim.translation);
        toResult = new Intent(Game.this,Results.class);

        counter = (TextView) findViewById(R.id.textView3);
        result = (TextView) findViewById(R.id.textView4);
        choice = (TextView) findViewById(R.id.textView5);
        flag1 = (ImageView) findViewById(R.id.imageView2);
        flag2 = (ImageView) findViewById(R.id.imageView3);
        flag3 = (ImageView) findViewById(R.id.imageView4);
        flag4 = (ImageView) findViewById(R.id.imageView5);
        bigFlag = (ImageView) findViewById(R.id.imageView6);
        btn1 = (Button) findViewById(R.id.button2);
        butons.add(btn1);
        btn2 = (Button) findViewById(R.id.button3);
        butons.add(btn2);
        btn3 = (Button) findViewById(R.id.button4);
        butons.add(btn3);
        btn4 = (Button) findViewById(R.id.button5);
        butons.add(btn4);

        selected();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        FrameLayout.LayoutParams top = new FrameLayout.LayoutParams((int)(width*.3),(int)(height*.1));
        top.setMargins((int)(width*.1),(int)(height*.1),0,0);
        counter.setLayoutParams(top);
        counter.setText("Question "+count+" out of 10");

        FrameLayout.LayoutParams answ = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.1));
        answ.setMargins((int)(width*.25),(int)(height*.2),0,0);
        result.setLayoutParams(answ);
        result.setVisibility(View.INVISIBLE);

        FrameLayout.LayoutParams flg1 = new FrameLayout.LayoutParams((int)(width*.30),(int)(height*.15));
        flg1.setMargins((int)(width*.10),(int)(height*.25),0,0);
        flag1.setLayoutParams(flg1);
        flag1.setVisibility(View.INVISIBLE);

        FrameLayout.LayoutParams flg2 = new FrameLayout.LayoutParams((int)(width*.30),(int)(height*.15));
        flg2.setMargins((int)(width*.60),(int)(height*.25),0,0);
        flag2.setLayoutParams(flg2);
        flag2.setVisibility(View.INVISIBLE);

        FrameLayout.LayoutParams flg3 = new FrameLayout.LayoutParams((int)(width*.30),(int)(height*.15));
        flg3.setMargins((int)(width*.10),(int)(height*.45),0,0);
        flag3.setLayoutParams(flg3);
        flag3.setVisibility(View.INVISIBLE);

        FrameLayout.LayoutParams flg4 = new FrameLayout.LayoutParams((int)(width*.30),(int)(height*.15));
        flg4.setMargins((int)(width*.60),(int)(height*.45),0,0);
        flag4.setLayoutParams(flg4);
        flag4.setVisibility(View.INVISIBLE);

        FrameLayout.LayoutParams big = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.40));
        big.setMargins((int)(width*.25),(int)(height*.30),0,0);
        bigFlag.setLayoutParams(big);
        bigFlag.setVisibility(View.INVISIBLE);

        FrameLayout.LayoutParams text = new FrameLayout.LayoutParams((int)(width*.50),(int)(height*.10));
        text.setMargins((int)(width*.25),(int)(height*.65),0,0);
        choice.setLayoutParams(text);


        FrameLayout.LayoutParams b1 = new FrameLayout.LayoutParams((int)(width*.30),(int)(height*.05));
        b1.setMargins((int)(width*.10),(int)(height*.70),0,0);
        btn1.setLayoutParams(b1);
        btn1.setOnClickListener(myClick);


        FrameLayout.LayoutParams b2 = new FrameLayout.LayoutParams((int)(width*.30),(int)(height*.05));
        b2.setMargins((int)(width*.60),(int)(height*.70),0,0);
        btn2.setLayoutParams(b2);
        btn2.setOnClickListener(myClick);

        FrameLayout.LayoutParams b3 = new FrameLayout.LayoutParams((int)(width*.30),(int)(height*.05));
        b3.setMargins((int)(width*.10),(int)(height*.75),0,0);
        btn3.setLayoutParams(b3);
        btn3.setOnClickListener(myClick);

        FrameLayout.LayoutParams b4 = new FrameLayout.LayoutParams((int)(width*.30),(int)(height*.05));
        b4.setMargins((int)(width*.60),(int)(height*.75),0,0);
        btn4.setLayoutParams(b4);
        btn4.setOnClickListener(myClick);


        fourFlags();


    }

    View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button boton = (Button) v;
            int index = butons.indexOf(boton);

            if (boton.getText().equals(answer)) {
                result.setVisibility(View.VISIBLE);
                result.setText("Correct!!!");
                result.setTextColor(Color.GREEN);

                if (count%2!=0){
                    boton.startAnimation(rotate);
                }else{
                    bigFlag.startAnimation(rotate);
                }


                for(int i =0; i<butons.size(); i++){
                    butons.get(i).setClickable(false);
                    if(index==i){
                        continue;
                    }else{
                        butons.get(i).setAlpha(0.5f);
                    }
                }
                toResult.putExtra(""+count, tries);
                tries = 0;

                ++count;


                delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (count==11){

                            startActivity(toResult);
                        }else {

                            if (count % 2 != 0) {
                                bigFlag.setVisibility(View.INVISIBLE);
                                fourFlags();

                            } else {
                                flag1.setVisibility(View.INVISIBLE);
                                flag2.setVisibility(View.INVISIBLE);
                                flag3.setVisibility(View.INVISIBLE);
                                flag4.setVisibility(View.INVISIBLE);
                                oneFlag();
                            }


                            for (int i = 0; i < butons.size(); i++) {
                                butons.get(i).setClickable(true);
                                butons.get(i).setAlpha(1f);
                            }
                            result.setVisibility(View.INVISIBLE);
                            counter.setText("Question " + count + " out of 10");
                        }


                    }
                },2000);//}

            }else{
                tries++;
                result.setVisibility(View.VISIBLE);
                result.setText("Incorrect!!!");
                result.setTextColor(Color.RED);
                if (count%2!=0) {
                    boton.startAnimation(shakeit);
                }else{
                    bigFlag.startAnimation(move);
                }
                for(int i =0; i<butons.size(); i++){
                    butons.get(i).setClickable(false);
                    if(index==i){
                        continue;
                    }else{
                        butons.get(i).setAlpha(0.5f);
                    }
                }
                delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(int i =0; i<butons.size(); i++){
                            butons.get(i).setClickable(true);
                            butons.get(i).setAlpha(1f);
                        }
                        result.setVisibility(View.INVISIBLE);

                    }
                },2000);

            }
        }
    };


    public void selected(){
        it = getIntent();
        selections = it.getBooleanArrayExtra("regi");
        if (selections[0]){
            regPlayed.add("Africa");
        }
        if (selections[1]){
            regPlayed.add("Asia");
        }
        if (selections[2]){
            regPlayed.add("Europe");
        }
        if (selections[3]){
            regPlayed.add("North_America");
        }
        if (selections[4]){
            regPlayed.add("South_America");
        }
    }

    public void fourFlags()  {
        choice.setText("Select a Region:");
        //count++;
        AssetManager assets = getAssets();
        //selected();
        Collections.shuffle(regPlayed);
        Random rand = new Random();
        int numb;
        if (regPlayed.get(0) == "Africa"){
            try {
                String [] africa =	assets.list("Africa");
                numb = rand.nextInt(africa.length-4);
                InputStream imageStream1 = getAssets().open("Africa/"+africa[numb]);
                Drawable theFlag1 = Drawable.createFromStream(imageStream1,null);
                flag1.setImageDrawable(theFlag1);
                flag1.setVisibility(View.VISIBLE);

                InputStream imageStream2 = getAssets().open("Africa/"+africa[numb+1]);
                Drawable theFlag2 = Drawable.createFromStream(imageStream2,null);
                flag2.setImageDrawable(theFlag2);
                flag2.setVisibility(View.VISIBLE);

                InputStream imageStream3 = getAssets().open("Africa/"+africa[numb+2]);
                Drawable theFlag3 = Drawable.createFromStream(imageStream3,null);
                flag3.setImageDrawable(theFlag3);
                flag3.setVisibility(View.VISIBLE);

                InputStream imageStream4 = getAssets().open("Africa/"+africa[numb+3]);
                Drawable theFlag4 = Drawable.createFromStream(imageStream4,null);
                flag4.setImageDrawable(theFlag4);
                flag4.setVisibility(View.VISIBLE);
                answer = "Africa";


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else if (regPlayed.get(0) == "Asia"){
            try {
                String [] asia =	assets.list("Asia");
                numb = rand.nextInt(asia.length-4);
                InputStream imageStream1 = getAssets().open("Asia/"+asia[numb]);
                Drawable theFlag1 = Drawable.createFromStream(imageStream1,null);
                flag1.setImageDrawable(theFlag1);
                flag1.setVisibility(View.VISIBLE);

                InputStream imageStream2 = getAssets().open("Asia/"+asia[numb+1]);
                Drawable theFlag2 = Drawable.createFromStream(imageStream2,null);
                flag2.setImageDrawable(theFlag2);
                flag2.setVisibility(View.VISIBLE);

                InputStream imageStream3 = getAssets().open("Asia/"+asia[numb+2]);
                Drawable theFlag3 = Drawable.createFromStream(imageStream3,null);
                flag3.setImageDrawable(theFlag3);
                flag3.setVisibility(View.VISIBLE);

                InputStream imageStream4 = getAssets().open("Asia/"+asia[numb+3]);
                Drawable theFlag4 = Drawable.createFromStream(imageStream4,null);
                flag4.setImageDrawable(theFlag4);
                flag4.setVisibility(View.VISIBLE);
                answer = "Asia";


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else if (regPlayed.get(0) == "Europe"){
            try {
                String [] europe =	assets.list("Europe");
                numb = rand.nextInt(europe.length-4);
                InputStream imageStream1 = getAssets().open("Europe/"+europe[numb]);
                Drawable theFlag1 = Drawable.createFromStream(imageStream1,null);
                flag1.setImageDrawable(theFlag1);
                flag1.setVisibility(View.VISIBLE);

                InputStream imageStream2 = getAssets().open("Europe/"+europe[numb+1]);
                Drawable theFlag2 = Drawable.createFromStream(imageStream2,null);
                flag2.setImageDrawable(theFlag2);
                flag2.setVisibility(View.VISIBLE);

                InputStream imageStream3 = getAssets().open("Europe/"+europe[numb+2]);
                Drawable theFlag3 = Drawable.createFromStream(imageStream3,null);
                flag3.setImageDrawable(theFlag3);
                flag3.setVisibility(View.VISIBLE);

                InputStream imageStream4 = getAssets().open("Europe/"+europe[numb+3]);
                Drawable theFlag4 = Drawable.createFromStream(imageStream4,null);
                flag4.setImageDrawable(theFlag4);
                flag4.setVisibility(View.VISIBLE);
                answer = "Europe";


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else if (regPlayed.get(0) == "North_America"){
            try {
                String [] north_america =	assets.list("North_America");
                numb = rand.nextInt(north_america.length-4);
                InputStream imageStream1 = getAssets().open("North_America/"+north_america[numb]);
                Drawable theFlag1 = Drawable.createFromStream(imageStream1,null);
                flag1.setImageDrawable(theFlag1);
                flag1.setVisibility(View.VISIBLE);

                InputStream imageStream2 = getAssets().open("North_America/"+north_america[numb+1]);
                Drawable theFlag2 = Drawable.createFromStream(imageStream2,null);
                flag2.setImageDrawable(theFlag2);
                flag2.setVisibility(View.VISIBLE);

                InputStream imageStream3 = getAssets().open("North_America/"+north_america[numb+2]);
                Drawable theFlag3 = Drawable.createFromStream(imageStream3,null);
                flag3.setImageDrawable(theFlag3);
                flag3.setVisibility(View.VISIBLE);

                InputStream imageStream4 = getAssets().open("North_America/"+north_america[numb+3]);
                Drawable theFlag4 = Drawable.createFromStream(imageStream4,null);
                flag4.setImageDrawable(theFlag4);
                flag4.setVisibility(View.VISIBLE);
                answer = "North_America";


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else if (regPlayed.get(0) == "South_America"){
            try {
                String [] south_america =	assets.list("South_America");
                numb = rand.nextInt(south_america.length-4);
                InputStream imageStream1 = getAssets().open("South_America/"+south_america[numb]);
                Drawable theFlag1 = Drawable.createFromStream(imageStream1,null);
                flag1.setImageDrawable(theFlag1);
                flag1.setVisibility(View.VISIBLE);

                InputStream imageStream2 = getAssets().open("South_America/"+south_america[numb+1]);
                Drawable theFlag2 = Drawable.createFromStream(imageStream2,null);
                flag2.setImageDrawable(theFlag2);
                flag2.setVisibility(View.VISIBLE);

                InputStream imageStream3 = getAssets().open("South_America/"+south_america[numb+2]);
                Drawable theFlag3 = Drawable.createFromStream(imageStream3,null);
                flag3.setImageDrawable(theFlag3);
                flag3.setVisibility(View.VISIBLE);

                InputStream imageStream4 = getAssets().open("South_America/"+south_america[numb+3]);
                Drawable theFlag4 = Drawable.createFromStream(imageStream4,null);
                flag4.setImageDrawable(theFlag4);
                flag4.setVisibility(View.VISIBLE);
                answer = "South_America";


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        Collections.shuffle(butons);
        butons.get(0).setText(regPlayed.get(0));
        butons.get(1).setText(regPlayed.get(1));
        butons.get(2).setText(regPlayed.get(2));
        butons.get(3).setText(regPlayed.get(3));
    }

    public void oneFlag(){
        choice.setText("Select a country:");
        //count++;
        Random n = new Random ();
        //selected();
        AssetManager assets = getAssets();
        int number = n.nextInt(regPlayed.size());
        if (regPlayed.get(number) == "Africa"){
            try {
                int random;
                String [] africa =	assets.list("Africa");
                random = n.nextInt(africa.length-4);

                InputStream imageStream1 = getAssets().open("Africa/"+africa[random]);
                Drawable theFlag = Drawable.createFromStream(imageStream1,null);
                bigFlag.setImageDrawable(theFlag);
                bigFlag.setVisibility(View.VISIBLE);
                answer = africa[random].substring(africa[random].indexOf('-') + 1).replace(".png", "");

                Collections.shuffle(butons);
                butons.get(0).setText(africa[random].substring(africa[random].indexOf('-') + 1).replace(".png", ""));
                butons.get(1).setText(africa[random+1].substring(africa[random+1].indexOf('-') + 1).replace(".png", ""));
                butons.get(2).setText(africa[random+2].substring(africa[random+2].indexOf('-') + 1).replace(".png", ""));
                butons.get(3).setText(africa[random+3].substring(africa[random+3].indexOf('-') + 1).replace(".png", ""));


            } catch (IOException e) {

                e.printStackTrace();
            }


        }else if (regPlayed.get(number) == "Asia"){
            try {
                int random;
                String [] asia =	assets.list("Asia");
                random = n.nextInt(asia.length-4);

                InputStream imageStream1 = getAssets().open("Asia/"+asia[random]);
                Drawable theFlag = Drawable.createFromStream(imageStream1,null);
                bigFlag.setImageDrawable(theFlag);
                bigFlag.setVisibility(View.VISIBLE);
                answer = asia[random].substring(asia[random].indexOf('-') + 1).replace(".png", "");

                Collections.shuffle(butons);
                butons.get(0).setText(asia[random].substring(asia[random].indexOf('-') + 1).replace(".png", ""));
                butons.get(1).setText(asia[random+1].substring(asia[random+1].indexOf('-') + 1).replace(".png", ""));
                butons.get(2).setText(asia[random+2].substring(asia[random+2].indexOf('-') + 1).replace(".png", ""));
                butons.get(3).setText(asia[random+3].substring(asia[random+3].indexOf('-') + 1).replace(".png", ""));


            } catch (IOException e) {

                e.printStackTrace();
            }


        } else if (regPlayed.get(number) == "Europe"){
            try {
                int random;
                String [] europe =	assets.list("Europe");
                random = n.nextInt(europe.length-4);

                InputStream imageStream1 = getAssets().open("Europe/"+europe[random]);
                Drawable theFlag = Drawable.createFromStream(imageStream1,null);
                bigFlag.setImageDrawable(theFlag);
                bigFlag.setVisibility(View.VISIBLE);
                answer = europe[random].substring(europe[random].indexOf('-') + 1).replace(".png", "");

                Collections.shuffle(butons);
                butons.get(0).setText(europe[random].substring(europe[number].indexOf('-') + 1).replace(".png", ""));
                butons.get(1).setText(europe[random+1].substring(europe[random+1].indexOf('-') + 1).replace(".png", ""));
                butons.get(2).setText(europe[random+2].substring(europe[random+2].indexOf('-') + 1).replace(".png", ""));
                butons.get(3).setText(europe[random+3].substring(europe[random+3].indexOf('-') + 1).replace(".png", ""));


            } catch (IOException e) {

                e.printStackTrace();
            }


        }else if (regPlayed.get(number) == "North_America"){
            try {
                int random;
                String [] north_america =	assets.list("North_America");
                random = n.nextInt(north_america.length-4);

                InputStream imageStream1 = getAssets().open("North_America/"+north_america[random]);
                Drawable theFlag = Drawable.createFromStream(imageStream1,null);
                bigFlag.setImageDrawable(theFlag);
                bigFlag.setVisibility(View.VISIBLE);
                answer = north_america[random].substring(north_america[random].indexOf('-') + 1).replace(".png", "");

                Collections.shuffle(butons);
                butons.get(0).setText(north_america[random].substring(north_america[random].indexOf('-') + 1).replace(".png", ""));
                butons.get(1).setText(north_america[random+1].substring(north_america[random+1].indexOf('-') + 1).replace(".png", ""));
                butons.get(2).setText(north_america[random+2].substring(north_america[random+2].indexOf('-') + 1).replace(".png", ""));
                butons.get(3).setText(north_america[random+3].substring(north_america[random+3].indexOf('-') + 1).replace(".png", ""));


            } catch (IOException e) {

                e.printStackTrace();
            }


        }else if (regPlayed.get(number) == "South_America"){
            try {
                int random;
                String [] south_america =	assets.list("South_America");
                random = n.nextInt(south_america.length-4);

                InputStream imageStream1 = getAssets().open("South_America/"+south_america[random]);
                Drawable theFlag = Drawable.createFromStream(imageStream1,null);
                bigFlag.setImageDrawable(theFlag);
                bigFlag.setVisibility(View.VISIBLE);
                answer = south_america[random].substring(south_america[random].indexOf('-') + 1).replace(".png", "");

                Collections.shuffle(butons);
                butons.get(0).setText(south_america[random].substring(south_america[random].indexOf('-') + 1).replace(".png", ""));
                butons.get(1).setText(south_america[random+1].substring(south_america[random+1].indexOf('-') + 1).replace(".png", ""));
                butons.get(2).setText(south_america[random+2].substring(south_america[random+2].indexOf('-') + 1).replace(".png", ""));
                butons.get(3).setText(south_america[random+3].substring(south_america[random+3].indexOf('-') + 1).replace(".png", ""));


            } catch (IOException e) {

                e.printStackTrace();
            }


        }

    }


}
