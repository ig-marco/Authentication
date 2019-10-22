package com.example.studentcommunicationportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class student_signup extends AppCompatActivity {

    EditText txtEmail,txtPassword,txtConfirmPassword,trollno,tname,tphone;
    Button btn_register;
    RadioButton radioMale,radioFemale;
    String gender="",strcollege="",strbranch="",stryear="";
    Spinner college,branch,year;
    String colleges[]={"CV Raman","GIET","GITA","Silicon","KIIT","IIT,BBSR","NIT,Rourkela","IIIT","Parala Maharaja","Trident","CET","HiTech","IGIT,Saranga","ITER","BPUT","CUTM","Other"};
    String branches[]={"Mechanical","Civil","Chemical","ETC","Electrical","CSE","CS & IT","AEI"};
    String years[]={"1st","2nd","3rd","4th"};

    DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);
        getSupportActionBar().setTitle("Student SignUp");

        trollno=findViewById(R.id.rollno);
        tname=findViewById(R.id.name);
        tphone=findViewById(R.id.phone);
        txtEmail=findViewById(R.id.email);
        txtPassword=findViewById(R.id.password);
        txtConfirmPassword=findViewById(R.id.confirmpassword);
        btn_register=findViewById(R.id.submit);
        radioMale=findViewById(R.id.male);
        radioFemale=findViewById(R.id.female);
        college=findViewById(R.id.college);
        branch=findViewById(R.id.branch);
        year=findViewById(R.id.year);


        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,colleges);
        college.setAdapter(adapter);
        college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strcollege=colleges[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter1=new ArrayAdapter(this,android.R.layout.simple_list_item_1,branches);
        branch.setAdapter(adapter1);
        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strbranch=branches[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter2=new ArrayAdapter(this,android.R.layout.simple_list_item_1,years);
        year.setAdapter(adapter2);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stryear=years[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Student:");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 final String name=tname.getText().toString().trim();
                final String rollno=trollno.getText().toString().trim();
                final String email=txtEmail.getText().toString().trim();
                   String password=txtPassword.getText().toString().trim();
                  String cpassword=txtConfirmPassword.getText().toString().trim();
                final String phone=tphone.getText().toString().trim();
                final String scollege=strcollege;
                final String sbranch=strbranch;
                final String syear=stryear;


                if(radioMale.isChecked())
                {
                    gender="Male";
                }
                if (radioFemale.isChecked())
                {
                    gender="Female";
                }

                if (TextUtils.isEmpty(name))
                {
                    Toast.makeText(student_signup.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rollno))
                {
                    Toast.makeText(student_signup.this, "Please enter your Rollno.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(student_signup.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(student_signup.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cpassword))
                {
                    Toast.makeText(student_signup.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length()<6)
                {
                    Toast.makeText(student_signup.this, "Password should be of 6 or more characters", Toast.LENGTH_SHORT).show();
                }
                if (cpassword.length()<6)
                {
                    Toast.makeText(student_signup.this, "Confirmed Password less than 6 characters", Toast.LENGTH_SHORT).show();
                }
               // progressBar.setVisibility(View.VISIBLE);


                if (password.equals(cpassword))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(student_signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                   // progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                                Student information = new Student(
                                                        name,
                                                        rollno,
                                                        email,
                                                        phone,
                                                        scollege,
                                                        sbranch,
                                                        syear,
                                                        gender);
                                                FirebaseDatabase.getInstance().getReference("Student:")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {


                                                        startActivity(new Intent(getApplicationContext(),MainPage.class));
                                                        Toast.makeText(student_signup.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                    } else {
                                        Toast.makeText(student_signup.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(student_signup.this, "Confirmed Password not equal to Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
