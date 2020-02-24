package com.example.spieler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button signupbtn;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mDisp;
    private FirebaseAuth mFirebaseAuth;
    private Toolbar mtool;
    private ProgressDialog regProg;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar mtool = (Toolbar)findViewById(R.id.toolbar) ;
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("SPIELER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFirebaseAuth=FirebaseAuth.getInstance();
        signupbtn=(Button)findViewById(R.id.signUp);
        mEmail=(TextInputLayout)findViewById(R.id.rEmail);
        mPassword=(TextInputLayout)findViewById(R.id.rPassword);
        mDisp=(TextInputLayout)findViewById(R.id.rDisplayName);

        regProg = new ProgressDialog(this);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String Email = mEmail.getEditText().getText().toString();
                String Password = mPassword.getEditText().getText().toString();
                 final String Name = mDisp.getEditText().getText().toString();
                if(Email.isEmpty()){
                    mEmail.setError("Email cannot be empty");
                }
                if(Password.isEmpty()){
                    mPassword.setError("Password cannot be empty");
                }
                if(Name.isEmpty()){
                    mDisp.setError("Display Name cannot be empty");
                }
                else if(!Email.isEmpty() && !Password.isEmpty() && !Name.isEmpty())
                    {
                        regProg.setTitle("Registration in Progress");
                        regProg.setMessage("Hold on to your Butts while we create your account");
                        regProg.setCanceledOnTouchOutside(false);
                        regProg.show();
                        mFirebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                regProg.dismiss();
                                Toast.makeText(RegisterActivity.this,"Couldn't complete registration, Try again",Toast.LENGTH_LONG).show();
                            }
                            else{
                                FirebaseUser m = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = m.getUid();
                                mdatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                HashMap<String, String> usermap = new HashMap<>();
                                usermap.put("Name",Name);
                                usermap.put("Status","Hi welcome to spieler");
                                usermap.put("Image","default");
                                usermap.put("Thumb_img","default");
                                mdatabase.setValue(usermap);
                                regProg.hide();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this,"Welcome "+Name,Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
                }}
        });
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId()==R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
