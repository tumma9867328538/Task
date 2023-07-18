package com.tumma.roomdatabasemvvmcrud.Utils.common;

import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonMethods {
    public static String getweekDay(int date, int month, int year) {  // sun, mon .....
        SimpleDateFormat inFormat = new SimpleDateFormat("dd MM yyyy");
        String dayName = "";
        try {
            Date myDate = inFormat.parse(date + " " + month + " " + year);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
            dayName = simpleDateFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayName;
    }

    public static void snackBar(String message, View parent) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }

    public static int getDayscount(int yearSelected,int monthSelected){ //  total month days count..
        Calendar cal1 = Calendar.getInstance();
        // Note: 0-based months
        cal1.set(yearSelected, monthSelected - 1, 1);
        int daysInMonth = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
        return daysInMonth;
    }
}
