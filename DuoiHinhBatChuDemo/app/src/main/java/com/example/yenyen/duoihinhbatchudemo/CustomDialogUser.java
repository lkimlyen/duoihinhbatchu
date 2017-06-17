package com.example.yenyen.duoihinhbatchudemo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yenyen on 6/16/2017.
 */

public class CustomDialogUser extends DialogFragment {
    String name;
    int money, score;

    public void setImage(String image) {
        this.image = image;
    }

    String image;
    private int counter = 0;

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
        dialog.setContentView(R.layout.layout_profile_user);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.btClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.findViewById(R.id.btChiaSe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //postPicture();
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
        return dialog;
//    }
//    public void postPicture() {
//        //check counter
//        if(counter == 0) {
//            //save the screenshot
//            View rootView = findViewById(android.R.id.content).getRootView();
//            rootView.setDrawingCacheEnabled(true);
//            // creates immutable clone of image
//            image = Bitmap.createBitmap(rootView.getDrawingCache());
//            // destroy
//            rootView.destroyDrawingCache();
//
//            //share dialog
//            AlertDialog.Builder shareDialog = new AlertDialog.Builder(getActivity());
//            shareDialog.setTitle("Share Screen Shot");
//            shareDialog.setMessage("Share image to Facebook?");
//            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    //share the image to Facebook
//                    SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
//                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
//                    shareButton.setShareContent(content);
//                    counter = 1;
//                    shareButton.performClick();
//                }
//            });
//            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            shareDialog.show();
//        }
//        else {
//            counter = 0;
//            shareButton.setShareContent(null);
//        }
    }
}
