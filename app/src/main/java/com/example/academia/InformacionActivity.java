package com.example.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InformacionActivity extends AppCompatActivity {

    //TODO
    //  session manager para cerrar la sesión
    // por qué el toast no es blanco y con el logo de la app como en otras activities?
    private static final String[] informacion = {"Quiénes somos","Versión "+BuildConfig.VERSION_NAME};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_reusable);
        TextView textView = findViewById(R.id.reusableTextView);
        textView.setText(getResources().getString(R.string.informacion));
        ListView listView = findViewById(R.id.listViewReusable);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_reusable, informacion);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(InformacionActivity.this, informacion[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}