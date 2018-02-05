package com.example.ivan.puzzle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class Game extends AppCompatActivity implements View.OnTouchListener{
    private ImageView back, img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img15,img16;
    private ArrayList <ImageView> images = new ArrayList <> ();
    private int width,height;
    float x,y,dx,dy,myX,myY;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        back = (ImageView) findViewById(R.id.imageView2);

        img1 = (ImageView) findViewById(R.id.imageView3);
        img2 = (ImageView) findViewById(R.id.imageView4);
        img3 = (ImageView) findViewById(R.id.imageView11);
        img4 = (ImageView) findViewById(R.id.imageView12);
        img5 = (ImageView) findViewById(R.id.imageView13);
        img6 = (ImageView) findViewById(R.id.imageView14);
        img7 = (ImageView) findViewById(R.id.imageView15);
        img8 = (ImageView) findViewById(R.id.imageView16);
        img9 = (ImageView) findViewById(R.id.imageView17);
        img10 = (ImageView) findViewById(R.id.imageView18);
        img11 = (ImageView) findViewById(R.id.imageView5);
        img12 = (ImageView) findViewById(R.id.imageView6);
        img13 = (ImageView) findViewById(R.id.imageView7);
        img14 = (ImageView) findViewById(R.id.imageView8);
        img15 = (ImageView) findViewById(R.id.imageView9);
        img16 = (ImageView) findViewById(R.id.imageView10);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;

        FrameLayout.LayoutParams image = new FrameLayout.LayoutParams(width,width);
        back.setLayoutParams(image);

        set_pieces();
        img1.setOnTouchListener(this);
        img2.setOnTouchListener(this);
        img3.setOnTouchListener(this);
        img4.setOnTouchListener(this);
        img5.setOnTouchListener(this);
        img6.setOnTouchListener(this);
        img7.setOnTouchListener(this);
        img8.setOnTouchListener(this);
        img9.setOnTouchListener(this);
        img10.setOnTouchListener(this);
        img11.setOnTouchListener(this);
        img12.setOnTouchListener(this);
        img13.setOnTouchListener(this);
        img14.setOnTouchListener(this);
        img15.setOnTouchListener(this);
        img16.setOnTouchListener(this);




    }

    public void set_pieces(){
        images.add(img1);
        images.add(img2);
        images.add(img3);
        images.add(img4);
        images.add(img5);
        images.add(img6);
        images.add(img7);
        images.add(img8);
        images.add(img9);
        images.add(img10);
        images.add(img11);
        images.add(img12);
        images.add(img13);
        images.add(img14);
        images.add(img15);
        images.add(img16);
        FrameLayout.LayoutParams margin = new FrameLayout.LayoutParams((int)(width*0.25),(int)(width*0.25));
        margin.setMargins((int)(width*0.375),(int)(width*1.1),0,0);
        for(int i=0; i<images.size(); i++){
            images.get(i).setLayoutParams(margin);
        }
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                v.bringToFront();
                break;
            case MotionEvent.ACTION_UP:
                if(v==img1){
                    if(v.getX() <= (width*0.1) && v.getY() <= (width*0.1)){
                        v.setX(0);
                        v.setY(0);
                        img1.setEnabled(false);
                        count++;
                    }
                } else if(v == img2){
                    if(abs(v.getX() - (width*0.25)) <= (width*0.25) && v.getY() <= (width*0.1)){
                        v.setX(width*0.25f);
                        v.setY(0);
                        img2.setEnabled(false);
                        count++;
                    }

                }else if(v == img3){
                    if(abs(v.getX() - (width*0.5)) <= (width*0.1) && v.getY() <= (width*0.1)){
                        v.setX(width*0.5f);
                        v.setY(0);
                        img3.setEnabled(false);
                        count++;
                    }

                }else if(v == img4){
                    if(abs(v.getX() - (width*0.75)) <= (width*0.1) && v.getY() <= (width*0.1)){
                        v.setX(width*0.75f);
                        v.setY(0);
                        img4.setEnabled(false);
                        count++;

                    }

                }else if(v == img5){
                    if(v.getX() <= (width*0.1) && abs(v.getY() - (width*0.25)) <= (width*0.1)){
                        v.setX(0);
                        v.setY(width*0.25f);
                        img5.setEnabled(false);
                        count++;
                    }

                }else if(v == img6){
                    if(abs(v.getX() - (width*0.25)) <= (width*0.1) && abs(v.getY() - (width*0.25)) <= (width*0.1)){
                        v.setX(width*0.25f);
                        v.setY(width*0.25f);
                        img6.setEnabled(false);
                        count++;
                    }

                }else if(v == img7){
                    if(abs(v.getX() - (width*0.5)) <= (width*0.1) && abs(v.getY() - (width*0.25)) <= (width*0.1)){
                        v.setX(width*0.5f);
                        v.setY(width*0.25f);
                        img7.setEnabled(false);
                        count++;
                    }

                }else if(v == img8){
                    if(abs(v.getX() - (width*0.75)) <= (width*0.1) && abs(v.getY()-(width*0.25f)) <= (width*0.1)){
                        v.setX(width*0.75f);
                        v.setY(width*0.25f);
                        img8.setEnabled(false);
                        count++;
                    }

                }else if(v == img9){
                    if(v.getX() <= (width*0.1) && abs(v.getY() - (width*0.5))<= (width*0.1)){
                        v.setX(0);
                        v.setY(width*0.5f);
                        img9.setEnabled(false);
                        count++;
                    }

                }else if(v == img10){
                    if(abs(v.getX() - (width*0.25)) <= (width*0.1) && abs(v.getY() - (width*0.5))<= (width*0.1) ){
                        v.setX(width*0.25f);
                        v.setY(width*0.5f);
                        img10.setEnabled(false);
                        count++;
                    }

                }else if(v == img11){
                    if(abs(v.getX() - (width*0.5)) <= (width*0.1) && abs(v.getY() - (width*0.5)) <= (width*0.1)){
                        v.setX(width*0.5f);
                        v.setY(width*0.5f);
                        img11.setEnabled(false);
                        count++;
                    }

                }else if(v == img12){
                    if(abs(v.getX() - (width*0.75)) <= (width*0.1) && abs(v.getY() - (width*0.5)) <= (width*0.1)){
                        v.setX(width*0.75f);
                        v.setY(width*0.5f);
                        img12.setEnabled(false);
                        count++;
                    }

                }  else if(v == img13){
                    if(v.getX() <= (width*0.1) && abs(v.getY() - (width*0.75)) <= (width*0.1)){
                        v.setX(0);
                        v.setY(width*0.75f);
                        img13.setEnabled(false);
                        count++;
                    }

                }else if(v == img14){
                    if(abs(v.getX() - (width*0.25)) <= (width*0.1) && abs(v.getY() - (width*0.75)) <= (width*0.1)){
                        v.setX(width*0.25f);
                        v.setY(width*0.75f);
                        img14.setEnabled(false);
                        count++;
                    }

                }else if(v == img15){
                    if(abs(v.getX() - (width*0.5)) <= (width*0.1) && abs(v.getY() - (width*0.75)) <= (width*0.1)){
                        v.setX(width*0.5f);
                        v.setY(width*0.75f);
                        img15.setEnabled(false);
                        count++;
                    }

                }else if(v == img16){
                    if(abs(v.getX() - (width*0.75)) <= (width*0.1) && abs(v.getY() - (width*0.75)) <= (width*0.1)){
                        v.setX(width*0.75f);
                        v.setY(width*0.75f);
                        img16.setEnabled(false);
                        count++;
                    }
                }
                gameOver();
                break;
            case MotionEvent.ACTION_MOVE:


                dx = (event.getX()-x);
                dy = (event.getY()-y);

                myX += dx;
                myY += dy;

                v.setX(myX);
                v.setY(myY);

        }
        myX = v.getX();
        myY = v.getY();
        return true;
    }

    public void gameOver(){
        if(count == 16){
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setTitle("Well done, puzzle completed");
            msg.setMessage("Do you want to restart the game?");

            msg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    recreate();
                }
            });

            msg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Game.this,MainActivity.class));
                    finish();
                }
            });
            AlertDialog done = msg.create();
            done.show();
            count =0;
        }
    }
}
