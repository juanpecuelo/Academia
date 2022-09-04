package com.example.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    private static final String[] arrayEnfermedades = {"Depresión","Fobia social","Déficit de atención","Ansiedad"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfermedades);
        ArrayAdapter<String> adapter;
        listView = findViewById(R.id.lvEnfermedades);
        TextView textView = findViewById(R.id.tvIrASintomas);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arrayEnfermedades);
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
                    bf.append(listView.getItemAtPosition(i));
                    if(i < listView.getCount()-1){
                        bf.append("\n");
                    }
                }
            }
            toastCorrecto(bf.toString());
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private void toastCorrecto(String msg){
        LayoutInflater inflater = getLayoutInflater();
        View view =inflater.inflate(R.layout.toast_ok, findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
    private void toastError(String msg){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error, findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastError);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}