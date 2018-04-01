package ubknights.com.spaceshooter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

/**
 * Created by carlos on 10/13/2016.
 */

public class TheGameLevel2 extends Activity implements View.OnTouchListener{

    TheView mySurfaceView;      //the surfaceview, where we draw
    TheSprites2 allsprites; //class that has all the location and sizes of the images in the sprite
    float xTouch,yTouch;          //the location when the screen is touched
    int s_width,s_height;       //the size of the surfaceview
    //used for which sprite to use
    int loc = 0,eLoc=0,e1Loc=3,e2Loc=0,m1Loc=0,m2Loc=1,m3Loc=2;
    Point shipbullet,explosion,meteor1,meteor2,meteor3;
    long enemyMoveTime = 0;
    //15 frames per seconds
    float skipTime =1000.0f/30.0f; //setting 30fps
    float positionx, positiony, positionx2,positiony2, positionx3, positiony3; //to get position in floats for better drawings
    float yMove, yMove2, yMove3,explosionx,explosiony;
    int hit1=0,hit2=0,hit3=0;
    long lastUpdate;
    float dt;
    boolean exp=false;
    boolean m1=false,m2=false,m3=false;
    float moveX;
    int resp=0; //to check if meteor1 was respawned before
    boolean click=false;
    boolean lose=false; //check if ship was hit
    int win = 1; //check meteors killed
    Intent end;
    Timer delay = new Timer();
    int secs=0,seconds=0,min=0,timer=0;
    Paint paint = new Paint();
    boolean firstRespawn=false;
    //sound ID
    final int SHIP_SHOT = 1;
    final int METEOR_BOOM = 2;
    final int SHIP_BOOM = 3;
    private SoundPool soundPool; // plays sound effects
    private Map<Integer, Integer> soundMap;
    private MediaPlayer player;
    Vibrator vib;

    @Override
    protected void onPause() {
        super.onPause();
        player.release();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); //Initialize vibration
        allsprites = new TheSprites2(getResources());
        //upload mainsong in mediaPlayer
        player = MediaPlayer.create(this,R.raw.mainsong);
        //setting volume down to hear others better
        player.setVolume(0.3f,0.3f);
        //start mainsong
        player.start();
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundMap = new HashMap<Integer, Integer>();
        //upload sounds effects
        soundMap.put(SHIP_SHOT, soundPool.load(this, R.raw.shipbullet, 1));
        soundMap.put(METEOR_BOOM, soundPool.load(this, R.raw.meteorboom, 1));
        soundMap.put(SHIP_BOOM, soundPool.load(this, R.raw.shipboom, 1));

        shipbullet = new Point(); //used for canvas drawing location
        meteor1 = new Point();
        meteor2 = new Point();
        meteor3 = new Point();
        explosion = new Point();
        lastUpdate = 0;         //to check against now time

        //hide the actoinbar and make it fullscreen
        hideAndFull();
        //our custom view
        mySurfaceView = new TheView(this);
        mySurfaceView.setOnTouchListener(this); //now we can touch the screen
        setContentView(mySurfaceView);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //not passing an xml, using the surfaceview as the layout
                //custom way of setting the size of the surfaceview
                mySurfaceView.startGame();
            }
        },2000);


    }

    public void hideAndFull()
    {
        ActionBar bar = getActionBar();
        bar.hide();
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getX()<allsprites.shipSize.width()) {
            click=true;//check first click
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xTouch = motionEvent.getX();
                    yTouch = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    xTouch = motionEvent.getX();
                    yTouch = motionEvent.getY();
                    view.performClick();//to get rid of the message, mimicking a click
                    break;
                case MotionEvent.ACTION_MOVE:
                    xTouch = motionEvent.getX();
                    yTouch = motionEvent.getY();
                    break;
            }
            spawnMeteor();//spawn meteor first time
        }
        return true;
    }
    // create meteor1 on click
    public void spawnMeteor() {
        if(click) {
            if (m1 == false && resp == 0) { //just first time respawning
                meteor1();
                resp++;
            } else if (m1 == false && m2 == true && m3 == true && positionx2 < s_width * 0.7 && positionx3 < s_width * 0.7) { // case where m2 and m3 are on the screen
                meteor1();
            } else if (m1 == false && m2 == false && m3 == true && positionx3 < s_width * 0.7) { //just m3 on screen wait for it to reach s_width*0.7
                meteor1();
            } else if (m1 == false && m2 == true && m3 == false && positionx2 < s_width * 0.7) { //just m2 on screen wait for it to reach s_width*0.7
                meteor1();
            }

        }


        }

    private void meteor1() {
        positionx = (float) s_width - (allsprites.meteorSize.width());
        Random ran = new Random();
        positiony = (float) ran.nextInt(s_height - (allsprites.meteorSize.height()));
        yMove = ((yTouch - (positiony + allsprites.meteorSize.height() / 2)) / 200.0f); //calculate Y movement
        m1 = true;
        hit1 = 0;// reset hits
    }

    //spawn meteor 2 if 1 are on s_width*.7 and on screen (avoid 2 respawns at same time in the whole game)
    public void spawnMeteor2(){
        if(resp>0) {
            if (m2 == false && m1 == true && m3==false && (positionx < s_width * 0.7)) {
                meteor2();
                resp++;
            } else if (m2 == false && m1 == true && m3 == true && (positionx < s_width * 0.7) && (positionx3 < s_width * 0.7)) {
                meteor2();

            } else if (m2 == false && m1 == false && m3 == true && (positionx3 < s_width * 0.7)) {
                meteor2();

            }
        }


    }
//Meteor2 random position
    private void meteor2() {
        positionx2 = (float) s_width - (allsprites.meteorSize.width());
        Random ran = new Random();
        positiony2 = (float) ran.nextInt(s_height - (allsprites.meteorSize.height()));
        yMove2 = ((yTouch - (positiony2 + allsprites.meteorSize.height() / 2)) / 200.0f);
        m2 = true;
        hit2 = 0;
    }

    //spawn meteor 3 if 2 are on s_width*.7 and meteor1 and meteor2 on screen (avoid 2 respawns at same time in the whole game)
    public void spawnMeteor3(){
        if(resp>1) {
            if (m3 == false && m2 == true && m1 == true && positionx < s_width * 0.7 && (positionx2 < s_width * 0.7)) {

                meteor3();

            } else if (m3 == false && m1 == true && m2 == false && positionx < s_width * 0.7) {
                meteor3();

            }else if(m3==false && m1==false && m2==true && positionx2 < s_width *0.7){
                meteor3();

            }
        }

    }

    //Meteor3 random position
    private void meteor3() {
        positionx3 = (float) s_width - (allsprites.meteorSize.width());
        Random ran = new Random();
        positiony3 = (float) ran.nextInt(s_height - (allsprites.meteorSize.height()));
        yMove3 = ((yTouch - (positiony3 + allsprites.meteorSize.height() / 2)) / 200.0f);
        m3 = true;
        hit3 = 0;
    }

    //surface view used so we can draw is dedicated made for drawing
    //View is updated in main thread while SurfaceView is updated in another thread.
    public class TheView extends SurfaceView implements SurfaceHolder.Callback {
        //resize and edit pixels in a surface. Holds the display
        SurfaceHolder holder;
        Boolean change = true;
        Thread gameThread;

        public TheView(Context context) {
            super(context);
            //get this holder
            holder = getHolder();//gets the surfaceview surface
            holder.addCallback(this);
            gameThread = new Thread(runn);
        }

        Runnable runn = new Runnable() {
            @Override
            public void run() {

                while (change == true) {
                    //perform drawing, does it have a surface?
                    if (!holder.getSurface().isValid()) {
                        continue;
                    }

                    dt = System.currentTimeMillis() - lastUpdate;
                    // Log.d("d", dt+" "+"latupdate: "+ lastUpdate);
                    if (dt >= skipTime) {
                        //look it to paint on it
                        Canvas c = holder.lockCanvas();
                        //draw the background color
                        Bitmap back = BitmapFactory.decodeResource(getResources(),R.drawable.background);

                        c.drawBitmap(back,0,0,null);

                        //draw ship
                        RectF place = new RectF( 0, yTouch, allsprites.shipSize.width(), yTouch + allsprites.shipSize.height() );
                        c.drawBitmap(allsprites.space, allsprites.shipSprites[eLoc], place, null);

                        //draw the explosion
                        if (exp) {
                            place = new RectF(explosionx, explosiony, explosionx + allsprites.boomSize[loc].width(),
                                    explosiony + allsprites.boomSize[loc].height());
                            c.drawBitmap(allsprites.space, allsprites.boomsprites[loc], place, null);
                        }

                        //meteor1, using a percentage of the actual screen to draw it
                        if (m1==true) {//check if meteor 1 is on screen
                            place = new RectF(positionx, positiony, positionx + allsprites.meteorSize.width(),
                                    positiony + allsprites.meteorSize.height());
                            c.drawBitmap(allsprites.space, allsprites.meteorsprites[m1Loc], place, null);
                            positionx -= moveX;
                            positiony += yMove;

                        }

                        //meteor2, using a percentage of the actual screen to draw it
                        if (m2==true) {//check if meteor2 is on screen
                            place = new RectF(positionx2, positiony2, positionx2 + allsprites.meteorSize.width(),
                                    positiony2 + allsprites.meteorSize.height());
                            c.drawBitmap(allsprites.space, allsprites.meteorsprites[m2Loc], place, null);
                            positionx2 -= moveX;
                            positiony2 += yMove2;

                        }

                        //meteor3, using a percentage of the actual screen to draw it
                        if(m3==true) {//check if meteor 3 is on screen
                            place = new RectF( positionx3, positiony3, positionx3  + allsprites.meteorSize.width(),
                                    positiony3 + allsprites.meteorSize.height());
                            c.drawBitmap(allsprites.space, allsprites.meteorsprites[m3Loc], place, null);

                            positionx3 -= moveX;
                            positiony3 += yMove3;

                        }

                        if(click) { //start drawing bullet and start time after first click

                            if(!firstRespawn) { //If it is first click, start drawing bullet
                                firstRespawn=true;
                                soundPool.play(soundMap.get(SHIP_SHOT), 1, 1, 1, 0, 1f);
                                shipbullet.x = allsprites.shipSize.width(); //set shipbullet X position in first click
                                shipbullet.y = ((int) (yTouch + allsprites.shipSize.height() / 2)); //set shipbullet Y after first click
                            }
                            //ship shooting
                            place = new RectF(shipbullet.x, shipbullet.y, shipbullet.x + allsprites.shipBulletSprite.width(),
                                    shipbullet.y + allsprites.shipBulletSprite.height());
                            c.drawBitmap(allsprites.space, allsprites.shipBulletSprite, place, null);

                            //TIMER
                            paint.setTextSize(s_width / 50);
                            paint.setColor(Color.WHITE);
                            secs = seconds / 10;
                            min = ((int) timer / 10) / 60;
                            if (secs == 60) {
                                seconds = 0;

                            }
                            c.drawText("time = " + String.format("%d:%02d",min,secs), s_width * 0.8f, s_height * 0.1f, paint);
                            seconds++;
                            timer++; //increases by tenths of a second
                            //END TIMER

                            //check if bullet hit meteors
                            checkHitMeteors();
                            //check if Meteors hit Ship
                            checkShipHit();
                            //move Ship bullet
                            shipbullet.x += (s_width-allsprites.shipSize.width())/50f;
                        }


                        holder.unlockCanvasAndPost(c);




                        loc = ((loc + 1) % 6);
                        eLoc = (eLoc + 1) % 4;
                        e1Loc = (e1Loc + 1) % 6;
                        e2Loc = (e2Loc + 1) % 6;
                        m1Loc = (m1Loc + 1) % 4;
                        m2Loc = (m2Loc + 1) % 4;
                        m3Loc = (m3Loc + 1) % 4;




                        //check if bullet or meteors are out of screen
                        if (shipbullet.x > (s_width + allsprites.shipBulletSprite.width()) ) {
                            resetShipBullet();
                        }
                        if(positionx < (0 - allsprites.meteorSize.width())){  //makes meteor1 respawn
                            positionx=s_width;
                            m1 = false;

                        }
                        if (positionx2 < (0 - allsprites.meteorSize.width())){ // makes meteor2 respawn
                            positionx2=s_width;
                            m2 = false;
                        }
                        if (positionx3 < (0 - allsprites.meteorSize.width())){ //makes meteor3 respawn
                            positionx3 = s_width;
                            m3 = false;
                        }//END CHECK

                        lastUpdate = System.currentTimeMillis();

                        spawnMeteor();
                        //spawn Meteor2
                        spawnMeteor2();
                        //spawn Meteor3
                        spawnMeteor3();

                    }
                    gameDone(); //Check if Game done
                }
            }
        };


        // reseting ship Bullet
        public void resetShipBullet()
        {
            shipbullet.y = (int) yTouch + allsprites.shipSize.height()/2;
            shipbullet.x =   allsprites.shipSize.width();
            soundPool.play(soundMap.get(SHIP_SHOT), 1, 1, 1, 0, 1f);
        }

        //Check if Meteors hit the Ship
        public void checkShipHit(){
            if(positionx < allsprites.shipSize.width()-(allsprites.shipSize.width()*0.15) && positionx+allsprites.meteorSize.width() > 0
                    && positiony+(allsprites.meteorSize.height()*0.85) > yTouch && positiony< yTouch )
            {
                vib.vibrate(500);
                soundPool.play(soundMap.get(SHIP_BOOM), 1, 1, 1, 0, 1f);
                lose=true;
            }
            else if(positionx < allsprites.shipSize.width()-(allsprites.shipSize.width()*0.15) && positionx+allsprites.meteorSize.width() > 0
                    && positiony+(allsprites.meteorSize.height()*0.15) < yTouch+allsprites.shipSize.height() && positiony> yTouch )
            {
                vib.vibrate(500);
                soundPool.play(soundMap.get(SHIP_BOOM), 1, 1, 1, 0, 1f);
                lose=true;
            }
            else if(positionx2 < allsprites.shipSize.width()-(allsprites.shipSize.width()*0.15) && positionx2+allsprites.meteorSize.width() > 0
                    && positiony2+(allsprites.meteorSize.height()*0.85) > yTouch && positiony2< yTouch )
            {
                vib.vibrate(500);
                soundPool.play(soundMap.get(SHIP_BOOM), 1, 1, 1, 0, 1f);
                lose=true;
            }
            else if(positionx2 < allsprites.shipSize.width()-(allsprites.shipSize.width()*0.15) && positionx2+allsprites.meteorSize.width() > 0
                    && positiony2+(allsprites.meteorSize.height()*0.15) < yTouch+allsprites.shipSize.height() && positiony2> yTouch )
            {
                vib.vibrate(500);
                soundPool.play(soundMap.get(SHIP_BOOM), 1, 1, 1, 0, 1f);
                lose=true;
            }
            else if(positionx3 < allsprites.shipSize.width()-(allsprites.shipSize.width()*0.15) && positionx3+allsprites.meteorSize.width() > 0
                    && positiony3+(allsprites.meteorSize.height()*0.85) > yTouch && positiony3< yTouch )
            {
                vib.vibrate(500);
                soundPool.play(soundMap.get(SHIP_BOOM), 1, 1, 1, 0, 1f);
                lose=true;
            }
            else if(positionx3 < (allsprites.shipSize.width()-(allsprites.shipSize.width()*0.15)) && positionx3+allsprites.meteorSize.width() > 0
                    && positiony3+(allsprites.meteorSize.height()*0.15) < yTouch+allsprites.shipSize.height() && positiony3> yTouch )
            {
                vib.vibrate(500);
                soundPool.play(soundMap.get(SHIP_BOOM), 1, 1, 1, 0, 1f);
                lose=true;
            }
        }

        //Check if shipBullet hits Meteors
        public void checkHitMeteors(){
            //check hit meteor1
            if (shipbullet.x > positionx && shipbullet.x < positionx+allsprites.meteorSize.width() &&
                    shipbullet.y > positiony && shipbullet.y < positiony+allsprites.meteorSize.height()){
                hit1++;
                if(hit1 == 3) {
                    vib.vibrate(1000);
                    win++;
                    loc = 0;
                    soundPool.play(soundMap.get(METEOR_BOOM), 1, 1, 1, 0, 1f);
                    explosionx = positionx;
                    explosiony = positiony;
                    positionx = s_width; //erase meteor before explosion
                    positiony = s_height; //erase meteor before explosion or bullet would detect hit in pver position
                    exp = true;
                    m1 = false;
                    hit1=0;
                    spawnMeteor();
                }
                resetShipBullet();
            }
            //check hit meteor2
            else if (shipbullet.x > positionx2 && shipbullet.x < positionx2+allsprites.meteorSize.width() &&
                    shipbullet.y > positiony2 && shipbullet.y < positiony2+allsprites.meteorSize.height()){
                hit2++;
                if(hit2 == 3) {
                    vib.vibrate(1000);
                    win++;
                    loc = 0;
                    soundPool.play(soundMap.get(METEOR_BOOM), 1, 1, 1, 0, 1f);
                    explosionx = positionx2;
                    explosiony = positiony2;
                    positionx2 = s_width; //erase meteor before explosion
                    positiony2 = s_height; //erase meteor before explosion or bullet would detect hit in pver position
                    exp = true;
                    m2 = false;
                    hit2=0;
                    spawnMeteor2();
                }
                resetShipBullet();
            }
            //check hit meteor3
            else if (shipbullet.x > positionx3 && shipbullet.x < positionx3+allsprites.meteorSize.width() &&
                    shipbullet.y >positiony3 && shipbullet.y < positiony3+allsprites.meteorSize.height()){
                hit3++;
                if(hit3 == 3) {
                    vib.vibrate(1000);
                    win++;
                    loc = 0;
                    soundPool.play(soundMap.get(METEOR_BOOM), 1, 1, 1, 0, 1f);
                    explosionx = positionx3;
                    explosiony = positiony3;
                    positionx3 = s_width; //erase meteor before explosion
                    positiony3 = s_height; //erase meteor before explosion or bullet would detect hit in pver position
                    exp = true;
                    m3 = false;
                    hit3=0;
                    spawnMeteor3();
                }
                resetShipBullet();
            }
            //make sure to draw the whole explosion
            else if(loc==5){

            exp = false;
        }

        }



        public void startGame()
        {
            gameThread.start();
        }

        public void gameDone(){
            if(win==10){
                end = new Intent(TheGameLevel2.this,MainActivity.class);
                end.putExtra("kills",win);
                end.putExtra("min",min);
                end.putExtra("sec",secs);
                startActivity(end);
                change = false;

            }else if(lose){
                end = new Intent(TheGameLevel2.this,MainActivity.class);
                end.putExtra("loses",lose);
                end.putExtra("min",min);
                end.putExtra("sec",secs);
                startActivity(end);
                change = false;

            }

            //clean the surface and show the menu by removing fullscreen
        }
        // three methods for the surfaceview
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int pixelFormat, int width, int height) {
            s_width = width;
            s_height = height;
            moveX = (s_width - allsprites.meteorSize.width())/200.0f; //X movement for meteors
            positionx=s_width;
            positionx2=s_width;
            positionx3=s_width;

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }
}
