package com.example.sample_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    EditText prn;
    Button submit;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prn=findViewById(R.id.prn);
        submit=findViewById(R.id.submit);
        tv=findViewById(R.id.tv);

        tv.setVisibility(View.GONE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idprn =prn.getText().toString().trim();
                if(idprn.length()==14) {
                    int yoa = Integer.parseInt(idprn.substring(0, 4));
                    String degree = idprn.substring(4, 7);
                    String bte = "bte";
                    String BTE = "BTE";
                    if (bte.equals(degree) || BTE.equals(degree))
                        degree = "Bachelor of Technology.!";
                    String branch = idprn.substring(7, 9);
                    String it = "it";
                    String IT = "IT";
                    String cs = "cs";
                    String CS = "CS";
                    String me = "me";
                    String ME = "ME";
                    String en = "en";
                    String EN = "EN";
                    if (it.equals(branch) || IT.equals(branch))
                        branch = "Information Technology.!";
                    if (cs.equals(branch) || CS.equals(branch))
                        branch = "Computer Science and Engineering.!";
                    if (me.equals(branch) || ME.equals(branch))
                        branch = "Mechanical Engineering.!";
                    if (en.equals(branch) || EN.equals(branch))
                        branch = "Electronics Engineering.!";
                    int rno = Integer.parseInt(idprn.substring(9, 14));
                    String text = getString(R.string.wce) + "\n" + getString(R.string.yoa) + yoa + "\n" + getString(R.string.yog) + (yoa + 4) + "\n" + getString(R.string.degree) + degree + "\n" + getString(R.string.branch) + branch + "\n" + getString(R.string.rno) + rno + "\n" + getString(R.string.ty);
                    tv.setText(text);
                }
                else
                    tv.setText(getString(R.string.vprn));
                tv.setVisibility(View.VISIBLE);
            }
        });
    }
}
