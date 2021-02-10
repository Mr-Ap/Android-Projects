package com.example.paytm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView,textView2;
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        imageView=findViewById(R.id.imageView);

    }
}
