package com.example.foodies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodies.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.User;
import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

     private Button btnSignUp,btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        Paper.init(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignIn.class));
                finish();

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

        //Check Remember
        String user=Paper.book().read(Common.USER_KEY);
        String pwd=Paper.book().read(Common.PWD_KEY);
        if(user!=null && pwd!=null)
        {
            if(!user.isEmpty() && !pwd.isEmpty())
                login(user,pwd);
        }


    }

    private void login(final String phone, final String pwd) {

        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference table_user=database.getReference("User");

        final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Loading..!");
        mDialog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //checking User already Exist or Not

                if(dataSnapshot.child(phone).exists()) {

                    //Get User Information
                    mDialog.dismiss();
                    User user = dataSnapshot.child(phone).getValue(User.class);

                    user.setPhone(phone);  //set Phone

                    if (user.getPassword().equals(pwd)) {
                        Toast.makeText(MainActivity.this, "Logged in Successfully..!", Toast.LENGTH_SHORT).show();
                        Common.currentUser = user;

                        Intent intent=new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed...Incorrect Password..!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Register First..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                mDialog.dismiss();
                Toast.makeText(MainActivity.this, "DatabaseError..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
