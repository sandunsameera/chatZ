package com.lolin.deemon_face.chatz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ProxyInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
//import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //layout
    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;
    private Button mStatusBtn;
    private Button mImageBtn;

    private static final int gallery_Pick = 1;
    private static final int MAX_LENGTH = 100;
    //storage of media firebase
    private StorageReference mImageStorage;

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_settings);

        mDisplayImage = findViewById (R.id.settings_img);
        mName = findViewById (R.id.settings_name);
        mStatusBtn =findViewById (R.id.settings_chnge_about_btn);
        mImageBtn = findViewById (R.id.settings_chnge_image_btn);
        mStatus = findViewById (R.id.settins_about);


        mCurrentUser = FirebaseAuth.getInstance ().getCurrentUser ();
        mImageStorage = FirebaseStorage.getInstance ().getReference ();

        String current_uid = mCurrentUser.getUid ();
        mUserDatabase = FirebaseDatabase.getInstance ().getReference ("user").child (current_uid);

        mUserDatabase.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child ("name").getValue ().toString ();
                String image = dataSnapshot.child ("image").getValue ().toString ();
                final String status = dataSnapshot.child ("status").getValue ().toString ();
                String thumb_image = dataSnapshot.child ("thumb_image").getValue ().toString ();

                mName.setText (name);
                mStatus.setText (status);

                mStatusBtn.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {

                        String status_value = mStatus.getText ().toString ();
                        Intent Status_intent = new Intent (SettingsActivity.this, status_activity.class);
                        Status_intent.putExtra ("status_value",status_value);
                        startActivity(Status_intent);
                    }
                });


                Glide.with (SettingsActivity.this).load (image).apply (RequestOptions.errorOf (R.drawable.ic_person_black_24dp)).into (mDisplayImage);

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
                CropImage.activity ()
                        .setGuidelines (CropImageView.Guidelines.ON)
                        .setAspectRatio (1, 1)
                        .start (SettingsActivity.this);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult (data);
            if (resultCode == RESULT_OK) {

                mProgressDialog = new ProgressDialog (SettingsActivity.this);
                mProgressDialog.setTitle ("Uploading Image ..");
                mProgressDialog.setMessage ("PLease wait a minute");
                mProgressDialog.setCanceledOnTouchOutside (false);
                Uri resultUri = result.getUri ();

                final String current_user_Id = mCurrentUser.getUid ();


                StorageReference filepath = mImageStorage.child ("Profile_pics").child (current_user_Id + ".jpg");
                final Task<Uri> download_url = filepath.getDownloadUrl ();
                filepath.putFile (resultUri).addOnCompleteListener (new OnCompleteListener<UploadTask.TaskSnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful ()) {
                            //String download_url =task.getResult ().getDownloadUrl().toString();
                            download_url.addOnCompleteListener (new OnCompleteListener<Uri> () {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri uri = download_url.getResult ();
                                    mUserDatabase.child (current_user_Id).child ("default_image").setValue (uri).addOnCompleteListener (new OnCompleteListener<Void> () {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful ()) {

                                                mProgressDialog.dismiss ();
                                                Toast.makeText (SettingsActivity.this, "success in Uploading", Toast.LENGTH_LONG).show ();


                                            }

                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText (SettingsActivity.this, "Error in Uploading", Toast.LENGTH_LONG).show ();
                            mProgressDialog.dismiss ();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError ();
            }
        }
    }

    public static String random() {
        Random generator = new Random ();
        StringBuilder randomStringBuilder = new StringBuilder ();
        int randomLength = generator.nextInt (MAX_LENGTH);
        char tempchar;
        for (int i = 0; i < randomLength; i++) {
            tempchar = (char) (generator.nextInt (96) + 32);
            randomStringBuilder.append (tempchar);

        }

        return randomStringBuilder.toString ();



    }


}
