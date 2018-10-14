package com.lolin.deemon_face.chatz;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.lolin.deemon_face.chatz.R.*;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mToolbar=findViewById (id.main_page_toobar);
        setSupportActionBar (mToolbar);
        getSupportActionBar ().setTitle ("Welcome to Chatz");

        //Tabs
        mViewPager = findViewById (id.main_tabPager);
        mSectionPagerAdapter = new SectionPagerAdapter (getSupportFragmentManager ());
        mViewPager.setAdapter (mSectionPagerAdapter);
        mTabLayout = findViewById (id.main_tabs);
        mTabLayout.setupWithViewPager (mViewPager);

    }

    @Override
    protected void onStart() {
        super.onStart ();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
           sendToStart();
        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent (MainActivity.this, startActivity.class);
        startActivity (startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.main_menu, menu);
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected (item);

        if(item.getItemId () == R.id.main_logout_btn) {
            FirebaseAuth.getInstance ().signOut ();
            sendToStart ();

        }

        if(item.getItemId ()== R.id.main_AccntSettings_btn){
            Intent settingsIntent = new Intent (MainActivity.this, SettingsActivity.class);
            startActivity (settingsIntent);


        }
        if (item.getItemId () == id.main_allUser_btn){

            Intent UsersIntent = new Intent (MainActivity.this, Users_activity.class);
            startActivity (UsersIntent);
        }

        return true;

           }
}
