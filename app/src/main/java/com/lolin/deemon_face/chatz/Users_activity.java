package com.lolin.deemon_face.chatz;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Users_activity extends AppCompatActivity {

    private FirebaseRecyclerAdapter mFirebaseRecyclerAdapter;
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

        FirebaseRecyclerOptions<Users> options= new FirebaseRecyclerOptions.Builder<Users> ()
                .setQuery (mUsersDatabase, Users.class)
                .build ();

        FirebaseRecyclerAdapter mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {

                holder.setName (model.getName ());
                holder.setStatus (model.getStatus ());

            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                return null;


            }
        };



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

        public void setStatus(String status){
            TextView usersStatusView = mView.findViewById (R.id.user_single_status);
            usersStatusView.setText (status);
        }

            }

}
