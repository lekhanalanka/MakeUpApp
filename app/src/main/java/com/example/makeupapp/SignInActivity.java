package com.example.makeupapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText signIn_email_et,signIn_passwd_et;
    private Button signIn_Button;
    private TextView signUp_textView;
    ProgressDialog progressDialog;
    private FirebaseAuth mfirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mfirebaseAuth = FirebaseAuth.getInstance();
        if (mfirebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        progressDialog = new ProgressDialog(this);
        signIn_email_et = findViewById(R.id.signIn_email_et);
        signIn_passwd_et = findViewById(R.id.signIn_password_et);
        signIn_Button = findViewById(R.id.signIn_button);
        signUp_textView = findViewById(R.id.signUp_txtview);

        signIn_Button.setOnClickListener(this);
        signUp_textView.setOnClickListener(this);


    }

    private void userLogin()
    {
        String email = signIn_email_et.getText().toString().trim();
        String password = signIn_passwd_et.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            signIn_email_et.setError("Please Enter Email");
            signIn_email_et.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            signIn_passwd_et.setError("Please Enter Password");
            signIn_passwd_et.requestFocus();
            return;
        }

        progressDialog.setMessage("Logging In...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mfirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == signIn_Button)
        {
            userLogin();
        }
        if (view==signUp_textView)
        {
            finish();
            startActivity(new Intent(this,SignUpActivity.class));
        }

        closeKeyboard();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view!=null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


