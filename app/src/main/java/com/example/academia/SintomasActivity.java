package com.example.academia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SintomasActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private static final String[] arraySintomas = {"Tristeza","Cansancio constante","Mal humor","Pena","Soledad","Dificultad para concentrarse","Apatía","Nerviosismo","Estrés"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);
        listView = findViewById(R.id.lvSintomas);
        TextView textView = findViewById(R.id.tvIrAEnfermedades);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arraySintomas);
        listView.setAdapter(adapter);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnfermedadesActivity.class);
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
        View view =inflater.inflate(R.layout.toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
    private void toastError(String msg){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastError);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}