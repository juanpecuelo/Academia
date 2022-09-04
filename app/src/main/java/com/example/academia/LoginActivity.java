package com.example.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {


    //TODO
    // no funciona el boton de siguiente en el teclado
    public static final String PREFS_NAME = "already_login";
    public static final String HAS_LOGGED_IN = "has_logged_in";
    public static final String PREFS_USER = "user";
    private EditText etEmail, etContrasena;
    private String email, contrasena;
    //private static final String URL = "http://10.0.2.2/login/login.php";
    private static final String URL = "http://192.168.1.18/login/login.php";
    private CheckBox recuerdame;
    private SharedPreferences.Editor editor;

    private void guardarRecuerdame() {
        SharedPreferences sp = getSharedPreferences("datos", 0);
        editor = sp.edit();
        String email = etEmail.getText().toString();
        String contrasena = etContrasena.getText().toString();
        boolean isChecked = recuerdame.isChecked();
        editor.putString("email", email);
        editor.putString("contrasena", contrasena);
        editor.putBoolean("checked", isChecked);
        editor.commit();
    }

    private void borrarRecuerdame() {
        SharedPreferences sp = getSharedPreferences("datos", 0);
        editor = sp.edit();
        editor.putString("email", "");
        editor.putString("contrasena", "");
        editor.putBoolean("checked", false);
        editor.commit();
    }

    private void cargarRecuerdame() {
        SharedPreferences sp = getSharedPreferences("datos", 0);
        etEmail.setText(sp.getString("email", ""));
        etContrasena.setText(sp.getString("contrasena", ""));
        recuerdame.setChecked(sp.getBoolean("checked", false));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etContrasena = findViewById(R.id.etContrasena);
        TextView tvHacerRegistro = findViewById(R.id.tvRegistrateAqui);
        Button btnIngresar = findViewById(R.id.btIngresar);
        recuerdame = findViewById(R.id.cbRecuerdame);
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

    private boolean comprobarDatos() {
        String s;
        try {
            s = email.split("@")[1];
            if (!s.contains(".com") && !(s.contains(".es"))) {
                toastError(getResources().getString(R.string.email_no_valido));
                return false;
            }
            if (email.equals("") || contrasena.equals("")) {
                toastError(getResources().getString(R.string.campos_vacios));
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            toastError(getResources().getString(R.string.email_no_valido));
            return false;
        } catch (Exception e) {
            toastError(getResources().getString(R.string.error));
            return false;
        }
        return true;
    }


    public void login(View view) {
        email = etEmail.getText().toString().trim();
        contrasena = etContrasena.getText().toString().trim();
        if (comprobarDatos()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        if (recuerdame.isChecked()) {
                            guardarRecuerdame();
                        } else {
                            borrarRecuerdame();
                        }
                        SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean(HAS_LOGGED_IN, true);
                        edit.commit();
                        establecerId();
                        Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        toastError(getResources().getString(R.string.email_contra_incorrectos));
                    } else {
                        System.out.println(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    toastError(error.toString().trim());
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
    }
    public void establecerId() {
        final String urlId = "http://192.168.1.18/login/getId.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                Log.i("tagconvertstr","["+response+"]");
                                JSONObject object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                System.out.println(id);
                                SharedPreferences sp = getSharedPreferences(PREFS_USER, 0);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putInt("user_id", id);
                                edit.commit();
                            }

                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastError(error.toString().trim());
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