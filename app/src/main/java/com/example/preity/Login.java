package com.example.preity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.preity.MainActivity;
import com.example.preity.firstPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText uEmail,uPass;
    TextView uRegister;
    Button uLogin;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    TextView uForgotPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uEmail = findViewById(R.id.EmailAddress);
        uPass = findViewById(R.id.password);
        uRegister = findViewById(R.id.Register);
        uLogin = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth =FirebaseAuth.getInstance();
        uForgotPass = findViewById(R.id.forgotPass);

        uLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= uEmail.getText().toString().trim();
                String pass= uPass.getText().toString().trim();
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
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseAuth.getInstance().getCurrentUser().reload();
                                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(getApplicationContext(), firstPage.class));
                                }
                                else
                                {
                                    Toast.makeText(Login.this, "First verify your mail!", Toast.LENGTH_LONG).show();
                                }


                            }
                            else
                            {
                                Toast.makeText(Login.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG ).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

        uRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        uForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail= new EditText(v.getContext());
                AlertDialog.Builder passResetDialog = new AlertDialog.Builder(v.getContext());
                passResetDialog.setTitle("Reset Password");
                passResetDialog.setMessage("Enter mail to recover password:");
                passResetDialog.setView(resetMail);

                passResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this, "SENT RESET LINK TO MAIL", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                passResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passResetDialog.create().show();


            }
        });

    }
}