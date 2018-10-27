package com.example.dsdatsme.nnewsapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class News {
    private String mTitle ;
    private String mSection;
    private String mAuthor;
    private String mDateTime;
    private String mUrl;

    public News(String mTitle, String mSection,String mAuthor, String mDateTime, String mUrl) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mAuthor = mAuthor;
        this.mDateTime = mDateTime;
        this.mUrl = mUrl;
    }

    public String getTitle() {return mTitle;}
    public String getSection() {return mSection;}
    public String getAuthor() {return "- "+mAuthor;}
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDateTime() throws ParseException {
        // ISO date format by Gurdian API
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = formatter.parse(mDateTime.replaceAll("Z$", "+0000"));
        // Custom format for displaying
        SimpleDateFormat fmtOut = new SimpleDateFormat("MMMM dd, yyyy  HH:mm");

        return fmtOut.format(date);

    }
    public String getUrl() {return mUrl;}
}