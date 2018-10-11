package com.lolin.deemon_face.chatz;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //layout
    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_settings);

        mDisplayImage = findViewById (R.id.settings_img);
        mName = findViewById (R.id.settings_name);
        mStatus = findViewById (R.id.settins_about);

        mCurrentUser = FirebaseAuth.getInstance ().getCurrentUser ();

        String current_uid = mCurrentUser.getUid ();
        mUserDatabase = FirebaseDatabase.getInstance ().getReference("user").child (current_uid);

        mUserDatabase.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child ("name").getValue ().toString ();
                String image = dataSnapshot.child ("image").getValue ().toString ();
                String status = dataSnapshot.child ("status").getValue ().toString ();
                String thumb_image = dataSnapshot.child ("thumb_image").getValue ().toString ();

                mName.setText (name);
                mStatus.setText (status);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
