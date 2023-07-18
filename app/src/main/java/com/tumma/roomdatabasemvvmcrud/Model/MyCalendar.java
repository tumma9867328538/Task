package com.tumma.roomdatabasemvvmcrud.Model;

public class MyCalendar {

    // storing day - like Sun, Wed etc..,
    // dates like 1, 2 etc..
    //Month 1..12

    private String day;
    private String date;

    public MyCalendar() {
    }

    public MyCalendar(String day, String date) {
        this.day = day;
        this.date = date;


    }

    public String getDay() {
        return day;
    }

    public void setDay(String date) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
