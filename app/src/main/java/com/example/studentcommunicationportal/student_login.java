package com.example.studentcommunicationportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class student_login extends AppCompatActivity {
    Button signup,login;
    EditText temail,tpass;
    private  FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        getSupportActionBar().setTitle("Login Form");

        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        temail=findViewById(R.id.emailadd);
        tpass=findViewById(R.id.passwordadd);

        firebaseAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=temail.getText().toString().trim();
               String  password=tpass.getText().toString().trim();
                Log.i("student_login","Executed 1");
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(student_login.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(student_login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(student_login.this, "password too short", Toast.LENGTH_SHORT).show();
                }


                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(student_login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    startActivity(new Intent(getApplicationContext(),MainPage.class));
                                } else {
                                    Toast.makeText(student_login.this, "Login failed or User not Available !!!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });


            }
        });



    }

    public void doSomething(View view) {
        Intent intent = new Intent(student_login.this,student_signup.class);
        startActivity(intent);
    }
}
