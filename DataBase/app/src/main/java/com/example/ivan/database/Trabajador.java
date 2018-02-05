package com.example.ivan.database;

/**
 * Created by ivans_000 on 4/25/2017.
 */

public class Trabajador {
    private String fname, lname, gender, diet, color, food1, food2;
    private int age, salary;

    public Trabajador(){
        fname = "";
        lname = "";
        age = 0;
        salary = 0;
        gender = "";
        diet = "";
        color = "";
        food1 = "";
        food2 = "";
    }

    public Trabajador (String nombre, String apellido, int edad, int salario,
                       String genero, String dieta, String col, String comida1, String comida2){
        fname = nombre;
        lname = apellido;
        age = edad;
        salary = salario;
        gender = genero;
        diet = dieta;
        color = col;
        food1 = comida1;
        food2 = comida2;
    }


    public String get_name() {
        return fname+" "+lname;
    }

    public String getGender() {
        return gender;
    }

    public String getDiet() {
        return diet;
    }

    public String getColor() {
        return color;
    }

    public String getFood1() {
        return food1;
    }

    public String getFood2() {
        return food2;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFood1(String food1) {
        this.food1 = food1;
    }

    public void setFood2(String food2) {
        this.food2 = food2;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
