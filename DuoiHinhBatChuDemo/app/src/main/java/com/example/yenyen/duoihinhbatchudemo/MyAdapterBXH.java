package com.example.yenyen.duoihinhbatchudemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yenyen on 6/16/2017.
 */

public class MyAdapterBXH extends ArrayAdapter<User> implements Serializable{
    ArrayList<User> users;
    Context context;
    int layout;

    public MyAdapterBXH(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.users = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layout, null);
        TextView name = (TextView) convertView.findViewById(R.id.tvName);
        name.setText(users.get(position).name);
        TextView score = (TextView) convertView.findViewById(R.id.tvScore);
        score.setText(String.valueOf(users.get(position).score));
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivAvatar);
        new ImageLoadTask(users.get(position).image,imageView).execute();
        return convertView;
    }
}
