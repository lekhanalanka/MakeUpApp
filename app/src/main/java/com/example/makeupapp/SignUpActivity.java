package com.example.makeupapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_EditText, password_EditText;
    private Button signUp_Button;
    private TextView signIn_TextView;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        progressDialog = new ProgressDialog(this);
        email_EditText = findViewById(R.id.signUp_email_et);
        password_EditText = findViewById(R.id.signUp_password_et);
        signUp_Button = findViewById(R.id.signUp_button);
        signIn_TextView = findViewById(R.id.signIn_txtview);

        signUp_Button.setOnClickListener(this);
        signIn_TextView.setOnClickListener(this);

    }

    private void registerUser() {

        String email = email_EditText.getText().toString().trim();
        String password = password_EditText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            email_EditText.setError("Please Enter Email");
            email_EditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            password_EditText.setError("Please Enter Password");
            password_EditText.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if (view == signUp_Button) {
            registerUser();
        }
        if (view == signIn_TextView) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }
        closeKeyboard();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}