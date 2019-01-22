package com.example.makeupapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makeupapp.DAO.DataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    EditText feedback;
    Button button;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    public static final String MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference(MESSAGE);
        feedback = findViewById(R.id.feedBack);
        button = findViewById(R.id.submit_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fee=feedback.getText().toString().trim();
                if (TextUtils.isEmpty(fee)) {
                    feedback.setError("Please Enter comment");
                    feedback.requestFocus();
                    return;
                }
                String data = firebaseDatabase.push().getKey();
                firebaseDatabase.child(data).setValue(fee);
                Toast.makeText(FeedbackActivity.this, R.string.Comment, Toast.LENGTH_SHORT).show();
                closeKeyboard();
                finish();
                startActivity(new Intent(FeedbackActivity.this,MainActivity.class));


            }
        });

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view!=null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}
