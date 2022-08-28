package com.example.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText etMail, etContrasena, etContrasena2;
    private Button btnRegistro;
    private static final String URL = "http://10.0.2.2/login/register.php";
    //private static final String URL = "http://192.168.1.18/login/register.php";
    private String email, contrasena, contrasena2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        etMail = findViewById(R.id.etRegistrarUsuario);
        etContrasena = findViewById(R.id.etRegistrarContrasena);
        etContrasena2 = findViewById(R.id.etRegistrarContrasena2);
        btnRegistro = findViewById(R.id.btRegistrar);
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
        email=etMail.getText().toString().trim();
        contrasena=etContrasena.getText().toString().trim();
        contrasena2=etContrasena2.getText().toString().trim();
        if(!contrasena.equals(contrasena2)){
            Toast.makeText(this, getResources().getText(R.string.clave_no_coincide), Toast.LENGTH_SHORT).show();
        }else if (!email.equals("") && !contrasena.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.ya_registrado), Toast.LENGTH_SHORT).show();
                        System.out.println("ya estás registrado");
                        btnRegistro.setClickable(false);
                    } else if (response.equals("failure")) {
                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                        System.out.println(response);
                    }else{
                        System.out.println(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.toString().trim());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String, String> data = new HashMap<>();
                    data.put("email",email);
                    data.put("password",contrasena);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
    public void login(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
