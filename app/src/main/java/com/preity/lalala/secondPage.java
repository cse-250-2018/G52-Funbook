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
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;

@SuppressWarnings("ALL")
public class secondPage extends AppCompatActivity {
    static Uri bg;
    float xDown_boy=0, yDown_boy=0,xDown_girl=0, yDown_girl=0,xDown_cat=0, yDown_cat=0,xDown_dog=0, yDown_dog=0;
    ImageView boy;
    ImageView girl;
    ImageView cat;
    ImageView dog;

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
        setBG();


        boy.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown_boy=event.getX();
                        yDown_boy=event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX, moveY;
                        moveX=event.getX();
                        moveY=event.getY();

                        float distanceX=moveX-xDown_boy;
                        float distanceY=moveY-yDown_boy;
                        boy.setX(boy.getX()+distanceX);
                        boy.setY(boy.getY()+distanceY);
                        break;
                }
                return true;
            }
        });


        girl.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown_girl=event.getX();
                        yDown_girl=event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX, moveY;
                        moveX=event.getX();
                        moveY=event.getY();

                        float distanceX=moveX-xDown_girl;
                        float distanceY=moveY-yDown_girl;
                        girl.setX(girl.getX()+distanceX);
                        girl.setY(girl.getY()+distanceY);
                        break;
                }
                return true;
            }
        });

        cat.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown_cat=event.getX();
                        yDown_cat=event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX, moveY;
                        moveX=event.getX();
                        moveY=event.getY();

                        float distanceX=moveX-xDown_cat;
                        float distanceY=moveY-yDown_cat;
                        cat.setX(cat.getX()+distanceX);
                        cat.setY(cat.getY()+distanceY);
                        break;
                }
                return true;
            }
        });

        dog.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown_dog=event.getX();
                        yDown_dog=event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX, moveY;
                        moveX=event.getX();
                        moveY=event.getY();

                        float distanceX=moveX-xDown_dog;
                        float distanceY=moveY-yDown_dog;
                        dog.setX(dog.getX()+distanceX);
                        dog.setY(dog.getY()+distanceY);
                        break;
                }
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
}