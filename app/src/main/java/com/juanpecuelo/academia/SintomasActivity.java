package com.juanpecuelo.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class SintomasActivity extends AppCompatActivity {
    private static final String URL_ACCOUNT = Constantes.IP + "/login/updateNewAccount.php";
    private static final String URL_INSERT = Constantes.IP + "/login/insertUser.php";

    private ListView listView;
    private SessionManager sm;
    private Utiles utiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);
        sm = new SessionManager(getApplicationContext());
        utiles = new Utiles(SintomasActivity.this);
        final String[] arraySintomas = getResources().getStringArray(R.array.sintomas);
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
                vincularUsuarioCategoria(Constantes.CATEGORIA_FELICIDAD);
                if (listView.getCheckedItemCount() != 0) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        if (listView.isItemChecked(i)) {
                           //TODO arreglar todo esto
                            if (i == 0 || i == 1 || i == 4 || i == 6) {
                                vincularUsuarioCategoria(Constantes.CATEGORIA_DEPRESION);
                                vincularUsuarioCategoria(Constantes.CATEGORIA_AUTOESTIMA);
                                vincularUsuarioCategoria(Constantes.CATEGORIA_SESIONES);
                            } else if (i == 5 || i == 7) {
                                vincularUsuarioCategoria(Constantes.CATEGORIA_CONCENTRACION);
                            } else if (i == 9) {
                                vincularUsuarioCategoria(Constantes.CATEGORIA_CULPABILIDAD);
                            } else if (i == 2 || i == 7) {
                                vincularUsuarioCategoria(Constantes.CATEGORIA_LECCIONES);
                            } else if (i == 3) {
                                vincularUsuarioCategoria(Constantes.CATEGORIA_DEPRESION);
                                vincularUsuarioCategoria(Constantes.CATEGORIA_CULPABILIDAD);
                            }
                            else if(i == 10){
                                vincularUsuarioCategoria(Constantes.CATEGORIA_DEPRESION);
                                vincularUsuarioCategoria(Constantes.CATEGORIA_AUTOESTIMA);
                                vincularUsuarioCategoria(Constantes.CATEGORIA_SESIONES);
                                vincularUsuarioCategoria(Constantes.CATEGORIA_LECCIONES);
                                vincularUsuarioCategoria(Constantes.CATEGORIA_CONCENTRACION);
                                vincularUsuarioCategoria(Constantes.CATEGORIA_CULPABILIDAD);
                            }
                        }
                    }
                    utiles.toast("Tienes nuevos módulos para leer");
                    setNewAccount(view);
                    Intent intent = new Intent(getApplicationContext(), MenuPrincipalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }

    public void setNewAccount(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, URL_ACCOUNT, new Response.Listener<String>() {
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
                params.put("id_usuario", sm.getId()+ "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SintomasActivity.this);
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
                params.put("id_usuario", sm.getId() + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }




}