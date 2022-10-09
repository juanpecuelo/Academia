package com.juanpecuelo.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import clases.Constantes;
import clases.Utiles;

public class RegisterActivity extends AppCompatActivity {

    //TODO
    //  no funciona el botón de siguiente en el teclado

    private EditText etMail, etContrasena, etContrasena2;
    private static final String URL = Constantes.IP+"/login/register.php";
    private String email, contrasena, contrasena2;
    private TextInputLayout textInputLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etMail = findViewById(R.id.etRegistrarUsuario);
        etContrasena = findViewById(R.id.etRegistrarContrasena);
        etContrasena2 = findViewById(R.id.etRegistrarContrasena2);
        Button btnRegistro = findViewById(R.id.btRegistrar);
        textInputLayout = findViewById(R.id.textInputLayoutEmailRegistro);
        TextView tvHacerLogin = findViewById(R.id.tvLoginAqui);
        email = contrasena = contrasena2 = "";
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar(view);
            }
        });
        tvHacerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }
    public void registrar(View view){
        Utiles utiles = new Utiles(RegisterActivity.this);
        email=etMail.getText().toString().trim();
        contrasena=etContrasena.getText().toString().trim();
        contrasena2=etContrasena2.getText().toString().trim();
            textInputLayout.setErrorEnabled(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Utiles utiles = new Utiles(RegisterActivity.this);
                    if (response.equals("success")) {
                        utiles.toast(getResources().getString(R.string.ya_registrado));
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        utiles.toast(getResources().getString(R.string.error));
                        System.out.println(response);
                    }else{
                        utiles.toast(response);
                        System.out.println(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utiles.toast(error.toString().trim());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String, String> data = new HashMap<>();
                    data.put("email",email);
                    data.put("password",contrasena);
                    data.put("contrasena2", contrasena2);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    public void login(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
