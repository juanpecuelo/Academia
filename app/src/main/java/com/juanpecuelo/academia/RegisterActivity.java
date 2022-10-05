package com.juanpecuelo.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import clases.Constantes;

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
        email=etMail.getText().toString().trim();
        contrasena=etContrasena.getText().toString().trim();
        contrasena2=etContrasena2.getText().toString().trim();
            textInputLayout.setErrorEnabled(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        toastCorrecto(getResources().getString(R.string.ya_registrado));
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        toastError(getResources().getString(R.string.error));
                        System.out.println(response);
                    }else{
                        toastError(response);
                        System.out.println(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    toastError(error.toString().trim());
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
