package com.example.myapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText fname,lname,email,pw;
    TextView tlog;
    Button log;
    FirebaseAuth fauth;
    String userId;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = findViewById(R.id.efname);
        lname = findViewById(R.id.etlname);
        email = findViewById(R.id.eemail);
        pw = findViewById(R.id.epassword);
        tlog = findViewById(R.id.logt);
        log = findViewById(R.id.rig);
        fauth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        if(fauth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Email = email.getText().toString().trim();
                final String firstname = fname.getText().toString().trim();
                final String lastname = lname.getText().toString().trim();
                String password = pw.getText().toString().trim();

                //check the input fields are empty
                        if(TextUtils.isEmpty(firstname)){
                            fname.setError("First Name is required.");
                            return;
                        }

                        if(TextUtils.isEmpty(lastname)){
                            lname.setError("Last Name is required");
                            return;
                        }

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

                        fauth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(Register.this, "New user created", Toast.LENGTH_SHORT).show();

                                    userId  = fauth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("users").document(userId);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("fname", firstname);
                                    user.put("lname", lastname);
                                    user.put("email", Email);
                                    db.collection("users")
                                            .add(user)
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

                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }
                                else{
                                    Toast.makeText(Register.this, "Error in create Account!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        tlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}