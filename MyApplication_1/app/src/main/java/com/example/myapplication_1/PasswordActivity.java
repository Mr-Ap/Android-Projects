package com.example.myapplication_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText PasswordEmail;
    private Button btnResetPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        firebaseAuth=FirebaseAuth.getInstance();
        PasswordEmail=(EditText)findViewById(R.id.etPasswordEmail);
        btnResetPassword=(Button)findViewById(R.id.btnPasswordReset);
        progressDialog=new ProgressDialog(this);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=PasswordEmail.getText().toString().trim();
                progressDialog.setMessage("Loading...!");
                progressDialog.show();
                if(email.equals(""))
                {
                    progressDialog.dismiss();
                    Toast.makeText(PasswordActivity.this,"Enter Valid Credentials..!",Toast.LENGTH_SHORT).show();

                }else
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(PasswordActivity.this, "Password Reset Email sent..!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this,MainActivity.class));
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(PasswordActivity.this,"Error in Sending Reset Password Email.!",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

    }
}
