package com.example.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EnfermedadesActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private static final String[] arrayEnfermedades = {"Depresión","Fobia social","Déficit de atención","Ansiedad"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfermedades);
        listView = findViewById(R.id.lvEnfermedades);
        TextView textView = findViewById(R.id.tvIrASintomas);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayEnfermedades);
        listView.setAdapter(adapter);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SintomasActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_done){
            StringBuffer bf = new StringBuffer("Seleccionado:\n");
            for (int i = 0; i < listView.getCount(); i++) {
                if(listView.isItemChecked(i)){
                    bf.append(listView.getItemAtPosition(i)+"\n");
                }
            }
            Toast.makeText(this,bf.toString(), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}