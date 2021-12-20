package com.preity.lalala;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class secondPage extends AppCompatActivity {
    static Uri bg;
    float xDown=0,yDown=0;

    private ImageView tmp;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    private ImageView imageView;
    Button addBoy, addGirl, addCat, addDog, addKidG, addKidB;
    Drawable draw,draw1,draw2,draw3,draw4,draw5;

    Button button2;
    LinearLayout layout;
    GridView scrollCharecters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        layout = findViewById(R.id.field);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        setBG();
        addBoy = findViewById(R.id.boy);
        addCat = findViewById(R.id.cat);
        addDog = findViewById(R.id.dog);
        addGirl = findViewById(R.id.girl);
        addKidB = findViewById(R.id.kidb);
        addKidG = findViewById(R.id.kidg);

        draw = getResources().getDrawable(R.drawable.boyy);
        draw1 = getResources().getDrawable(R.drawable.girly);
        draw2 = getResources().getDrawable(R.drawable.catty);
        draw3 = getResources().getDrawable(R.drawable.doggy);
        draw4 = getResources().getDrawable(R.drawable.kiddob);
        draw5 = getResources().getDrawable(R.drawable.kiddog);

        addBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = new ImageView(secondPage.this);
                addView(tmp,100,100);
                tmp.setImageDrawable(draw);
                setListener(tmp);

            }
        });
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = new ImageView(secondPage.this);
                addView(tmp,100,100);
                tmp.setImageDrawable(draw2);
                setListener(tmp);

            }
        });
        addDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = new ImageView(secondPage.this);
                addView(tmp,100,100);
                tmp.setImageDrawable(draw3);
                setListener(tmp);

            }
        });
        addGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = new ImageView(secondPage.this);
                addView(tmp,100,100);
                tmp.setImageDrawable(draw1);
                setListener(tmp);

            }
        });
        addKidB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = new ImageView(secondPage.this);
                addView(tmp,100,100);
                tmp.setImageDrawable(draw4);
                setListener(tmp);

            }
        });
        addKidG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = new ImageView(secondPage.this);
                addView(tmp,100,100);
                tmp.setImageDrawable(draw5);
                setListener(tmp);

            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.scrollCharecters).setVisibility(View.GONE);
                findViewById(R.id.button2).setVisibility(View.GONE);
            }
        });
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

    private void addView(ImageView boy, int i, int i1) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(i, i1);
        params.setMargins(0, 10, 0, 0);
        boy.setLayoutParams(params);
        layout.addView(boy);
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
            mScaleFactor = Math.max(0.000001f, Math.min(mScaleFactor, 2000000.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);

            return true;
        }
    }

}