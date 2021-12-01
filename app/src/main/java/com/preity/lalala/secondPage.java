package com.preity.lalala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;

@SuppressWarnings("ALL")
public class secondPage extends AppCompatActivity {
    static Uri bg;

    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        layout= findViewById(R.id.lay);
        setBG();

    }

    private void setBG() {
        try{

            Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver() , bg);
            Drawable dr= new BitmapDrawable(bitmap);

            layout.setBackgroundDrawable(dr);
          //  Toast.makeText(this, bg.toString(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    protected static void setBGimg(Uri uri) {
        bg= uri;
    }
}