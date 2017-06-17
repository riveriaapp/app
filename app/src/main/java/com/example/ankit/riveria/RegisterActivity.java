package com.example.ankit.riveria;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText mDisplayName,mEmail,mPassword;
    private Button mCreateBtn;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       //For doing any thing on Authentication we need to get it's Instance
        mAuth=FirebaseAuth.getInstance();

        //To show the Custon toolbar
        mToolbar=(Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        //For getting the back arrow button on top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Getting all the Values entered in the Edit Text Section of the Layout file
        mDisplayName=(EditText)findViewById(R.id.reg_name);
        mEmail=(EditText)findViewById(R.id.reg_email);
        mPassword=(EditText)findViewById(R.id.reg_pwd);
        mCreateBtn=(Button)findViewById(R.id.reg_create_acc);

        //Initializing the Progress Dialog
        mProgress=new ProgressDialog(this);

        //on Clicking button following function will execute
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Converting whatever is stored in EditText objects to strings
                String display_name=mDisplayName.getText().toString().trim();
                String email=mEmail.getText().toString().trim();
                String pwd=mPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(display_name)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pwd)) {
                    if(pwd.length()<=6){
                        Toast.makeText(RegisterActivity.this,"Password needs to be atleast 6 char. long",Toast.LENGTH_SHORT).show();
                    }

                    //Customizing the Progress Bar
                        mProgress.setTitle("Registering User");
                        mProgress.setMessage("Please wait while we create ur Account");
                        mProgress.setCanceledOnTouchOutside(false);
                        mProgress.show();
                    //Call Fire base Function to register user
                        register_user(display_name, email, pwd);

                }
            }
        });
    }

    private void register_user(String display_name, String email, String password) {
        // Followinf Function is defalut function in Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            mProgress.dismiss();
                            Toast.makeText(RegisterActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            Intent main_intent=new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(main_intent);
                            finish();
                        }else{
                            mProgress.hide();
                            Toast.makeText(RegisterActivity.this," no Success",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
