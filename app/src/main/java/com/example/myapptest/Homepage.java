package com.example.myapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {

    private Button b1;
    private Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        b1 = findViewById(R.id.hlogb);
        b2 = findViewById(R.id.hcreateb);



        b1.setOnClickListener(new View.OnClickListener() {
            //button click to Login page
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this,Login.class);
                startActivity(intent);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            //Button click to Register page
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this,Register.class);
                startActivity(intent);
            }
        });
    }
}