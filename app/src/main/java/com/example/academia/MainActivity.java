package com.example.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_OUT=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                boolean hasLoggedIn = sp.getBoolean("has_logged_in", false);
                Intent intent;
                if(hasLoggedIn){
                    intent = new Intent(MainActivity.this, MenuPrincipalActivity.class);
                }else{
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }
}