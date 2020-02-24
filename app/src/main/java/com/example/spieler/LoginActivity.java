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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Toolbar mtool;
    private TextInputLayout lemail;
    private TextInputLayout lpass;
    private Button login;
    private ProgressDialog prog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar mtool = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("SPIELER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lemail=(TextInputLayout)findViewById(R.id.login_email);
        lpass=(TextInputLayout)findViewById(R.id.login_pass);
        login=(Button)findViewById(R.id.login);
        prog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = lemail.getEditText().getText().toString();
                String passw = lpass.getEditText().getText().toString();
                if(email.isEmpty()){
                    lemail.setError("Email can't be empty!");
                }
                if(passw.isEmpty()){
                    lpass.setError("Password can't be empty");
                }
                if(!email.isEmpty() && !passw.isEmpty()){
                    prog.setTitle("Logging In");
                    prog.setMessage("Hold on to your BUTTS while we log you in!");
                    prog.setCanceledOnTouchOutside(false);
                    prog.show();
                   loginUser(email,passw);
                }
            }
        });
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId()==R.id.home){
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    private void loginUser(String email, String passw) {
        mAuth.signInWithEmailAndPassword(email, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    prog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    prog.dismiss();
                    Toast.makeText(LoginActivity.this,"Email/Password is incorrect",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
