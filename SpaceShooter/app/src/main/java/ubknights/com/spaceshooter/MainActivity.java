package ubknights.com.spaceshooter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends Activity {
    Intent results;
    public static MediaPlayer finish;
    public static MediaPlayer succeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finish = MediaPlayer.create(this,R.raw.gameover);
        succeed = MediaPlayer.create(this,R.raw.success);
        results = getIntent();

        if(((results.getIntExtra("loses",0))==5) || (results.getBooleanExtra("loses",false)==true)){//Check if we lost
            finish.start();
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setTitle("Game Over, You lost ");
            msg.setMessage("time played: "+String.format("%d:%02d",results.getIntExtra("min",0),results.getIntExtra("sec",0)));
            msg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish.release();

                }
            });
            AlertDialog done = msg.create();
            done.show();

        }else if((results.getIntExtra("wins",0) == 10) || (results.getIntExtra("kills",0)==10)){//Check if we won
            succeed.start();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Good Job, You won!! ");
            if(results.getIntExtra("wins",0) == 10){ //Intent coming from game1
                alert.setMessage("time played: "+String.format("%d:%02d",results.getIntExtra("min",0),results.getIntExtra("sec",0))
                +"\n You had: "+results.getIntExtra("lives",0)+" live(s) left");
            }else{//intent coming from game2
                alert.setMessage("time played: "+String.format("%d:%02d",results.getIntExtra("min",0),results.getIntExtra("sec",0)));
            }
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    succeed.release();

                }
            });
            AlertDialog text = alert.create();
            text.show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create a menu with two options: play round 1 or play round two
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE,0,Menu.NONE,"Play round 1");
        menu.add(Menu.NONE,1,Menu.NONE,"Play round 2");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                //start round1
               Intent t = new Intent(MainActivity.this, TheGameLevel1X.class);
                startActivity(t);
                return true;
            case 1:
                Intent a = new Intent(MainActivity.this, TheGameLevel2.class);
                startActivity(a);
                return true;
                //start round2
            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
