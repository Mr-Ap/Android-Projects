package com.example.myapplication_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etUserEmail,etUserPassword,etUserName,etMobile;
    private Button btnRegister;
    private TextView textView2;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String mail,name,password,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    //Upload Data to Database
                    mail=etUserEmail.getText().toString().trim();
                    password=etUserPassword.getText().toString().trim();
                    name=etUserName.getText().toString();
                    mobile=etMobile.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                //finish();
                                //Toast.makeText(RegistrationActivity.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                                sendEmailVerification();
                                // sendUserData();
                                //Toast.makeText(RegistrationActivity.this,"Successfully Registered,Verification Mail Sent..!",Toast.LENGTH_SHORT).show();
                                //firebaseAuth.signOut();
                                // finish();
                                //startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //else
                //Toast.makeText(RegistrationActivity.this, "Enter Valid Details!", Toast.LENGTH_SHORT).show();

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });

    }

    private void setupUIViews()
    {
        etUserEmail=(EditText)findViewById(R.id.etUserEmail);
        etUserName=(EditText)findViewById(R.id.etUserName);
        etUserPassword=(EditText)findViewById(R.id.etUserPassword);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        textView2=(TextView)findViewById(R.id.textView2);
        etMobile=(EditText)findViewById(R.id.etMobile);
    }

    private boolean validate()
    {
        boolean result=false;
        progressDialog.setMessage("Loading...!");
        progressDialog.show();
        name=etUserName.getText().toString();
        mail=etUserEmail.getText().toString().trim();
        password=etUserPassword.getText().toString().trim();
        mobile=etMobile.getText().toString();
        if(name.isEmpty() || mail.isEmpty() || password.isEmpty() || mobile.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(RegistrationActivity.this, "Plz Enter Valid Credentials!", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }

    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this,"Successfully Registered,Verification Mail Sent..!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this,"Verification Mail is not sent..!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile=new UserProfile(mail,mobile,name);
        myRef.setValue(userProfile);
    }

}
