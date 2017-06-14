package com.example.yenyen.duoihinhbatchudemo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class ResultActivity extends AppCompatActivity {
    TextView textView, tvCauHoi;
    ImageView imageView;
    Button btChoiTiep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView = (TextView) findViewById(R.id.tvKetQua);
        tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
        imageView = (ImageView) findViewById(R.id.ivImageCH);
        try {
            InputStream inputStream = getAssets().open(getIntent().getStringExtra("image"));
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tvCauHoi.setText(getIntent().getStringExtra("cauhoi"));

        textView.setText(getIntent().getStringExtra("kq"));

        btChoiTiep = (Button) findViewById(R.id.btChoiTiep);
        btChoiTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getIntent().getStringExtra("cauhoi")) == 10) {
                    CustomDialogGoiY dialogGoiY =  new CustomDialogGoiY();
                    dialogGoiY.setCancelable(false);
                    dialogGoiY.show(getFragmentManager(), "dmn");
                    dialogGoiY.setTieude("Thông báo");
                    dialogGoiY.setGoiy("Bạn vui lòng đăng nhập để chơi tiếp");
                } else {
                    Intent intent = new Intent(ResultActivity.this, PlayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
