package com.example.foodies;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.Common.Common;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName,tvPhone;
    ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tvName=(TextView)findViewById(R.id.tvName);
        tvPhone=(TextView)findViewById(R.id.tvPhone);
        profilepic=(ImageView)findViewById(R.id.profilepic);

        tvName.setText(Common.currentUser.getName());
        tvPhone.setText(Common.currentUser.getPhone());
    }


}
