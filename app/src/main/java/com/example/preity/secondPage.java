package com.example.preity;
import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hbisoft.hbrecorder.HBRecorder;
import com.hbisoft.hbrecorder.HBRecorderListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
public class secondPage extends AppCompatActivity implements HBRecorderListener  {
    private static final String TAG = "Recorder";
    private static final int SCREEN_RECORD_REQUEST_CODE = 100;
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 101;
    private static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = 102;
    private static final int PERMISSION_OK = 200;
    HBRecorder hbRecorder;
    boolean hasPermissions;
    ContentValues contentValues;
    ContentResolver resolver;
    Uri mUri;
    static Uri bg;
    float xDown=0,yDown=0;
    ImageView recordOn, recordStop;

    private ImageView tmp;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    private ImageView imageView;
    Button addBoy, addGirl, addCat, addDog, addKidG, addKidB;
    Drawable draw,draw1,draw2,draw3,draw4,draw5;

    Button button2;
    LinearLayout layout, layout_field;
    GridView scrollCharecters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        layout = findViewById(R.id.lay);
        hbRecorder = new HBRecorder(this, this);
        hbRecorder.setScreenDimensions(720,1280);
       // hbRecorder.setScreenDimensions(1280,720);
        hbRecorder.setVideoEncoder("H264");
        layout_field = findViewById(R.id.field);


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
                recordOn = findViewById(R.id.start_record);
                recordStop = findViewById(R.id.stop_record);
                LinearLayout tmp= findViewById(R.id.record_lay);
                tmp.setVisibility(View.VISIBLE);
                recordOn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       // getMicroPer();
                        if (getMicroPer() );
                        {
                            startRecordingScreen();
                        }
                    }
                });

                recordStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        hbRecorder.stopScreenRecording();
                    }
                });

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
        layout_field.addView(boy);
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
/*    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        hbRecorder.pauseScreenRecording();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        hbRecorder.resumeScreenRecording();
    }

*/


    @Override
    public void HBRecorderOnStart() {
        Log.d(TAG,"started");
   //     Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void HBRecorderOnComplete() {

        String s= hbRecorder.getFilePath();
        uploadInFirebase.setUri(s);
        startActivity(new Intent(getApplicationContext(), uploadInFirebase.class));
        Log.d(TAG, "filepath : "+ s);
     //   Toast.makeText(this, "Video saved in: "+ s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void HBRecorderOnError(int errorCode, String reason) {
       // Toast.makeText(this, errorCode+": "+reason, Toast.LENGTH_LONG).show();
        Log.d(TAG, "HBRecorderOnError: "+ reason);
    }
    private void startRecordingScreen() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent permissionIntent = mediaProjectionManager != null ? mediaProjectionManager.createScreenCaptureIntent() : null;
        startActivityForResult(permissionIntent, SCREEN_RECORD_REQUEST_CODE);
    }
    private Boolean getMicroPer()
    {
       // Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO},PERMISSION_OK);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_OK);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCREEN_RECORD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Start screen recording
                hbRecorder.startScreenRecording(data, resultCode, this);

            }
        }
    }


    //For Android 10> we will pass a Uri to HBRecorder
    //This is not necessary - You can still use getExternalStoragePublicDirectory
    //But then you will have to add android:requestLegacyExternalStorage="true" in your Manifest
    //IT IS IMPORTANT TO SET THE FILE NAME THE SAME AS THE NAME YOU USE FOR TITLE AND DISPLAY_NAME

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setOutputPath() {
        String filename = generateFileName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            resolver = getContentResolver();
            contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "SpeedTest/" + "SpeedTest");
            contentValues.put(MediaStore.Video.Media.TITLE, filename);
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
            mUri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            //FILE NAME SHOULD BE THE SAME
            hbRecorder.setFileName(filename);
            hbRecorder.setOutputUri(mUri);
        }else{
            createFolder();
            hbRecorder.setOutputPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) +"/HBRecorder");
        }
    }
    //Check if permissions was granted
    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            return false;
        }
        return true;
    }
    private void updateGalleryUri(){
        contentValues.clear();
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);
        getContentResolver().update(mUri, contentValues, null, null);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void refreshGalleryFile() {
        MediaScannerConnection.scanFile(this,
                new String[]{hbRecorder.getFilePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }
    //Generate a timestamp to be used as a file name
    private String generateFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate).replace(" ", "");
    }
    //drawable to byte[]
    private byte[] drawable2ByteArray(@DrawableRes int drawableId) {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), drawableId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    //Create Folder
    //Only call this on Android 9 and lower (getExternalStoragePublicDirectory is deprecated)
    //This can still be used on Android 10> but you will have to add android:requestLegacyExternalStorage="true" in your Manifest
    private void createFolder() {
        File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "SpeedTest");
        if (!f1.exists()) {
            if (f1.mkdirs()) {
                Log.i("Folder ", "created");
            }
        }
    }
}