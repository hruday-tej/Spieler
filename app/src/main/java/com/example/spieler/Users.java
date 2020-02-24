package com.example.spieler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Users extends AppCompatActivity {

    private Toolbar mtoolbar;

    private RecyclerView muserslist;

    private DatabaseReference mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mtoolbar = (Toolbar)findViewById(R.id.users_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mdb = FirebaseDatabase.getInstance().getReference().child("Users");


        muserslist = (RecyclerView)findViewById(R.id.users_list);
        muserslist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(mdb,User.class)
                .build();
        FirebaseRecyclerAdapter<User,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull User model) {
                holder.username.setText(model.getName());
                holder.userstatus.setText(model.getStatus());
            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout,parent,false);
                UsersViewHolder usersViewHolder = new UsersViewHolder(view);
                return usersViewHolder;
            }
        };
        muserslist.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView username,userstatus;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            userstatus = itemView.findViewById(R.id.user_single_status);
            username = itemView.findViewById(R.id.user_single_name);

        }

    }
}
