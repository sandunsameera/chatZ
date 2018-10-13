package com.lolin.deemon_face.chatz;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //layout
    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatusBtn;
    private Button mImageBtn;

    private static final int gallery_Pick =1;

    //storage of media firebase
    private StorageReference mImageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_settings);

        mDisplayImage = findViewById (R.id.settings_img);
        mName = findViewById (R.id.settings_name);
        mStatusBtn = findViewById (R.id.settins_about);
        mImageBtn = findViewById (R.id.settings_chnge_image_btn);
        mImageStorage = FirebaseStorage.getInstance ().getReference ();

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
                mStatusBtn.setText (status);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mImageBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               /* Intent galleryIntent = new Intent ();
                galleryIntent.setType ("image/*");
                galleryIntent.setAction (Intent.ACTION_GET_CONTENT);

                startActivityForResult (Intent.createChooser(galleryIntent, "Choose your profile picture"),gallery_Pick);
                */
               CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio (1,1)
                        .start(SettingsActivity.this);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                StorageReference filepath = mImageStorage.child ("Profile_images").child ("Profile_Image.jpg");
                filepath.putFile (resultUri).addOnCompleteListener (new OnCompleteListener<UploadTask.TaskSnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       if (task.isSuccessful ()){
                           Toast.makeText (SettingsActivity.this, "Working", Toast.LENGTH_LONG).show ();
                       }
                       else {
                           Toast.makeText (SettingsActivity.this, "Error in Uploading", Toast.LENGTH_LONG).show ();
                       }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
