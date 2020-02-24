package com.example.spieler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private Button chng;
    private TextInputLayout status_new;
    private FirebaseUser muser;
    private DatabaseReference mstatusdb;
    private ProgressDialog prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        muser= FirebaseAuth.getInstance().getCurrentUser();
        String uid = muser.getUid();
        mstatusdb= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mtoolbar=(Toolbar) findViewById(R.id.status_mappbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Status Loading");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chng = (Button)findViewById(R.id.status_change_btn);
        status_new = (TextInputLayout)findViewById(R.id.new_status);

        String status_value = getIntent().getStringExtra("status_value");
        status_new.getEditText().setText(status_value);

        chng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prog = new ProgressDialog(StatusActivity.this);
                prog.setTitle("Updating Status");
                prog.setMessage("Stay like a good boy while your Litt status is being upated!");
                prog.show();
                String status = status_new.getEditText().getText().toString();
                mstatusdb.child("Status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            prog.dismiss();
                        }
                    }
                });
            }
        });

    }

}
