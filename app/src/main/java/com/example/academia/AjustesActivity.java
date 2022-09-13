package com.example.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AjustesActivity extends AppCompatActivity {

    //TODO
    //  session manager para cerrar la sesión
    private static final String[] ajustes = {"Mi cuenta", "Modo oscuro","Cerrar sesión"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_reusable);
        TextView textView = findViewById(R.id.reusableTextView);
        textView.setText(getResources().getString(R.string.ajustes));
        ListView listView = findViewById(R.id.listViewReusable);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_reusable, ajustes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s ="";
                switch (i){
                    case 2:
                        SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_NAME,0);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.remove(LoginActivity.HAS_LOGGED_IN);
                        edit.commit();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(AjustesActivity.this, "Se ha cerrado sesión con éxito", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}