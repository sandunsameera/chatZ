package com.lolin.deemon_face.chatz;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class status_activity extends AppCompatActivity {

    //initialize toolBar
    private Toolbar mToolBar;
    //Initializing of TextInputLayout and button
    private TextInputEditText mStatus;
    private Button mSaveBtn;

    //progress
    private ProgressDialog mProgress;

    //Firebase part
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_status_activity);

        //Progress
        mProgress = new ProgressDialog (this);


        //Firebase part
        mCurrentUser = FirebaseAuth.getInstance ().getCurrentUser ();
        String current_uid = mCurrentUser.getUid ();
        mStatusDatabase = FirebaseDatabase.getInstance ().getReference ().child ("user").child (current_uid);



        mToolBar = findViewById (R.id.status_appbar);
        setSupportActionBar (mToolBar);
        getSupportActionBar ().setTitle ("Account Status");
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        String status_value = getIntent ().getStringExtra ("status_value");


        mStatus = findViewById (R.id.status_Input);
        mSaveBtn = findViewById (R.id.status_save_btn);

        mStatus.getEditableText ();

        mSaveBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                //progress
                mProgress=new ProgressDialog (status_activity.this);
                mProgress.setTitle ("PLease wait.. Saving changes");
                mProgress.show ();

                String status = mStatus.getEditableText ().toString ();

                mStatusDatabase.child ("status").setValue (status).addOnCompleteListener (new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful ()){
                            mProgress.dismiss ();
                        }
                        else {

                            Toast.makeText (getApplicationContext (), "Error in Saving Data", Toast.LENGTH_LONG).show ();
                        }
                    }
                });


            }
        });
    }
}
