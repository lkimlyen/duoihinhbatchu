package com.example.yenyen.duoihinhbatchudemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class ResultActivity extends BaseActivity {
    TextView textView, tvCauHoi;
    ImageView imageView, ivAvatar,ivAvatarKhung, ivCoinIcon,ivPictureBorder,ivDolaIcon;
    Button btChoiTiep, btShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        anhXa();
        setImageView();

        tvCauHoi.setText(getIntent().getStringExtra("cauhoi"));
        textView.setText(getIntent().getStringExtra("kq"));

       setBtChoiTiep();

    }

    private void setBtChoiTiep() {
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
                    finish();
                }
            }
        });
    }

    private void anhXa() {
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        ivAvatarKhung = (ImageView) findViewById(R.id.ivAvatarKhung);
        ivDolaIcon = (ImageView) findViewById(R.id.ivDolaIcon);
        ivPictureBorder = (ImageView) findViewById(R.id.ivPictureBorder);
        ivCoinIcon = (ImageView) findViewById(R.id.ivCoinIcon);
        textView = (TextView) findViewById(R.id.tvKetQua);
        tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
        imageView = (ImageView) findViewById(R.id.ivImageCH);
        btChoiTiep = (Button) findViewById(R.id.btChoiTiep);
        btShare = (Button) findViewById(R.id.btShare);
    }

    public void setBtShare() {
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogGoiY dialogGoiY =  new CustomDialogGoiY();
                dialogGoiY.setCancelable(false);
                dialogGoiY.show(getFragmentManager(), "dmn");
                dialogGoiY.setTieude("Thông báo");
                dialogGoiY.setGoiy("Bạn vui lòng đăng nhập để sử dụng chức năng này");
            }
        });
    }

    public void setImageView() {
        try {
            InputStream inputStream = getAssets().open(getIntent().getStringExtra("image"));
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ivPictureBorder.setImageResource(R.drawable.pictureborder);
        ivCoinIcon.setImageResource(R.drawable.coinicon);
        ivAvatarKhung.setImageResource(R.drawable.avataricon);
        ivDolaIcon.setImageResource(R.drawable.dolaicon);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.avatar);
        Bitmap iconcrop  = getCroppedBitmap(icon);
        ivAvatar.setImageBitmap(iconcrop);
    }
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
