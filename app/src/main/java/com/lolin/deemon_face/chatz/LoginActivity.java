package com.lolin.deemon_face.chatz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private Button mLoginBtn;

    private ProgressDialog mLoginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance ();

        mToolBar = findViewById (R.id.Login_toolbar);
        setSupportActionBar (mToolBar);
        Objects.requireNonNull (getSupportActionBar ()).setDisplayHomeAsUpEnabled (true);
        getSupportActionBar ().setTitle ("LogIn");

        mLoginProgress = new ProgressDialog (this );

        mLoginEmail = findViewById (R.id.login_email);
        mLoginPassword = findViewById (R.id.login_password);
        mLoginBtn = findViewById (R.id.login_btn);

        mLoginBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String email = mLoginEmail.getText ().toString ();
                String password = mLoginPassword.getText ().toString ();

                if(!TextUtils.isEmpty (email) || !TextUtils.isEmpty (password)){

                    mLoginProgress.setTitle ("Logging In");
                    mLoginProgress.setMessage ("Checking Credentials");
                    mLoginProgress.setCanceledOnTouchOutside (false);
                    mLoginProgress.show ();
                    loginUser(email, password);

                }
            }
        });

    }

    private void loginUser(String email, String password) {
        Log.e ("sex",email);
        Log.e ("sex",password);

        mAuth.signInWithEmailAndPassword (email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            mLoginProgress.dismiss ();
                            Intent mainIntent = new Intent (LoginActivity.this , MainActivity.class);
                            mainIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity (mainIntent);
                            finish ();

                        } else {

                            mLoginProgress.hide ();
                            Toast.makeText (LoginActivity.this, "Cannot log in check again !", Toast.LENGTH_LONG).show ();

                        }


                    }
                });

    }
}
