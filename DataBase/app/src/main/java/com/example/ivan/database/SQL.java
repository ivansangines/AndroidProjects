package com.example.ivan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SQL extends SQLiteOpenHelper {
    //db name
    final static String DB_NAME = "Employee.db";
    //table name
    final static String TABLE_NAME = "people";
    //version
    final static int DB_VERSION = 1;
    //columns
    final static String COLUMN1 = "ID";
    final static String COLUMN2 = "fname";
    final static String COLUMN3 = "lname";
    final static String COLUMN4 = "age";
    final static String COLUMN5 = "salary";
    final static String COLUMN6 = "gender";
    final static String COLUMN7 = "diet_type";
    final static String COLUMN8 = "color";
    final static String COLUMN9 = "first_food";
    final static String COLUMN10 = "second_food";


    public SQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("
                + COLUMN1 + " INTEGER PRIMARY KEY, "
                + COLUMN2 + " TEXT NOT NULL, "
                + COLUMN3 + " TEXT NOT NULL, "
                + COLUMN4 + " INT NOT NULL, "
                + COLUMN5 + " INT NOT NULL, "
                + COLUMN6 + " TEXT NOT NULL, "
                + COLUMN7 + " TEXT NOT NULL, "
                + COLUMN8 + " TEXT NOT NULL, "
                + COLUMN9 + " TEXT NOT NULL, "
                + COLUMN10 + " TEXT NOT NULL) "

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData (String fname, String lname, int age, int salary, String gender,
                               String diet, String color, String food1, String food2 ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues param = new ContentValues();

        param.put(COLUMN2,fname);
        param.put(COLUMN3,lname);
        param.put(COLUMN4,age);
        param.put(COLUMN5,salary);
        param.put(COLUMN6,gender);
        param.put(COLUMN7,diet);
        param.put(COLUMN8,color);
        param.put(COLUMN9,food1);
        param.put(COLUMN10,food2);
        long check = db.insert(TABLE_NAME,null,param);
        if(check == -1){
            return false;
        }else{

            return true;}

    }


}
