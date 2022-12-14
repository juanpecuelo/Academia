package com.juanpecuelo.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import clases.Constantes;
import clases.SessionManager;
import clases.Utiles;

public class LoginActivity extends AppCompatActivity {


    //TODO
    // no funciona el boton de siguiente en el teclado
    private EditText etEmail, etContrasena;
    private String email, contrasena;
    private static final String URL = Constantes.IP + "/login/login.php";
    private static final String URL_ID = Constantes.IP + "/login/getId.php";
    private CheckBox cbRecuerdame;
    private SessionManager sm;
    private Utiles utiles;

    private void cargarRecuerdame() {
        etEmail.setText(sm.getEmail());
        etContrasena.setText(sm.getContrasena());
        cbRecuerdame.setChecked(sm.getCheckedRecuerdame());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sm = new SessionManager(getApplicationContext());
        utiles = new Utiles(LoginActivity.this);
        etEmail = findViewById(R.id.etEmail);
        etContrasena = findViewById(R.id.etContrasena);
        TextView tvHacerRegistro = findViewById(R.id.tvRegistrateAqui);
        Button btnIngresar = findViewById(R.id.btIngresar);
        cbRecuerdame = findViewById(R.id.cbRecuerdame);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
        tvHacerRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });

        email = contrasena = "";
        cargarRecuerdame();

    }
    public void login(View view) {
        email = etEmail.getText().toString().trim();
        contrasena = etContrasena.getText().toString().trim();
        boolean recuerdame = cbRecuerdame.isChecked();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int newAccount=0;
                String[] respuesta = response.split("@");
                if (respuesta[0].equals("success")) {
                    if (recuerdame) {
                        sm.setLogin(true);
                        sm.guardarRecuerdame(email, contrasena, true);
                    } else {
                        sm.borrarRecuerdame();
                    }
                    try {
                        JSONArray array = new JSONArray(respuesta[1]);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            newAccount= object.getInt("is_new_account");
                            // System.out.println(newAccount + " arriba");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    establecerId();
                    Intent intent;
                    if (newAccount == 1) {
                        intent = new Intent(LoginActivity.this, SintomasActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
                    }
                    startActivity(intent);
                    finish();
                } else if (respuesta[0].equals("failure")) {
                    utiles.toast(getResources().getString(R.string.email_contra_incorrectos));
                } else {
                    utiles.toast(getResources().getString(R.string.campos_vacios));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utiles.toast(error.toString().trim());
                System.out.println(error.toString().trim());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", contrasena);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



    public void establecerId() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //                      System.out.println(response + "<--- respuesta id");
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                System.out.println(id);
                                sm.setId(id);
//                                System.out.println(sp.getInt("user_id", -1));
                            }

                        } catch (Exception e) {
                            System.out.println(e + " excepci??n");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utiles.toast(error.toString().trim());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        Volley.newRequestQueue(LoginActivity.this).add(stringRequest);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


}   