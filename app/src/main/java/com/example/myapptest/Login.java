package com.example.myapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText pw,email;
    Button log;
    TextView tcreate;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pw  = findViewById(R.id.lepassword);
        email = findViewById(R.id.leemail);
        log  = findViewById(R.id.blog);
        tcreate = findViewById(R.id.logt);

        fauth = FirebaseAuth.getInstance();


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();
                String password = pw.getText().toString().trim();

                //check the input fields are empty
                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    pw.setError("Password is required");
                    return;
                }

                if(password.length() < 6 ){
                    pw.setError("Password must contain 6 or more characters");
                    return;
                }

                //Check the entering login credentials
                fauth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Error in login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //return to create account page
        tcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }
}