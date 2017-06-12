package com.example.yenyen.duoihinhbatchudemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yenyen on 5/25/2017.
 */

public class MyAdapter extends BaseAdapter{
    ArrayList<CauHoi> ds;
    Context c;

    public MyAdapter(Context c) {
        this.c = c;
    }

    public MyAdapter(Context c, ArrayList<CauHoi> ds) {
        this.ds = ds;
        this.c = c;
    }

    public static class View_Mot_O {
        ImageView iv;
        TextView tv;

    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int position) {
        return ds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View_Mot_O mot_o;
        LayoutInflater inf = ((Activity) c).getLayoutInflater();

        if (convertView == null) {
            mot_o = new View_Mot_O();
            convertView = inf.inflate(R.layout.layout_item_choose1, null);

            mot_o.iv = (ImageView) convertView.findViewById(R.id.ivTileHover);
            mot_o.tv = (TextView) convertView.findViewById(R.id.tvKyTu);

            convertView.setTag(mot_o);
        } else {
            mot_o = (View_Mot_O) convertView.getTag();
        }

       // mot_o.iv.setImageResource(ds.get(position).hinh);
        //mot_o.tv.setText(ds.get(position).ten);

/*        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, position + "", Toast.LENGTH_SHORT).show();
            }
        });*/

        mot_o.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(c, "click hinh " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;

    }
}
