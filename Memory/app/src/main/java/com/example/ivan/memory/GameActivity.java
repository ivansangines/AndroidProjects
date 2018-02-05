package com.example.ivan.memory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;


public class GameActivity extends AppCompatActivity  {
    private Spinner mySpinner;
    private ImageView img;
    private ArrayList<ImageView> images = new ArrayList<ImageView>();
    private ArrayList<Bitmap> draws = new ArrayList<Bitmap>();
    private ArrayList<Integer> random;
    Bitmap front;
    private Animation.AnimationListener rotationListener;


    private int win;

    Context thisone;
    int lon = Toast.LENGTH_LONG;
    int sho = Toast.LENGTH_SHORT;
    Toast theToast;
    private int index1,index2;
    private int clicks = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        thisone = this;
        theToast = Toast.makeText(thisone,"You won!",lon);

        mySpinner = (Spinner) findViewById(R.id.spinner1);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        win=7;
                        mySpinner.setEnabled(false);
                        game1();
                        break;
                    case 2:
                        win=5;
                        mySpinner.setEnabled(false);
                        game2();
                        break;
                    case 3:
                        win=0;
                        mySpinner.setEnabled(false);
                        game3();
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setGame();
        rotationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                images.get(index1).setAlpha((float)0.5);
                images.get(index2).setAlpha((float)0.5);
                if(win==10){
                    clicks = 0;
                    index1 = index2 = 0;
                    mySpinner.setEnabled(true);
                    theToast.show();
                    for(int i=0; i<20; i++){
                        images.get(i).setAlpha((float)1.0);
                        images.get(i).setImageBitmap(front);
                        images.get(i).setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

    }



    public void setGame (){
        img = (ImageView) findViewById(R.id.imageView2);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView3);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView6);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView7);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView10);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView11);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView14);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView15);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView18);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView19);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView1);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView4);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView5);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView8);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView9);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView12);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView13);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView16);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView17);
        images.add(img);
        img = (ImageView) findViewById(R.id.imageView20);
        images.add(img);

        Bitmap apple = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        draws.add(apple);
        Bitmap banana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
        draws.add(banana);
        Bitmap cherries = BitmapFactory.decodeResource(getResources(), R.drawable.cherries);
        draws.add(cherries);
        Bitmap grapes = BitmapFactory.decodeResource(getResources(), R.drawable.grapes);
        draws.add(grapes);
        Bitmap orange = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
        draws.add(orange);
        Bitmap papaya = BitmapFactory.decodeResource(getResources(), R.drawable.papaya);
        draws.add(papaya);
        Bitmap piña = BitmapFactory.decodeResource(getResources(), R.drawable.pineapple);
        draws.add(piña);
        Bitmap star = BitmapFactory.decodeResource(getResources(), R.drawable.star);
        draws.add(star);
        Bitmap fresa = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry);
        draws.add(fresa);
        Bitmap sandia = BitmapFactory.decodeResource(getResources(), R.drawable.watermelon);
        draws.add(sandia);
        front = BitmapFactory.decodeResource(getResources(), R.drawable.front);


    }

    public void game1() {


        for (int i = 0; i<6; i++){
            images.get(i).setVisibility(View.VISIBLE);
        }
        random = new ArrayList<Integer>();
        for(int i =0; i<3; i++) {
            random.add(i);
        }for(int i =0; i<3; i++) {
            random.add(i);
        }
        Collections.shuffle(random);

        images.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks++;
                    index1=0;
                    //choice1 = random.get(0);
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks =1;
                    index1=0;
                }else{

                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks++;
                    //choice2 = random.get(0);
                    index2=0;
                    compare();
                }
            }
        });
        images.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks++;
                    //choice1 = random.get(1);
                    index1=1;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks =1;
                    index1=1;
                }else{
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks++;
                    //choice2 = random.get(1);
                    index2=1;
                    compare();
                }

            }
        });
        images.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicks==0){
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks++;
                    //choice1 = random.get(2);
                    index1=2;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks =1;
                    index1=2;
                }else{
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks++;
                    //choice2 = random.get(2);
                    index2=2;
                    compare();
                }
            }
        });
        images.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks++;
                    //choice1 = random.get(3);
                    index1=3;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks =1;
                    index1=3;
                }else{
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks++;
                    //choice2 = random.get(3);
                    index2=3;
                    compare();
                }
            }
        });
        images.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks++;
                    //choice1 = random.get(4);
                    index1=4;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks =1;
                    index1=4;
                }else{
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks++;
                    //choice2 = random.get(4);
                    index2=4;
                    compare();
                }
            }
        });
        images.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks++;
                    //choice1 = random.get(5);
                    index1=5;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks =1;
                    index1=5;
                }else{
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks++;
                    //choice2 = random.get(5);
                    index2=5;
                    compare();
                }
            }
        });

    }

    public void game2() {
        for (int i = 0; i<10; i++){
            images.get(i).setVisibility(View.VISIBLE);
        }
        random = new ArrayList<Integer>();
        for(int i =0; i<5; i++) {
            random.add(i);
        }for(int i =0; i<5; i++) {
            random.add(i);
        }
        Collections.shuffle(random);

        images.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks++;
                    index1=0;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks =1;
                    index1=0;
                }else{

                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks++;
                    index2=0;
                    compare();
                }
            }
        });
        images.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks++;
                    index1=1;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks =1;
                    index1=1;
                }else{
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks++;
                    index2=1;
                    compare();
                }

            }
        });
        images.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicks==0){
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks++;
                    index1=2;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks =1;
                    index1=2;
                }else{
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks++;
                    index2=2;
                    compare();
                }
            }
        });
        images.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks++;
                    index1=3;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks =1;
                    index1=3;
                }else{
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks++;
                    index2=3;
                    compare();
                }
            }
        });
        images.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks++;
                    index1=4;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks =1;
                    index1=4;
                }else{
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks++;
                    index2=4;
                    compare();
                }
            }
        });
        images.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks++;
                    index1=5;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks =1;
                    index1=5;
                }else{
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks++;
                    index2=5;
                    compare();
                }
            }
        });

        images.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(6).setImageBitmap(draws.get(random.get(6)));
                    clicks++;
                    index1=6;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(6).setImageBitmap(draws.get(random.get(6)));
                    clicks =1;
                    index1=6;
                }else{

                    images.get(6).setImageBitmap(draws.get(random.get(6)));
                    clicks++;
                    index2=6;
                    compare();
                }
            }
        });
        images.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(7).setImageBitmap(draws.get(random.get(7)));
                    clicks++;
                    index1=7;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(7).setImageBitmap(draws.get(random.get(7)));
                    clicks =1;
                    index1=7;
                }else{
                    images.get(7).setImageBitmap(draws.get(random.get(7)));
                    clicks++;
                    index2=7;
                    compare();
                }

            }
        });
        images.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicks==0){
                    images.get(8).setImageBitmap(draws.get(random.get(8)));
                    clicks++;
                    index1=8;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(8).setImageBitmap(draws.get(random.get(8)));
                    clicks =1;
                    index1=8;
                }else{
                    images.get(8).setImageBitmap(draws.get(random.get(8)));
                    clicks++;
                    index2=8;
                    compare();
                }
            }
        });
        images.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(9).setImageBitmap(draws.get(random.get(9)));
                    clicks++;
                    index1=9;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(9).setImageBitmap(draws.get(random.get(9)));
                    clicks =1;
                    index1=9;
                }else{
                    images.get(9).setImageBitmap(draws.get(random.get(9)));
                    clicks++;
                    index2=9;
                    compare();
                }
            }
        });


    }
    public void game3(){
        for (int i = 0; i<20; i++){
            images.get(i).setVisibility(View.VISIBLE);
        }
        random = new ArrayList<Integer>();
        for(int i =0; i<10; i++) {
            random.add(i);
        }for(int i =0; i<10; i++) {
            random.add(i);
        }
        Collections.shuffle(random);

        images.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks++;
                    index1=0;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks =1;
                    index1=0;
                }else{

                    images.get(0).setImageBitmap(draws.get(random.get(0)));
                    clicks++;
                    index2=0;
                    compare();
                }
            }
        });
        images.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks++;
                    index1=1;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks =1;
                    index1=1;
                }else{
                    images.get(1).setImageBitmap(draws.get(random.get(1)));
                    clicks++;
                    index2=1;
                    compare();
                }

            }
        });
        images.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicks==0){
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks++;
                    index1=2;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks =1;
                    index1=2;
                }else{
                    images.get(2).setImageBitmap(draws.get(random.get(2)));
                    clicks++;
                    index2=2;
                    compare();
                }
            }
        });
        images.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks++;
                    index1=3;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks =1;
                    index1=3;
                }else{
                    images.get(3).setImageBitmap(draws.get(random.get(3)));
                    clicks++;
                    index2=3;
                    compare();
                }
            }
        });
        images.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks++;
                    index1=4;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks =1;
                    index1=4;
                }else{
                    images.get(4).setImageBitmap(draws.get(random.get(4)));
                    clicks++;
                    index2=4;
                    compare();
                }
            }
        });
        images.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks++;
                    index1=5;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks =1;
                    index1=5;
                }else{
                    images.get(5).setImageBitmap(draws.get(random.get(5)));
                    clicks++;
                    index2=5;
                    compare();
                }
            }
        });

        images.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(6).setImageBitmap(draws.get(random.get(6)));
                    clicks++;
                    index1=6;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(6).setImageBitmap(draws.get(random.get(6)));
                    clicks =1;
                    index1=6;
                }else{

                    images.get(6).setImageBitmap(draws.get(random.get(6)));
                    clicks++;
                    index2=6;
                    compare();
                }
            }
        });
        images.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(7).setImageBitmap(draws.get(random.get(7)));
                    clicks++;
                    index1=7;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(7).setImageBitmap(draws.get(random.get(7)));
                    clicks =1;
                    index1=7;
                }else{
                    images.get(7).setImageBitmap(draws.get(random.get(7)));
                    clicks++;
                    index2=7;
                    compare();
                }

            }
        });
        images.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicks==0){
                    images.get(8).setImageBitmap(draws.get(random.get(8)));
                    clicks++;
                    index1=8;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(8).setImageBitmap(draws.get(random.get(8)));
                    clicks =1;
                    index1=8;
                }else{
                    images.get(8).setImageBitmap(draws.get(random.get(8)));
                    clicks++;
                    index2=8;
                    compare();
                }
            }
        });
        images.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(9).setImageBitmap(draws.get(random.get(9)));
                    clicks++;
                    index1=9;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(9).setImageBitmap(draws.get(random.get(9)));
                    clicks =1;
                    index1=9;
                }else{
                    images.get(9).setImageBitmap(draws.get(random.get(9)));
                    clicks++;
                    index2=9;
                    compare();
                }
            }
        });
        images.get(10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(10).setImageBitmap(draws.get(random.get(10)));
                    clicks++;
                    index1=10;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(10).setImageBitmap(draws.get(random.get(10)));
                    clicks =1;
                    index1=10;
                }else{

                    images.get(10).setImageBitmap(draws.get(random.get(10)));
                    clicks++;
                    index2=10;
                    compare();
                }
            }
        });
        images.get(11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(11).setImageBitmap(draws.get(random.get(11)));
                    clicks++;
                    index1=11;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(11).setImageBitmap(draws.get(random.get(11)));
                    clicks =1;
                    index1=11;
                }else{
                    images.get(11).setImageBitmap(draws.get(random.get(11)));
                    clicks++;
                    index2=11;
                    compare();
                }

            }
        });
        images.get(12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicks==0){
                    images.get(12).setImageBitmap(draws.get(random.get(12)));
                    clicks++;
                    index1=12;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(12).setImageBitmap(draws.get(random.get(12)));
                    clicks =1;
                    index1=12;
                }else{
                    images.get(12).setImageBitmap(draws.get(random.get(12)));
                    clicks++;
                    index2=12;
                    compare();
                }
            }
        });
        images.get(13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(13).setImageBitmap(draws.get(random.get(13)));
                    clicks++;
                    index1=13;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(13).setImageBitmap(draws.get(random.get(13)));
                    clicks =1;
                    index1=13;
                }else{
                    images.get(13).setImageBitmap(draws.get(random.get(13)));
                    clicks++;
                    index2=13;
                    compare();
                }
            }
        });
        images.get(14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(14).setImageBitmap(draws.get(random.get(14)));
                    clicks++;
                    index1=14;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(14).setImageBitmap(draws.get(random.get(14)));
                    clicks =1;
                    index1=14;
                }else{
                    images.get(14).setImageBitmap(draws.get(random.get(14)));
                    clicks++;
                    index2=14;
                    compare();
                }
            }
        });
        images.get(15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(15).setImageBitmap(draws.get(random.get(15)));
                    clicks++;
                    index1=15;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(15).setImageBitmap(draws.get(random.get(15)));
                    clicks =1;
                    index1=15;
                }else{
                    images.get(15).setImageBitmap(draws.get(random.get(15)));
                    clicks++;
                    index2=15;
                    compare();
                }
            }
        });

        images.get(16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(16).setImageBitmap(draws.get(random.get(16)));
                    clicks++;
                    index1=16;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(16).setImageBitmap(draws.get(random.get(16)));
                    clicks =1;
                    index1=16;
                }else{

                    images.get(16).setImageBitmap(draws.get(random.get(16)));
                    clicks++;
                    index2=16;
                    compare();
                }
            }
        });
        images.get(17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks==0){
                    images.get(17).setImageBitmap(draws.get(random.get(17)));
                    clicks++;
                    index1=17;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(17).setImageBitmap(draws.get(random.get(17)));
                    clicks =1;
                    index1=17;
                }else{
                    images.get(17).setImageBitmap(draws.get(random.get(17)));
                    clicks++;
                    index2=17;
                    compare();
                }

            }
        });
        images.get(18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicks==0){
                    images.get(18).setImageBitmap(draws.get(random.get(18)));
                    clicks++;
                    index1=18;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(18).setImageBitmap(draws.get(random.get(18)));
                    clicks =1;
                    index1=18;
                }else{
                    images.get(18).setImageBitmap(draws.get(random.get(18)));
                    clicks++;
                    index2=18;
                    compare();
                }
            }
        });
        images.get(19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clicks==0){
                    images.get(19).setImageBitmap(draws.get(random.get(19)));
                    clicks++;
                    index1=19;
                }else if(clicks == 2){
                    images.get(index1).setImageBitmap(front);
                    images.get(index2).setImageBitmap(front);
                    images.get(19).setImageBitmap(draws.get(random.get(19)));
                    clicks =1;
                    index1=19;
                }else{
                    images.get(19).setImageBitmap(draws.get(random.get(19)));
                    clicks++;
                    index2=19;
                    compare();
                }
            }
        });
    }

    public void compare(){
        if(random.get(index1) == random.get(index2)){
            Animation rotation = AnimationUtils.loadAnimation(GameActivity.this, R.anim.rotate);
            rotation.setAnimationListener(rotationListener);
            images.get(index1).setClickable(false);
            images.get(index2).setClickable(false);
            images.get(index1).startAnimation(rotation);
            images.get(index2).startAnimation(rotation);
            clicks =0;
            win++;

        }

    }

}


