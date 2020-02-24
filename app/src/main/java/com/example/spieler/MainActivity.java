package com.example.spieler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private FirebaseAuth mAuth;
    private ViewPager mviewpager;
    private SectionPagerAdapter sectionPagerAdapter;
    private TabLayout mtablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("SPIELER");
        mAuth = FirebaseAuth.getInstance();
        mviewpager=(ViewPager)findViewById(R.id.tab_pager);

        sectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());
        mviewpager.setAdapter(sectionPagerAdapter);
        mtablayout=(TabLayout)findViewById(R.id.main_tablayout);

        mtablayout.setupWithViewPager(mviewpager);
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
            Intent alu = new Intent(MainActivity.this, Users.class);
            startActivity(alu);
        }

        return true;
    }
}
