package com.example.academia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SintomasActivity extends AppCompatActivity {
    private static final String URL = "http://"+Constantes.IP+"/login/updateNewAccount.php";;
    private static final String[] arraySintomas = {"Tristeza", "Cansancio constante", "Mal humor", "Pena", "Soledad", "Dificultad para concentrarse", "Apatía", "Nerviosismo", "Estrés"};

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);
        ArrayAdapter<String> adapter;
        listView = findViewById(R.id.lvSintomas);
        TextView textView = findViewById(R.id.tvIrAEnfermedades);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arraySintomas);
        listView.setAdapter(adapter);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnfermedadesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button button = findViewById(R.id.buttonSintomas);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewAccount(view);
                Intent intent = new Intent(getApplicationContext(), MenuPrincipalActivity.class);
                startActivity(intent);

            }
        });
    }
    public void setNewAccount(View view){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(PreviewPdfActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastError(getResources().getString(R.string.error));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER, 0);
                params.put("id_usuario",sp.getInt("user_id", -1)+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SintomasActivity.this);
        requestQueue.add(request);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_done) {
            StringBuffer bf = new StringBuffer("Seleccionado:\n");
            for (int i = 0; i < listView.getCount(); i++) {
                if (listView.isItemChecked(i)) {
                    bf.append(listView.getItemAtPosition(i));
                    if (i < listView.getCount() - 1) {
                        bf.append("\n");
                    }
                }
            }
            toastCorrecto(bf.toString());
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
*/
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