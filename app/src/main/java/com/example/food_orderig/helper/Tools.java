package com.example.food_orderig.helper;

import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class Tools {
    public static Integer convertToPrice(String value){
        return Integer.valueOf(value.replace(".","")
                .replace(",","")
                .replace(" ",""));
    }
    public static String getForamtPrice(String price){
        Double number = Double.valueOf(price);
        DecimalFormat dec = new DecimalFormat("###,###,###");
        dec.setMinimumFractionDigits(0);
        return dec.format(number);
    }

    public static Date getDate(int dayAgo){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR,dayAgo);
        return c.getTime();
    }

    public static String getCurrentDate(){
        PersianCalendar now = new PersianCalendar();
        String currentDate = now.getPersianShortDate();
        return currentDate;
    }

    public static String getDayAgo(int dayAgo){
        PersianCalendar now = new PersianCalendar();
        now.add(PersianCalendar.DAY_OF_YEAR , dayAgo);
        String currentDate = now.getPersianShortDate();
        return currentDate;
    }

    public static String getSevenDayAgo(int dayAgo){
        PersianCalendar now = new PersianCalendar();
        now.add(PersianCalendar.DAY_OF_YEAR , dayAgo);
        String currentDate = now.getPersianShortDate();
        return currentDate;
    }

    public static String getDayName(){
        PersianCalendar now = new PersianCalendar();
        String currentDate = now.getPersianWeekDayName();
        return currentDate;
    }
    public static String getMonthName(){
        PersianCalendar now = new PersianCalendar();
        String monthName = now.getPersianMonthName();
        return monthName;
    }


    public static String dayAgo(){
        PersianCalendar now = new PersianCalendar();
        String dayName = now.getPersianWeekDayName();
        String date = "";

        switch (dayName){
            case "شنبه" :
                date = getCurrentDate();
                break;
            case "یکشنبه" :
                date = getSevenDayAgo(-1);
                break;
            case "دوشنبه" :
                date = getSevenDayAgo(-2);
                break;
            case "سه شنبه" :
                date = getSevenDayAgo(-3);
                break;
            case "چهارشنبه" :
                date = getSevenDayAgo(-4);
                break;
            case "پنج شنبه" :
                date = getSevenDayAgo(-5);
                break;
            case "جمعه" :
                date = getSevenDayAgo(-6);
                break;
            default:
//                throw new IllegalStateException("Unexpected value: " + dayName);
        }
        return date ;
    }

    public static String getThirtyDaysAgo(){
        PersianCalendar now = new PersianCalendar();
        int day = now.getPersianDay();
        int thirtyAgo = (day - ((day * 2)-1)) ;
        String date = getDayAgo(thirtyAgo);
        return date;
    }

    public static byte[] getBytes(Uri uri)  {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        try {
            FileInputStream file = new FileInputStream(uri.getPath());
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = file.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return byteBuffer.toByteArray();
    }

    public static String saveFile(byte[] data, File DESTINY_DIR, String fileName) {
        if (!DESTINY_DIR.exists()) DESTINY_DIR.mkdirs();

        File mainPicture = new File(DESTINY_DIR, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(mainPicture);
            fos.write(data);
            fos.close();
            return mainPicture.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
