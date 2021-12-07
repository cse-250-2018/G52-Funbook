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
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;

@SuppressWarnings("ALL")
public class secondPage extends AppCompatActivity {
    static Uri bg;
    float xDown_boy=0, yDown_boy=0,xDown_girl=0, yDown_girl=0,xDown_cat=0, yDown_cat=0,xDown_dog=0, yDown_dog=0,xDown=0,yDown=0;
    ImageView boy;
    ImageView girl;
    ImageView cat;
    ImageView dog;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    private ImageView imageView;

    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        layout= findViewById(R.id.lay);
        boy = findViewById(R.id.boy);
        girl = findViewById(R.id.girl);
        cat = findViewById(R.id.cat);
        dog = findViewById(R.id.dog);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        setBG();

        setListener(boy);
        setListener(girl);
        setListener(cat);
        setListener(dog);
    }

    private void setListener(ImageView img) {
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageView=img;
                if(event.getPointerCount() <2)
                {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            xDown=event.getX();
                            yDown=event.getY();

                            break;
                        case MotionEvent.ACTION_MOVE:
                            float moveX, moveY;
                            moveX=event.getX();
                            moveY=event.getY();

                            float distanceX=moveX-xDown;
                            float distanceY=moveY-yDown;
                            imageView.setX(imageView.getX()+distanceX);
                            imageView.setY(imageView.getY()+distanceY);
                            break;
                    }
                    return true;
                }

                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });

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

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.7f, Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);

            return true;
        }
    }

}