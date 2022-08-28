package com.example.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    //TODO
    // no funciona el registro

    private EditText etEmail, etContrasena;
    private String email, contrasena;
    private static final String URL = "http://10.0.2.2/login/login.php";
    //private static final String URL = "http://192.168.1.18/login/login.php";
    private CheckBox recuerdame;
    private SharedPreferences.Editor editor;

    private void guardarRecuerdame(){
        SharedPreferences sp = getSharedPreferences("datos", 0);
        editor = sp.edit();
        String email = etEmail.getText().toString();
        String contrasena = etContrasena.getText().toString();
        boolean isChecked = recuerdame.isChecked();
        editor.putString("email",email);
        editor.putString("contrasena",contrasena);
        editor.putBoolean("checked", isChecked);
        editor.commit();
    }
    private void borrarRecuerdame(){
        SharedPreferences sp = getSharedPreferences("datos",0);
        editor = sp.edit();
        editor.putString("email","");
        editor.putString("contrasena","");
        editor.putBoolean("checked", false);
        editor.commit();
    }
    private void cargarRecuerdame(){
        SharedPreferences sp = getSharedPreferences("datos",0);
        etEmail.setText(sp.getString("email",""));
        etContrasena.setText(sp.getString("contrasena",""));
        recuerdame.setChecked(sp.getBoolean("checked", false));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void login(View view){
        email = etEmail.getText().toString().trim();
        contrasena = etContrasena.getText().toString().trim();
        if(!email.equals("") && !contrasena.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                        System.out.println("Se ha registrado");
                        if(recuerdame.isChecked()){
                            guardarRecuerdame();
                        }else{
                            borrarRecuerdame();
                        }
                        startActivity(intent);
                    } else if (response.equals("failure")) {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.email_contra_incorrectos), Toast.LENGTH_SHORT).show();
                        System.out.println(getResources().getString(R.string.email_contra_incorrectos));
                    }else{
                        System.out.println(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
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
        }else{
            Toast.makeText(this, getResources().getString(R.string.campos_vacíos), Toast.LENGTH_LONG).show();
            System.out.println(getResources().getString(R.string.campos_vacíos));
        }
    }
    public void register (View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        //finish();
    }
}   