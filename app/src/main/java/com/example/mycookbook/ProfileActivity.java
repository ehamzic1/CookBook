package com.example.mycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements  View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogOut;
    private String userEmail, userFullName, userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        userEmail = user.getEmail();
        userFullName = userEmail.substring(0, userEmail.indexOf('@'));
        try{
            userName = userFullName.substring(0, userEmail.indexOf('.'));
        }
        catch(Exception e){
            userName = userFullName;
        }

        textViewUserEmail.setText(("Welcome " + capitalize(userName) + "!"));
        textViewUserEmail.setGravity(Gravity.CENTER);

        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        buttonLogOut.setOnClickListener(this);
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
    @Override
    public void onClick(View view){
        if(view == buttonLogOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        }
    }
}
