package com.example.yenyen.duoihinhbatchudemo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;

/**
 * Created by yenyen on 6/20/2017.
 */
public class CustomDialogMenu extends DialogFragment {
    String name;
    int money, score, luotchoi;
    ShareDialog shareDialog;
    Context context;
    ArrayList<User> users;

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }


    public void setLuotchoi(int luotchoi) {
        this.luotchoi = luotchoi;
    }

    public void setShareDialog(ShareDialog shareDialog) {
        this.shareDialog = shareDialog;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    View rootView;

    public void setImage(String image) {
        this.image = image;
    }

    String image;

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.layout_custom_dialog_menu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.btQuayLai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.findViewById(R.id.btHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                startActivity(intent);
                dismiss();
            }
        });
        dialog.findViewById(R.id.btProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogUser dialogUser = new CustomDialogUser();
                dialogUser.setCancelable(false);
                dialogUser.show(getFragmentManager(), "abc");
                dialogUser.setName(name);
                dialogUser.setImage(image);
                dialogUser.setMoney(money);
                dialogUser.setScore(score);
                dialogUser.setRootView(rootView);
                dialogUser.setContext(context);
                dialogUser.setShareDialog(shareDialog);
                dialogUser.setLuotchoi(luotchoi);
                dismiss();
            }
        });
        dialog.findViewById(R.id.btBXH).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogBXH dialogBXH = new CustomDialogBXH();
                dialogBXH.setCancelable(false);
                dialogBXH.show(getFragmentManager(), "abc");
                dialogBXH.setUsers(users);
                dialogBXH.setContext(context);
                dismiss();
            }
        });
        return dialog;
    }
}
