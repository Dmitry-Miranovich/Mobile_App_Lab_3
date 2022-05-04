package com.example.lab3.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.HomeFragment;
import com.example.lab3.ProfileFragment;
import com.example.lab3.R;
import com.example.lab3.db.ProfileSQLiteHelper;
import com.example.lab3.db.users.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private ProfileSQLiteHelper helper;


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        Button b = findViewById(R.id.login_button);
        b.setOnClickListener(view -> {
            login();
        });
        TextView login = findViewById(R.id.login_registration_label);
        login.setOnClickListener(view ->{
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        helper = new ProfileSQLiteHelper(this);
        db = helper.getWritableDatabase();
    }

    private void login(){
        TextView email_view = findViewById(R.id.login_email_input);
        TextView password_view = findViewById(R.id.login_password_input);
        String email = email_view.getText().toString();
        String password = password_view.getText().toString();
        if(!email.equals("") && !password.equals("")){
            String hex = email + password;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                final byte[] hash = md.digest(hex.getBytes());
                StringBuilder res = new StringBuilder();
                for(byte b : hash){
                    res.append(String.format("%02x", b));
                }
                Profile profile = helper.getProfile(db, res.toString());
                if(profile != null){
                    System.out.println(profile);
                    HomeFragment.cur_profile = profile;
                    ProfileFragment.profile = profile;
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }else{
                    System.out.println("ERROR : person is null");
                    Toast.makeText(getApplicationContext(), "Email or password are invalid", Toast.LENGTH_SHORT).show();
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Email or password are not written", Toast.LENGTH_SHORT).show();
        }
    }
}
