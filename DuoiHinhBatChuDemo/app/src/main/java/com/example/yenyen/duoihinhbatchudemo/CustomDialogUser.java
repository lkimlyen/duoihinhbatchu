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
import android.widget.TextView;

import com.facebook.share.widget.ShareDialog;

/**
 * Created by yenyen on 6/16/2017.
 */

public class CustomDialogUser extends DialogFragment {
    String name;
    int money, score;
    int luotchoi = 5;
    Context context;
    ShareDialog shareDialog;

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
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.layout_profile_user);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.btClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.findViewById(R.id.btDong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView name = (TextView) dialog.findViewById(R.id.tvName);
        name.setText(this.name);
        TextView score = (TextView) dialog.findViewById(R.id.tvScore);
        score.setText(String.valueOf(this.score));
        TextView money = (TextView) dialog.findViewById(R.id.tvMoney);
        money.setText(String.valueOf(this.money));
        ImageView imageView = (ImageView) dialog.findViewById(R.id.ivAvatar);
        new ImageLoadTask(image, imageView).execute();
        ImageView ivIconUser = (ImageView) dialog.findViewById(R.id.ivIconUser);
        ivIconUser.setImageResource(R.drawable.buttonusernormal);
        ImageView ivTim = (ImageView) dialog.findViewById(R.id.ivTim);
        ivTim.setImageResource(R.drawable.profilebtntim);
        ImageView ivDolaIcon = (ImageView) dialog.findViewById(R.id.ivDolaIcon);
        ivDolaIcon.setImageResource(R.drawable.dolaicon);
        ImageView ivIconTime = (ImageView) dialog.findViewById(R.id.ivIconTime);
        ivIconTime.setImageResource(R.drawable.profileicontime);
        ImageView ivProfileKhung = (ImageView) dialog.findViewById(R.id.ivProfileKhung);
        ivProfileKhung.setImageResource(R.drawable.profilekhung);
        ImageView ivAvatarKhung = (ImageView) dialog.findViewById(R.id.ivAvatarKhung);
        ivAvatarKhung.setImageResource(R.drawable.listfriendavatarholder);
        TextView tvLuotChoi = (TextView) dialog.findViewById(R.id.tvLuotChoi);
        tvLuotChoi.setText(String.valueOf(luotchoi));
        return dialog;
    }

}