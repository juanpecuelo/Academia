package com.juanpecuelo.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import clases.Constantes;
import clases.SessionManager;
import clases.Utiles;

public class EnfermedadesActivity extends AppCompatActivity {

    private static final String URL = Constantes.IP + "/login/updateNewAccount.php";
    private static final String URL_INSERT = Constantes.IP + "/login/insertUser.php";
    private ListView listView;
    private SessionManager sm;
    private Utiles utiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfermedades);
        sm = new SessionManager(getApplicationContext());
        utiles = new Utiles(EnfermedadesActivity.this);
        final String[] arrayEnfermedades = getResources().getStringArray(R.array.enfermedades);
        ArrayAdapter<String> adapter;
        listView = findViewById(R.id.lvEnfermedades);
        TextView textView = findViewById(R.id.tvIrASintomas);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arrayEnfermedades);
        Button button = findViewById(R.id.buttonEnfermedades);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vincularUsuarioCategoria(Constantes.CATEGORIA_FELICIDAD);
                if (listView.getCheckedItemCount() != 0) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        if (listView.isItemChecked(i)) {
                            switch (i) {
                                case 0:
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_DEPRESION);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_AUTOESTIMA);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_SESIONES);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_CULPABILIDAD);
                                    break;
                                case 1:
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_AUTOESTIMA);
                                    break;
                                case 2:
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_CONCENTRACION);
                                    break;
                                case 3:
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_CONCENTRACION);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_LECCIONES);
                                    break;
                                case 4:
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_DEPRESION);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_AUTOESTIMA);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_SESIONES);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_LECCIONES);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_CONCENTRACION);
                                    vincularUsuarioCategoria(Constantes.CATEGORIA_CULPABILIDAD);
                                    break;
                            }
                        }
                    }
                    utiles.toast("Tienes nuevos módulos para leer");
                    setNewAccount(view);
                    Intent intent = new Intent(getApplicationContext(), MenuPrincipalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            }
        });
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

    public void setNewAccount(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(PreviewPdfActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utiles.toast(getResources().getString(R.string.error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id_usuario", sm.getId()+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EnfermedadesActivity.this);
        requestQueue.add(request);
    }

    public void vincularUsuarioCategoria(int id_categoria) {

        StringRequest request = new StringRequest(Request.Method.POST, URL_INSERT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(PreviewPdfActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utiles.toast(getResources().getString(R.string.error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_categoria", (id_categoria) + "");
                params.put("id_usuario", sm.getId()+ "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }



}