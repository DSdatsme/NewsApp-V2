package com.example.dsdatsme.nnewsapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, @NonNull List<News> news) {
        super(context, 0, news);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item,parent,false);
        }
        News currentNews = getItem(position);

        TextView section = listItemView.findViewById(R.id.section);
        section.setText(currentNews.getSection());

        TextView title =  listItemView.findViewById(R.id.title);
        title.setText(currentNews.getTitle());

        TextView author = listItemView.findViewById(R.id.author);
        author.setText(currentNews.getAuthor());

        TextView date = listItemView.findViewById(R.id.date);
        try {
            date.setText(currentNews.getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return listItemView;
    }
}