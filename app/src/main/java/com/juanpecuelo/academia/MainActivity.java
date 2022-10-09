package com.juanpecuelo.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import clases.SessionManager;


public class MainActivity extends AppCompatActivity {
    private static final int TIME_OUT = 1000;
    private SessionManager sm;
    // TODO pedir al usuario si quiere desactivar el wifi una vez visualizado el pdf
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sm = new SessionManager(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean hasLoggedIn = sm.getLogin();
                Intent intent;
                if (hasLoggedIn) {
                    intent = new Intent(MainActivity.this, MenuPrincipalActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }

}