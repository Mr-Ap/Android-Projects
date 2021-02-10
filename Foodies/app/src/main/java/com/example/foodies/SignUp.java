package com.example.foodies;

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

import Model.User;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtPhone,edtName,edtPassword;
    Button btnSignUp;
    FirebaseDatabase database;
    DatabaseReference table_user;
    int flg=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);

        btnSignUp = (Button)findViewById(R.id.btnRegister);

        //FirebaseApp.initializeApp(this);

        //Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isConnectedToInterner(getBaseContext())) {

                    final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("Loading..!");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //check if already user exist
                            mDialog.dismiss();

                            if (dataSnapshot.child(edtPhone.getText().toString().trim()).exists() != true) {

                                // mDialog.dismiss();
                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString().trim());
                                table_user.child(edtPhone.getText().toString().trim()).setValue(user);
                                flg = 1;
                                Toast.makeText(SignUp.this, "Successfully Registered....", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(SignUp.this,MainActivity.class);
                                startActivity(intent);

                                //mDialog.dismiss();
                                // Toast.makeText(SignUp.this, "Already Registered....", Toast.LENGTH_SHORT).show();
                            } else {
                                // mDialog.dismiss();
                                if (flg == 0)
                                    Toast.makeText(SignUp.this, "Already Registered....", Toast.LENGTH_SHORT).show();
                                /*mDialog.dismiss();
                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString().trim());
                                table_user.child(edtPhone.getText().toString().trim()).setValue(user);
                                Toast.makeText(SignUp.this, "Successfully Registered....", Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent=new Intent(SignUp.this,MainActivity.class);
                                startActivity(intent);*/
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });

                }
                else {
                    Toast.makeText(SignUp.this,"Please Check your Internet Connection..!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });




    }
}
