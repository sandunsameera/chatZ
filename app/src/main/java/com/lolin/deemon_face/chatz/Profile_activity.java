package com.lolin.deemon_face.chatz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile_activity extends AppCompatActivity {

    private TextView mDisplayID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile_activity);

        String user_id = getIntent ().getStringExtra ("user_id");

//        mDisplayID = findViewById (R.id.);
        mDisplayID.setText (user_id);



    }
}
