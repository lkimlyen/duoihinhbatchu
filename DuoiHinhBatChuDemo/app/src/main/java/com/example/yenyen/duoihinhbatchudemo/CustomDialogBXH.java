package com.example.yenyen.duoihinhbatchudemo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by yenyen on 6/16/2017.
 */

public class CustomDialogBXH extends DialogFragment {

    MyAdapterBXH adapterBXH;
    Context context;
    ArrayList<User> users;

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.layout_bang_xep_hang);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.btClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ListView listView = (ListView) dialog.findViewById(R.id.dsUser);
        adapterBXH = new MyAdapterBXH(context, R.layout.layout_listview_item_bxh, users);
        listView.setAdapter(adapterBXH);
        ImageView ivIconBXH= (ImageView) dialog.findViewById(R.id.ivIconBXH);
        ivIconBXH.setImageResource(R.drawable.iconbangxephang);
        return dialog;
    }
}
