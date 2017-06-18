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

public class MyAdapterBXH extends ArrayAdapter<User> implements Serializable {
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
        new ImageLoadTask(users.get(position).image, imageView).execute();
        ImageView hang = (ImageView) convertView.findViewById(R.id.ivHang);
        TextView tvHang = (TextView) convertView.findViewById(R.id.tvHang);
        if (position == 0) {
            hang.setImageResource(R.drawable.iconhang1);
            tvHang.setText("");
        } else if (position == 1) {
            hang.setImageResource(R.drawable.iconhang2);
            tvHang.setText("");
        } else if (position == 2) {
            hang.setImageResource(R.drawable.iconhang3);
            tvHang.setText("");
        }
        else {
            for (int i = 3; i < users.size();i++)
            {
                if (position >= i) {
                    tvHang.setText(String.valueOf(i+1));
                }
            }
        }

        return convertView;
    }
}
