package com.example.spieler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    private Toolbar mtool;
    private Button mRegButton;
    private Button mlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar mtool = (Toolbar)findViewById(R.id.toolbar);
        mtool.setTitle("SPIELER");
        setSupportActionBar(mtool);
        mRegButton=(Button) findViewById(R.id.start_reg_button);
        mlogin=(Button)findViewById(R.id.start_login_button);
        Intent curr = new Intent(StartActivity.this,StartActivity.class);
        curr.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        curr.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(regIntent);
                finish();
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        int backButtonCount=0;
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
//            super.onBackPressed();
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
