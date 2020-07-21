package com.example.spieler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mdbr;
    private FirebaseUser muser;
    private String Username;
    private ViewPager mviewpager;
    private SectionPagerAdapter sectionPagerAdapter;
    private TabLayout mtablayout;
    private FirebaseListAdapter<ChatMessage> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("SPIELER");
        mAuth = FirebaseAuth.getInstance();
        muser = FirebaseAuth.getInstance().getCurrentUser();
        if (muser!=null){
            String curruser = muser.getUid();
            mdbr = FirebaseDatabase.getInstance().getReference().child("Users").child(curruser);
            mdbr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Username = dataSnapshot.child("Name").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            mviewpager=(ViewPager)findViewById(R.id.tab_pager);

            sectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());
            mviewpager.setAdapter(sectionPagerAdapter);
            mtablayout=(TabLayout)findViewById(R.id.main_tablayout);

            mtablayout.setupWithViewPager(mviewpager);
//            FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    EditText input = (EditText)findViewById(R.id.input);
//
//                    // Read the input field and push a new instance
//                    // of ChatMessage to the Firebase database
//                    FirebaseDatabase.getInstance()
//                            .getReference()
//                            .push()
//                            .setValue(new ChatMessage(input.getText().toString(),
//                                    FirebaseAuth.getInstance()
//                                            .getCurrentUser()
//                                            .getDisplayName())
//                            );
//
//                    // Clear the input
//                    input.setText("");
//                }
//            });
        }

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            redirectStart();
        }

    }



//    private void displayChatMessages() {
//
//        ListView listofMessages = (ListView)findViewById(R.id.list_of_messages);
//        Query query = FirebaseDatabase.getInstance().getReference().child("chats");
////The error said the constructor expected FirebaseListOptions - here you create them:
//        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
//                .setQuery(query, ChatMessage.class)
//                .setLayout(R.layout.message)
//                .build();
//        //Finally you pass them to the constructor here:
//        adapter = new FirebaseListAdapter<ChatMessage>(options){
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//                // Get references to the views of message.xml
//                TextView messageText = (TextView)v.findViewById(R.id.message_text);
//                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
//                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
//
//                // Set their text
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//
//
//
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
//            }
//        };
//        listofMessages.setAdapter(adapter);
//    }

    private void redirectStart() {
        startActivity(new Intent(MainActivity.this,StartActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()== R.id.logout){
            FirebaseAuth.getInstance().signOut();
            redirectStart();
        }
        if(item.getItemId() == R.id.my_account){
            Intent set = new Intent(MainActivity.this,AccountSettingsActivity.class);
            startActivity(set);

        }
        if(item.getItemId() == R.id.allusers){
            Intent alu = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(alu);
        }

        return true;
    }
}
