package com.juanpecuelo.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class AjustesActivity extends AppCompatActivity {

    //TODO
    //  session manager para cerrar la sesión


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_reusable);
        final String[] ajustes = getResources().getStringArray(R.array.opciones_ajustes);
        TextView textView = findViewById(R.id.reusableTextView);
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
                        SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.remove(LoginActivity.HAS_LOGGED_IN);
                        edit.commit();
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(AjustesActivity.this, "Se ha cerrado sesión con éxito", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void toastCorrecto(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_ok, findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    private void toastError(String msg) {
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