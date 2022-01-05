 package com.example.preity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

// Base activity from where we can go to multiple activity

 public class firstPage extends AppCompatActivity {
    private static final String TAG = "first page";
    Button btn,btnUpload,btnGotoList;
    ProgressDialog progressDialog;
    ImageView img;
    TextView logout;
    int SELECT_PICTURE = 200;
    Uri videouri;

    Button btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btn = findViewById(R.id.button);
        btnUpload = findViewById(R.id.uploadButton);
        btnGotoList = findViewById(R.id.listViewButton);
        img = findViewById(R.id.image);
        logout= findViewById(R.id.logout_text);


        logout.setOnClickListener(new View.OnClickListener() {  // logging out the current user
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {  // Can choose an image for background of next page
            @Override
            public void onClick(View v) {
                imagepicker();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {   // can upload movies manually
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(firstPage.this);
                choosevideo();
            }
        });
        btnGotoList.setOnClickListener(new View.OnClickListener() {    // going to movie list page
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), VideoListActivity.class));
            }
        });
    }
    // method to create an intent to choose background image
    void imagepicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // in case of image
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant

            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                try {
                    secondPage.setBGimg(selectedImageUri);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    OpenSecondPage();
                }
            }

        }

        //INn case of Video to upload in firebase
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouri = data.getData();
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            uploadvideo();
        }
    }

    //method for going to second page
    public void OpenSecondPage() {
        Intent i= new Intent(this, secondPage.class );
        startActivity(i);
    }


    // choose a video from phone storage
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }


    // returns the extention of selected video
    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }
    //Uploading in firebase
    private void uploadvideo() {
        if (videouri != null) {
            // save the selected video in Firebase storage

            final StorageReference reference = FirebaseStorage.getInstance().getReference("Videos/" + System.currentTimeMillis() + "." + getfiletype(videouri));
            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video");
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    // caption random
                    String uniqueString = UUID.randomUUID().toString();
                    String filename = "Funbook"+uniqueString;

                    VideoFile file = new VideoFile(FirebaseAuth.getInstance().getCurrentUser().getUid(),downloadUri, filename, Timestamp.now());

                    db.collection("Videos")
                            .add(file)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                    //reference1.child("" + System.currentTimeMillis()).setValue(map);
                    // Video uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(firstPage.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(firstPage.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

}
