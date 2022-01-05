package com.example.preity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


// If not logged in First page that user will see first


public class MainActivity extends AppCompatActivity {

    EditText uEmail, uPass, uConPass;
    Button uRegisterBtn;
    TextView uLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uEmail = findViewById(R.id.EmailAddress);
        uPass = findViewById(R.id.password);
        uConPass = findViewById(R.id.passwordConfirm);
        uRegisterBtn = findViewById(R.id.register);
        uLoginBtn = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        if( firebaseAuth.getCurrentUser() != null )  // if user is logged in, go to first page
        {
            startActivity(new Intent(getApplicationContext(),firstPage.class));
            finish();
        }

        // User trying to register
        uRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= uEmail.getText().toString().trim();
                String pass= uPass.getText().toString().trim();
                String conPass=uConPass.getText().toString().trim();
                // Register field errors
                if(TextUtils.isEmpty(email))
                {
                    uEmail.setError("EMAIL CAN'T BE EMPTY");
                    return;
                }
                if(TextUtils.isEmpty(pass) || pass.length()<6)
                {
                    uPass.setError("PASSWORD CAN'T BE LESS THAN 6");
                    return;
                }
                // all fields are ok
                else if(pass.equals(conPass))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(MainActivity.this, "Verification Mail sent!", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG ).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                else
                {
                    uConPass.setError("PASSWORD DID'T MATCH");
                    return;
                }

            }
        });

        //User do not have to register
        // Already have account
        uLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}
