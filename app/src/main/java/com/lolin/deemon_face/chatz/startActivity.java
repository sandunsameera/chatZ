package com.lolin.deemon_face.chatz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startActivity extends AppCompatActivity {

    private Button mRegBtn;
    private Button mAlready_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_start);

        mRegBtn=findViewById (R.id.start_reg_btn);
        mRegBtn.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent (startActivity.this, RegisterActivity.class);
                startActivity (regIntent);
            }
        });

        mAlready_Btn=findViewById (R.id.start_logIn_btn);
        mAlready_Btn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent (startActivity.this, LoginActivity.class);
                startActivity(logIntent);
            }
        });
    }

}
