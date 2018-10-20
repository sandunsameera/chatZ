package com.lolin.deemon_face.chatz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;

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
        super.onStart();
        startListening();

    }
    public void startListening(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("user")
                .limitToLast(50);

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {
            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_single_layout, parent, false);

                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(UserViewHolder userViewHolder, int i, Users users) {
                // Bind the Chat object to the ChatHolder
                userViewHolder.setName(users.name);
                userViewHolder.setUserStatus(users.getStatus ());
                userViewHolder.setUserImage(users.getThumb_image (), getApplicationContext ());

                final String user_id = getRef (i).getKey ();
                userViewHolder.mView.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {

                        Intent profile_intent = new Intent (Users_activity.this,Profile_activity.class);
                        profile_intent.putExtra ("user_id",user_id);
                        startActivity(profile_intent);

                    }
                });
                // ...
            }

        };
        mUsersList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setName(String name){
            TextView userNameView =mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);
        }

        public void setUserStatus(String status){
            TextView userStatusView = mView.findViewById (R.id.user_single_status);
            userStatusView.setText (status);
        }

        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = mView.findViewById (R.id.user_single_image);
            Glide.with (ctx).load (thumb_image).apply (RequestOptions.errorOf (R.drawable.ic_person_black_24dp)).into (userImageView);
        }
    }

}
