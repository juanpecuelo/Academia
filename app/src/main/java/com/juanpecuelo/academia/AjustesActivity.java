package com.juanpecuelo.academia;

import static android.app.UiModeManager.MODE_NIGHT_YES;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import clases.SessionManager;
import clases.Utiles;


public class AjustesActivity extends AppCompatActivity {

    //TODO
    //  session manager para cerrar la sesión


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_reusable);
        final String[] ajustes = getResources().getStringArray(R.array.opciones_ajustes);
        TextView textView = findViewById(R.id.reusableTextView);

        final SessionManager sm = new SessionManager(getApplicationContext());
        Utiles utiles = new Utiles(this);

        textView.setText(getResources().getString(R.string.ajustes));
        ListView listView = findViewById(R.id.listViewReusable);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_reusable, ajustes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(getApplicationContext(), MiCuentaActivity.class);
                        startActivity(intent);
                        break;
                    case 1:

                        System.out.println("ffefeefefef");
                        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                        boolean nightMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
                        int darkMode = MODE_NIGHT_YES;
                        if(nightMode){
                            sm.setDarkMode(true);
                        }else{
                            darkMode = MODE_NIGHT_NO;
                            sm.setDarkMode(false);
                        }
                            AppCompatDelegate.setDefaultNightMode(darkMode);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), ReportarBugActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        sm.setLogin(false);
                        utiles.toast(getString(R.string.exito_cerrar_sesion));
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}