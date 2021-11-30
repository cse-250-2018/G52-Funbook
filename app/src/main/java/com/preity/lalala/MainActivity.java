package com.preity.lalala;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.preity.lalala.R;
import com.preity.lalala.firstPage;

public class MainActivity extends AppCompatActivity {

    private Button btn_1;
    private Button btn_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_1 =  findViewById(R.id.btn_1);
        btn_2 =  findViewById(R.id.btn_2);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFirstPage();
            }
        });

    }

    public void OpenFirstPage() {
        Intent intent= new Intent(this, firstPage.class );
        startActivity(intent);
    }
}