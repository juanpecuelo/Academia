package com.example.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {
    private static final int TIME_OUT = 1000;
    public static final String PREFS_ULTIMA_CATEGORIA = "id_ultima_categoria";
    // TODO pedir al usuario si quiere desactivar el wifi una vez visualizado el pdf
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                boolean hasLoggedIn = sp.getBoolean(LoginActivity.HAS_LOGGED_IN, false);
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