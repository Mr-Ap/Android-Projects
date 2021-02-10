package com.example.myapplication_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail,etPassword;
    private TextView Info;
    private TextView tvRegister;
    private Button btnLogin;
    private Integer counter=5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView tvforgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail=(EditText)findViewById(R.id.etMail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        Info=(TextView)findViewById(R.id.Info);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        tvRegister=(TextView)findViewById(R.id.tvRegister);

        tvforgotPassword=(TextView)findViewById(R.id.tvforgotPassword) ;


        Info.setText("No of Attempts Remaining : 5");
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }
        // else
        //Toast.makeText(MainActivity.this,"Enter Valid Credentials..!",Toast.LENGTH_SHORT).show();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().isEmpty()
                        || etPassword.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this,"Enter Valid Credentials..!",Toast.LENGTH_SHORT).show();
                else
                    validate(etEmail.getText().toString().trim(),etPassword.getText().toString().trim());
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

        tvforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PasswordActivity.class));
            }
        });


    }

    private void validate(String userName,String userPassword)
    {
        progressDialog.setMessage("Logging In...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    //Toast.makeText(MainActivity.this,"Login Successful...!",Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                    //startActivity(new Intent(MainActivity.this, SecondActivity.class));

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Failed...!", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of Attempts remaining : "+counter);
                    if(counter==0)
                        btnLogin.setEnabled(false);
                }
            }
        });
    }

    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if (emailflag) {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        } else {
            Toast.makeText(this, "Verify Your Email..!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
