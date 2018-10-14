package com.lolin.deemon_face.chatz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Users_activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_users_activity);

        mToolbar = findViewById (R.id.users_AppBAr);
        setSupportActionBar (mToolbar);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        setSupportActionBar (mToolbar);
        getSupportActionBar ().setTitle ("All Users");

        mUsersDatabase = FirebaseDatabase.getInstance ().getReference ().child ("Users");

        mUsersList = findViewById (R.id.users_list);
        mUsersList.setHasFixedSize (true);
        mUsersList.setLayoutManager (new LinearLayoutManager (this));
    }

    @Override
    protected void onStart() {
        super.onStart ();

        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder> (

                Users.class,
                R.layout.users_single_layout,
                UsersViewHolder.class, mUsersDatabase) {
            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }

            @Override
            protected void onBindViewHolder( UsersViewHolder usersViewHolder, int i, @NonNull Users users) {

                usersViewHolder.setName(users.getName ());


            }


        };

        mUsersList.setAdapter (FirebaseRecyclerAdapter);

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(@NonNull View itemView) {
            super (itemView);

            mView = itemView;
        }

        public void setName(String name){
            TextView usersNameView = mView.findViewById (R.id.user_single_name);
            usersNameView.setText (name);
        }

            }

}
