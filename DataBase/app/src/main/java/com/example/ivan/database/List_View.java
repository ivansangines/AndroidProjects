package com.example.ivan.database;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.color.holo_blue_bright;
import static android.R.color.holo_blue_dark;
import static android.R.color.holo_green_dark;
import static android.R.color.holo_orange_dark;
import static android.R.color.holo_red_dark;
import static com.example.ivan.database.R.attr.colorPrimary;

/**
 * Created by ivans_000 on 4/25/2017.
 */

public class List_View extends BaseAdapter {
    Context context;
    Trabajador [] employer;

    public List_View (Context cnt, Trabajador [] empl){
        context = cnt;
        employer = empl;
    }
    @Override
    public int getCount() {
        return employer.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = null;

        if(view == null) {
            myView = inflater.inflate(R.layout.list_format, null, true);
        }else{
            myView = view;
        }

        TextView name = (TextView) myView.findViewById(R.id.name);
        TextView age = (TextView) myView.findViewById(R.id.age);
        TextView salary = (TextView) myView.findViewById(R.id.ammount);
        ImageView gender=(ImageView) myView.findViewById(R.id.gender);
        ImageView diet=(ImageView) myView.findViewById(R.id.diet);
        ImageView color=(ImageView) myView.findViewById(R.id.color);
        TextView food1 = (TextView) myView.findViewById(R.id.food);
        TextView food2 = (TextView) myView.findViewById(R.id.food2);


        name.setText(employer[position].get_name());
        age.setText("Age: " + employer[position].getAge());
        salary.setText("Salary: " + employer[position].getSalary());
        if(employer[position].getGender().equals("female")){
            gender.setImageResource(R.drawable.famale);
        }else{
            gender.setImageResource(R.drawable.male);
        }
        if(employer[position].getDiet().equals("vegetarian")){
            diet.setImageResource(R.drawable.vegetarian);
        }else  if(employer[position].getDiet().equals("poultry")){
            diet.setImageResource(R.drawable.poultry);
        }else{
            diet.setImageResource(R.drawable.redmeat);
        }
        if(employer[position].getColor().equals("orange")){
            //color.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            color.setBackgroundColor(0xffff6600);
        }else if (employer[position].getColor().equals("red")){
            //color.setColorFilter(holo_red_dark);
            color.setBackgroundColor(0xffff0000);
        }else if (employer[position].getColor().equals("yellow")){
            //color.setColorFilter(0xffffff00);
            color.setBackgroundColor(0xffffff00);
        }else if (employer[position].getColor().equals("blue")){
            //ColorFilter filter = new LightingColorFilter( Color.BLUE, Color.BLUE );
           // color.setColorFilter(holo_blue_dark);
            color.setBackgroundColor(0xff0000ff);
        }else{
            //color.setColorFilter(holo_green_dark);
            color.setBackgroundColor(0xff00c000);
        }
        food1.setText(employer[position].getFood1());
        food2.setText(employer[position].getFood2());

        return myView;
    }
}
