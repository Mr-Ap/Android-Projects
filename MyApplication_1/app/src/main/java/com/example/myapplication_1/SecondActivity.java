package com.example.myapplication_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class SecondActivity extends AppCompatActivity {
    private Button btnLogOut;
    private FirebaseAuth firebaseAuth;
    private Button btnCanteen,btnLipton,btnCafeteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth=FirebaseAuth.getInstance();
        btnLogOut=findViewById(R.id.btnLogOut);
        btnCanteen=findViewById(R.id.btnCanteen);
        btnLipton=findViewById(R.id.btnLipton);
        btnCafeteria=findViewById(R.id.btnCafeteria);
        btnLipton.setEnabled(false);
        btnCafeteria.setEnabled(false);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnCanteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(SecondActivity.this,.class));
            }
        });

    }

    private void logout()
    {
        firebaseAuth.signOut();
        finish();
        Toast.makeText(SecondActivity.this,"Logged Out Successfully..!",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SecondActivity.this,MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logoutMenu:
            {
                logout();
            }
            case R.id.accountMenu:
            {
                Toast.makeText(this,"Not Yet",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.orderMenu:
            {
                Toast.makeText(this,"Not Yet",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.CartMenu:
            {
                Toast.makeText(this,"Not Yet",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.SettingsMenu:
            {
                Toast.makeText(this,"What Kind of settings you want! Shut Up man..!",Toast.LENGTH_SHORT).show();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}

