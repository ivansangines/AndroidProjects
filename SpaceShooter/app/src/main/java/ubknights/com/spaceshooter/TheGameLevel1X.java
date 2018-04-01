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
import java.util.TimerTask;

/**
 * Created by carlos on 10/13/2016.
 */

public class TheGameLevel1X extends Activity implements View.OnTouchListener{


    TheView mySurfaceView;      //the surfaceview, where we draw
    TheSprites2 allsprites; //class that has all the location and sizes of the images in the sprite
    float xTouch,yTouch;          //the location when the screen is touched
    int s_width,s_height;       //the size of the surfaceview
    //used for which sprite to use
    int loc = 0,eLoc=0,e1Loc=3,e2Loc=0,m1Loc=0,m2Loc=1,m3Loc=2,win=0,lose=0;
    Point shipbullet,enemy1,enemy2,enemy1bullet,enemy2bullet,explosion;
    Timer delay = new Timer();
    long enemyMoveTime = 0;
    //15 frames per seconds
    float skipTime =1000.0f/30.0f; //setting 30fps
    long lastUpdate;
    long timer=0;
    int seconds=0;
    int min = 0;
    int secs = 0;
    float dt;
    float minX, maxX, minY, maxY;
    boolean exp=false, ship=true, moved=false;
    private MediaPlayer player;
    /*public static MediaPlayer finish;
    public static MediaPlayer succeed;*/
    //ID FOR SOUNDS
    final int SPACE_BOOM = 1;
    final int ENEMY_BOOM = 2;
    final int SHIP_SHOT =3;
    private SoundPool soundPool; // plays sound effects
    private Map<Integer, Integer> soundMap;
    //paint where to draw timer
    Paint paint = new Paint();
    //check begining of game
    boolean click=false;
    int resp=0;
    Vibrator vib;


    Intent end;

    @Override
    protected void onPause() {
        super.onPause();
        player.release();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        player = MediaPlayer.create(this,R.raw.mainsong);
        player.setVolume(0.3f,0.3f);
        player.start();
       /* finish = MediaPlayer.create(this,R.raw.gameover);
        succeed = MediaPlayer.create(this,R.raw.success);*/
        // initialize SoundPool to play the app's three sound effects
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        // create Map of sounds and pre-load sounds (load returns a sound_ID)
        soundMap = new HashMap<Integer, Integer>();
        soundMap.put(SPACE_BOOM, soundPool.load(this, R.raw.shipboom, 1));
        soundMap.put(ENEMY_BOOM, soundPool.load(this, R.raw.enemyboom, 1));
        soundMap.put(SHIP_SHOT, soundPool.load(this, R.raw.shipbullet, 1));


        //setContentView(R.layout.spritesheetbetter); //not setting a static xml
        //set all the sprite locations and sizes
        allsprites = new TheSprites2(getResources());

        //make sure there is only ONE copy of the image and that the image
        //is in the drawable-nodpi. if it is not unwanted scaling might occur
        shipbullet = new Point(); //used for canvas drawing location
        enemy1bullet = new Point();
        enemy2bullet = new Point();
        enemy1 = new Point();     //used for canvas drawing location
        enemy2 = new Point();
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
            click = true;
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
        }
        return true;
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
                       // c.drawARGB(255, 0, 0, 0);
                        Rect place;

                        //draw ship 4x the original size, you should use some percentage of the screen to make it the same size on every device
                        if(ship) {
                            place = new Rect(0, (int) yTouch, allsprites.shipSize.width(), (int) yTouch + allsprites.shipSize.height());
                            c.drawBitmap(allsprites.space, allsprites.shipSprites[eLoc], place, null);
                        }
                        //draw the explosion
                        if (exp) {
                            place = new Rect(explosion.x, explosion.y, explosion.x + allsprites.boomSize[loc].width(),
                                    explosion.y + allsprites.boomSize[loc].height());
                            c.drawBitmap(allsprites.space, allsprites.boomsprites[loc], place, null);
                        }
                        //draw the enemy 1
                        place = new Rect(enemy1.x, enemy1.y, enemy1.x + allsprites.enemySize.width() ,
                                enemy1.y + allsprites.enemySize.height() );
                        c.drawBitmap(allsprites.space, allsprites.enemy1sprites[e1Loc], place, null);
                        //draw the enemy 2
                        place = new Rect(enemy2.x, enemy2.y, enemy2.x + allsprites.enemySize.width() ,
                                enemy2.y + allsprites.enemySize.height() );
                        c.drawBitmap(allsprites.space, allsprites.enemy2sprites[e2Loc], place, null);


                        if(click) { //start drawing shots and time after first click
                            //ship shooting
                            place = new Rect((int) shipbullet.x, (int) shipbullet.y, (int) shipbullet.x + allsprites.shipBulletSprite.width(),
                                    shipbullet.y + allsprites.shipBulletSprite.height());
                            c.drawBitmap(allsprites.space, allsprites.shipBulletSprite, place, null);

                            //enemy1 shooting
                            place = new Rect((int) enemy1bullet.x, (int) enemy1bullet.y, (int) enemy1bullet.x + allsprites.enemybulletSprite.width(),
                                    enemy1bullet.y + allsprites.enemybulletSprite.height());
                            c.drawBitmap(allsprites.space, allsprites.enemybulletSprite, place, null);

                            //enemy2 shooting
                            place = new Rect((int) enemy2bullet.x, (int) enemy2bullet.y, (int) enemy2bullet.x + allsprites.enemybulletSprite.width(),
                                    enemy2bullet.y + allsprites.enemybulletSprite.height());
                            c.drawBitmap(allsprites.space, allsprites.enemybulletSprite, place, null);

                            //TIMER
                            paint.setTextSize(s_width/50);
                            paint.setColor(Color.WHITE);
                            secs = seconds /10;
                            min = ((int)timer/10) / 60;
                            if(secs ==60){
                                seconds=0;

                            }
                            c.drawText("time = "+String.format("%d:%02d",min, secs), s_width * 0.8f, s_height * 0.1f, paint);
                            seconds++;
                            timer++; //increases by tenths of a second
                            //END TIMER

                            c.drawText("Lives left: " + (5 - lose), s_width * 0.8f, s_height * 0.2f, paint);



                            //move ship bullet on the first click
                            if(resp==0) { //check it is the first click
                                resp++;
                                soundPool.play(soundMap.get(SHIP_SHOT), 0.7f, 0.7f, 1, 0, 1f);
                                shipbullet.x = allsprites.shipSize.width(); //set shipbullet X position in first click
                                shipbullet.y = ((int) (yTouch + allsprites.shipSize.height() / 2)); //set shipbullet Y after first click
                            }
                            shipbullet.x += (s_width - allsprites.shipSize.width()) / 35; //constantly move shipbullet after first click
                            //enemy1bullet
                            enemy1bullet.x -= s_width / 90; //the velocity is constant no matter position. width-shipPosition/50 not constant
                            //enemy2bullet
                            enemy2bullet.x -= s_width / 90;
                            //check if bullet hit enemy or meteors
                            checkHitEnemies();

                            //check if enemies bullet hit the ship
                            chekShipHit();


                            //check if ship bullet is out of screen
                            if (shipbullet.x > (s_width + allsprites.shipBulletSprite.width()) ) {
                                resetShipBullet();
                            }
                            if (enemy1bullet.x < (0-allsprites.enemybulletSprite.width())){
                                resetEnemy1Bullet();
                            }
                            if (enemy2bullet.x < (0-allsprites.enemybulletSprite.width())){
                                resetEnemy2Bullet();
                            }

                            //Respawn enemies every 5 seconds
                            TimerTask n =new TimerTask() {
                                @Override
                                public void run() {

                                    while(true) {

                                        try {
                                            Thread.sleep(5000);  //stop Thread for 5 seconds
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        moveEnemy();
                                        moveEnemy2();
                                    }
                                }
                            };
                            delay.schedule(n, 0);

                        }
                        holder.unlockCanvasAndPost(c);



                        loc = ((loc + 1) % 6);
                        eLoc = (eLoc + 1) % 4;
                        e1Loc = (e1Loc + 1) % 6;
                        e2Loc = (e2Loc + 1) % 6;
                        m1Loc = (m1Loc + 1) % 4;
                        m2Loc = (m2Loc + 1) % 4;
                        m3Loc = (m3Loc + 1) % 4;


                        lastUpdate = System.currentTimeMillis();


                    }
                    gameDone();
                }
            }
        };

        //Reseting shipBullet position
        public void resetShipBullet()
        {
            soundPool.play(soundMap.get(SHIP_SHOT), 0.7f, 0.7f, 1, 0, 1f);
            shipbullet.y = (int) yTouch + allsprites.shipSize.height()/2;
            shipbullet.x =   allsprites.shipSize.width();
        }

        //Reseting enemi1Bullet position
        public void resetEnemy1Bullet()
        {
            enemy1bullet.y = enemy1.y + allsprites.enemySize.height()/2;
            enemy1bullet.x =   enemy1.x - allsprites.enemybulletSprite.width();
        }

        //Reseting enemi2Bullet position
        public void resetEnemy2Bullet()
        {
            enemy2bullet.y = enemy2.y + allsprites.enemySize.height()/2;
            enemy2bullet.x =   enemy2.x - allsprites.enemybulletSprite.width();
        }
        //check if shipBullet hit enemies
        public void checkHitEnemies()
        {
            //check bullet for enemy 1
            if(shipbullet.x > enemy1.x && shipbullet.x < enemy1.x+allsprites.enemySize.width() &&
                    shipbullet.y > enemy1.y && shipbullet.y < enemy1.y+allsprites.enemySize.height() )
            {
                vib.vibrate(1000);//vibration for 1 sec
                win++;
                soundPool.play(soundMap.get(ENEMY_BOOM), 1, 1, 1, 0, 1f);
                exp = true;
                loc=0;
                explosion.x=enemy1.x;
                explosion.y=enemy1.y;
                moveEnemy();
                resetShipBullet();
            }
            //check bullt for enemy 2
            else if (shipbullet.x > enemy2.x && shipbullet.x < enemy2.x+allsprites.enemySize.width() &&
                    shipbullet.y > enemy2.y && shipbullet.y < enemy2.y+allsprites.enemySize.height() )
            {
                vib.vibrate(1000);//vibration for 1 sec
                win++;
                soundPool.play(soundMap.get(ENEMY_BOOM), 1, 1, 1, 0, 1f);
                exp = true;
                loc=0;
                explosion.x = enemy2.x;
                explosion.y = enemy2.y;
                moveEnemy2();
                resetShipBullet();
             //make sure to draw whole explosion
            }else if(loc==5){//make sure to draw the whole explosion
                exp = false;
            }
        }

        public void chekShipHit() //check if enemies bullets hit the ship
        {
            if(enemy1bullet.x < allsprites.shipSize.width() && enemy1bullet.x > 0 && enemy1bullet.y > yTouch &&
                    enemy1bullet.y < yTouch + allsprites.shipSize.height())
            {
                vib.vibrate(1000);//vibration for 1 sec
                lose++;
                soundPool.play(soundMap.get(SPACE_BOOM), 1, 1, 1, 0, 1f);
                exp = true;
                ship = false;
                loc=0;
                explosion.x = 0;
                explosion.y = (int) yTouch;
                resetEnemy1Bullet();
            }
            else if(enemy2bullet.x < allsprites.shipSize.width() && enemy2bullet.x > 0 && enemy2bullet.y > yTouch &&
                    enemy2bullet.y < yTouch + allsprites.shipSize.height())
            {
                vib.vibrate(1000);//vibration for 1 sec
                lose++;
                soundPool.play(soundMap.get(SPACE_BOOM), 1, 1, 1, 0, 1f);
                exp = true;
                ship = false;
                loc=0;
                explosion.x = 0;
                explosion.y = (int) yTouch;
                resetEnemy2Bullet();
            }
            else if (loc==5){
                exp=false;
                ship=true;
            }

        }

        public void moveEnemy()//set a new random location
        {

           Random rand = new Random();
            int finalX1 = rand.nextInt( (int)maxX - (int)minX) + (int)minX;
            int finalY1 = rand.nextInt((int) maxY);
            enemy1.x = finalX1;
            enemy1.y = finalY1;
            moved = true;

        }
        public void moveEnemy2()//set a new random location
        {

            Random rand = new Random();
            int finalX2 = rand.nextInt( (int)maxX - (int)minX) + (int)minX;
            int finalY2 = rand.nextInt((int) maxY);
            enemy2.x = finalX2;
            enemy2.y = finalY2;

        }
        public void startGame()
        {
            gameThread.start();
        }
        //gameOver
        public void gameDone(){
            if(lose == 5) {//check if it is a lost

                end = new Intent(TheGameLevel1X.this,MainActivity.class);
                end.putExtra("loses",lose);
                end.putExtra("min",min);
                end.putExtra("sec",secs);
                startActivity(end);
                change = false;
                //clean the surface and show the menu by removing fullscreen
            }else if (win == 10){//check if you won
                end = new Intent(TheGameLevel1X.this,MainActivity.class);
                end.putExtra("wins",win);
                end.putExtra("min",min);
                end.putExtra("sec",secs);
                end.putExtra("lives",(5-lose));
                startActivity(end);
                change = false;
            }
        }
        // three methods for the surfaceview
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int pixelFormat, int width, int height) {
            s_width = width;
            s_height = height;
            minX = s_width* .6f;
            maxX = (s_width * .9f) - allsprites.enemySize.width();
            minY = 0;
            maxY = s_height - allsprites.enemySize.height();
            Random rand = new Random();

            enemy1.x =rand.nextInt( (int)maxX - (int)minX) + (int)minX;;
            enemy1.y = rand.nextInt((int) maxY);
            enemy2.x =(int) (s_width * .5f);
            enemy2.y = (int)(s_height * .6f);
            enemy1bullet.x = enemy1.x - allsprites.enemybulletSprite.width();
            enemy1bullet.y = enemy1.y + allsprites.enemySize.height()/2;
            enemy2bullet.x = enemy2.x - allsprites.enemybulletSprite.width();
            enemy2bullet.y = enemy2.y + allsprites.enemySize.height()/2;

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }




}
