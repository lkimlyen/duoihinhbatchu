package com.example.yenyen.duoihinhbatchudemo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * Created by yenyen on 6/16/2017.
 */

public class CustomDialogBXH extends DialogFragment{
   MyAdapterBXH adapterBXH;

    public void setAdapterBXH(MyAdapterBXH adapterBXH) {
        this.adapterBXH = adapterBXH;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.layout_dialog_goi_y);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ListView listView = (ListView) dialog.findViewById(R.id.dsUser);
        listView.setAdapter(adapterBXH);
        return dialog;
    }
}
