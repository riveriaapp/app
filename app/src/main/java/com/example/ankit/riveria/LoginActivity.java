package com.example.ankit.riveria;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmail,mPwd;
    private Button mButton;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar=(Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEmail=(EditText)findViewById(R.id.enter_email);
        mPwd=(EditText)findViewById(R.id.enter_pwd);
        mButton=(Button) findViewById(R.id.login_try);
        mAuth=FirebaseAuth.getInstance();
        mProgress=new ProgressDialog(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String pwd=mPwd.getText().toString().trim();
                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pwd)) {
                    mProgress.setTitle("Loging In");
                    mProgress.setMessage("Please wait while we Login");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();
                    login_user(email, pwd);
                }
            }
        });
    }

    private void login_user(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //  Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            mProgress.hide();
                            //Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this,"Login Falied",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            mProgress.dismiss();
                            Intent in=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }

                        // ...
                    }
                });
    }
}
