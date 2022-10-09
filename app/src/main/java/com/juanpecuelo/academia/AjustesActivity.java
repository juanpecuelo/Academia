package com.juanpecuelo.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        Utiles utiles = new Utiles(AjustesActivity.this);

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
                    case 2:
                        intent = new Intent(getApplicationContext(), ReportarBugActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        sm.setLogin(false);
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        utiles.toast(getString(R.string.exito_cerrar_sesion));
                        break;
                }
            }
        });
    }
}