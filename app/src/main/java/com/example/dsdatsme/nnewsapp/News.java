package com.example.dsdatsme.nnewsapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

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
    public String getDateTime() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        TemporalAccessor accessor = timeFormatter.parse(mDateTime);
        return DateTimeFormatter.ofPattern("MMMM dd, yyyy  HH:mm:ss", Locale.ENGLISH).format(accessor);
    }
    public String getUrl() {return mUrl;}
}