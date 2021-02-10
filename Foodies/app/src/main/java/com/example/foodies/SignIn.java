package com.example.foodies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodies.Common.Common;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;

import Model.User;
import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {

    private Button btnLogIn;
    private MaterialEditText edtPhone,edtPassword;

    private CheckBox ckbRemember;

    FirebaseDatabase database;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnLogIn=(Button) findViewById(R.id.btnLogin);
        edtPhone=(MaterialEditText) findViewById(R.id.edtPhone);
        edtPassword=(MaterialEditText) findViewById(R.id.edtPassword);

        ckbRemember=(CheckBox)findViewById(R.id.ckbRemember);
        //Paper Init
        Paper.init(this);

        FirebaseApp.initializeApp(this);



        //Firebase
        //final FirebaseDatabase
                 database=FirebaseDatabase.getInstance();
        //final DatabaseReference
                table_user=database.getReference("User");

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInterner(getBaseContext())) {

                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Loading..!");
                    mDialog.show();
                    if (ckbRemember.isChecked()) {
                        Paper.book().write(Common.USER_KEY, edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY, edtPassword.getText().toString());
                    }

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //checking User already Exist or Not

                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                                //Get User Information
                                mDialog.dismiss();
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                                user.setPhone(edtPhone.getText().toString());  //set Phone

                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Toast.makeText(SignIn.this, "Logged in Successfully..!", Toast.LENGTH_SHORT).show();
                                    Common.currentUser = user;

                                    Intent intent = new Intent(SignIn.this, Home.class);
                                    startActivity(intent);
                                    finish();


                                } else {
                                    Toast.makeText(SignIn.this, "Login Failed...Incorrect Password..!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "Register First..!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "DatabaseError..!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignIn.this,"Please Check your Internet Connection..!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        });
    }


}
