package com.example.ivan.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    boolean delete;
    SQL myDataBase;
    SQLiteDatabase sql;
    TextView tittle;
    ListView list;
    List_View my_list;
    Vector values;
    AssetManager assets;
    int age,salary;
    String color,diet,food,food2,gender;
    Cursor cursor;
    SharedPreferences shr_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        myDataBase = new SQL(this);
        sql = myDataBase.getReadableDatabase();
        shr_pref = getSharedPreferences("created", Context.MODE_PRIVATE);



        tittle = (TextView) findViewById(R.id.textView);
        list = (ListView) findViewById(R.id.Costumers);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.heightPixels;

        FrameLayout.LayoutParams txt = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        txt.setMargins(0,(int) (height*0.01),0,0);
        txt.gravity = Gravity.CENTER_HORIZONTAL;
        tittle.setLayoutParams(txt);

        FrameLayout.LayoutParams lista = new FrameLayout.LayoutParams(width,(int) (height*0.85));
        lista.setMargins(0,(int) (height*0.05),0,0);
        list.setLayoutParams(lista);

       try {
           if (!shr_pref.getBoolean("tabla", false)){
               createTable();
               Toast.makeText(MainActivity.this, "Done loading", Toast.LENGTH_SHORT).show();
           }else{
               Toast.makeText(MainActivity.this, "DataBase is ready", Toast.LENGTH_SHORT).show();
           }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createTable () throws IOException {
        values = new Vector();
        assets = MainActivity.this.getResources().getAssets();
        String [] sep_name;

        InputStreamReader [] files = {new InputStreamReader(assets.open("names.txt")), new InputStreamReader(assets.open("age.txt")),
                new InputStreamReader(assets.open("salary.txt")), new InputStreamReader(assets.open("gender.txt")),
                new InputStreamReader(assets.open("diet.txt")), new InputStreamReader(assets.open("colors.txt")),
                new InputStreamReader(assets.open("food.txt")), new InputStreamReader(assets.open("food2.txt"))};

        BufferedReader name_read = new BufferedReader(files[0]);
        BufferedReader age_read = new BufferedReader(files[1]);
        BufferedReader salary_read = new BufferedReader(files[2]);
        BufferedReader gender_read = new BufferedReader(files[3]);
        BufferedReader diet_read = new BufferedReader(files[4]);
        BufferedReader color_read = new BufferedReader(files[5]);
        BufferedReader food_read = new BufferedReader(files[6]);
        BufferedReader food2_read = new BufferedReader(files[7]);

        for(int i=0; i<100; i++){
            sep_name = name_read.readLine().split(" ");
            age = Integer.parseInt(age_read.readLine());
            salary = Integer.parseInt(salary_read.readLine());
            gender = gender_read.readLine();
            diet = diet_read.readLine();
            color = color_read.readLine();
            food = food_read.readLine();
            food2 = food2_read.readLine();


            myDataBase.insertData(sep_name[0],sep_name[1],age,salary,gender,diet,color,food,food2);
        }
        for(int i=0; i<files.length; i++){
            files[i].close();
        }
        SharedPreferences.Editor edit = shr_pref.edit();
        edit.putBoolean("tabla",true);
        edit.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create a menu with all the options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options,menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item){


       switch (item.getItemId()){
           case R.id.everyone:
               cursor = sql.rawQuery("SELECT * FROM people", null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.op1: //like chicken
               cursor=sql.query("people", null,"first_food='chicken' OR second_food='chicken'",null,null,null,null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.op2: //like goat
               cursor=sql.query("people", null,"first_food='goat' OR second_food='goat'",null,null,null,null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.op3: //less than 60k
               cursor=sql.query("people", null,"salary<60000",null,null,null,null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.op4: //younger than 38
               cursor=sql.query("people", null,"age<38",null,null,null,null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.op5: //female like blue
               cursor=sql.query("people", null,"gender='female' AND color='blue'",null,null,null,null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.c1: //salary bigger than 70k
               cursor = sql.rawQuery("SELECT * FROM people WHERE salary>70000", null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.c2: //older than 40 with poultry diet
               cursor = sql.rawQuery("SELECT * FROM people WHERE age>40 AND diet_type='poultry'", null);
               //cursor=sql.query("people", null,"age>40 AND diet='poultry'",null,null,null,null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.c3: //like turkey 1st and under 40
               cursor = sql.rawQuery("SELECT * FROM people WHERE (age<40 AND first_food='turkey')", null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.c4: //male and are red meat lovers
               cursor = sql.rawQuery("SELECT * FROM people WHERE gender='male' AND diet_type='redMeat'", null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.c5: //vegetarian females and 1st/2nd food tofu
               cursor = sql.rawQuery("SELECT * FROM people WHERE gender='female' AND diet_type='vegetarian' AND (first_food='tofu' OR second_food='tofu')", null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.r1: //lname starts with M/O color blue
               cursor = sql.rawQuery("SELECT * FROM people WHERE color='blue' AND (lname LIKE 'M%' OR lname LIKE 'O%')", null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.r2: //male and are red meat lovers
               cursor = sql.rawQuery("SELECT * FROM people WHERE age>30 AND (gender='female'  AND diet_type='redMeat'AND color='yellow') OR " +
                       "(gender='male' AND color='blue' AND diet_type='poultry')", null);
               tittle.setText(""+cursor.getCount());
               break;
           case R.id.delete:
               delete = true;
               //shr_pref.edit().remove("created").commit();
               shr_pref.edit().putBoolean("tabla",false).commit();
               /*shr_pref = getSharedPreferences("created", Context.MODE_PRIVATE);
               shr_pref.edit().clear().commit();*/
               MainActivity.this.deleteDatabase("Employee.db");

               break;


           default:
               return super.onOptionsItemSelected(item);
       }
       if(delete!=true) {
           Trabajador[] employer = new Trabajador[cursor.getCount()];
           cursor.moveToFirst();
           for (int i = 0; i < cursor.getCount(); i++) {
               employer[i] = new Trabajador(cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),
                       cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
               cursor.moveToNext();
           }
           my_list = new List_View(MainActivity.this, employer);
           list.setAdapter(my_list);
       }else{
           delete = false;
           Trabajador [] empl = new Trabajador[0];
           my_list = new List_View(MainActivity.this, empl);
           list.setAdapter(my_list);
           tittle.setText("");
           Toast.makeText(MainActivity.this, "DatatBase is deleted", Toast.LENGTH_SHORT).show();
       }


        return true;
    }


}
