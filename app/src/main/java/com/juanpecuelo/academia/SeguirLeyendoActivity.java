package com.juanpecuelo.academia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import clases.Constantes;
import clases.SessionManager;
import clases.Utiles;

public class SeguirLeyendoActivity extends AppCompatActivity {

    private static final String BASE_URL = Constantes.IP + "/login/getUltimaCategoria.php";
    private ImageButton image;
    private TextView name;
    private TextView descripcion;
    private TextView porcentaje;
    private int idCategoria;

    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguir_leyendo);
        sm = new SessionManager(getApplicationContext());
        image = findViewById(R.id.seguirLeyendoimageButtonCategoria);
        name = findViewById(R.id.seguirLeyendo_textName);
        descripcion = findViewById(R.id.seguirLeyendo_textDescripcion);
        porcentaje = findViewById(R.id.txtPorcentajeSeguirLeyendo);

        TextView seguirLeyendo = findViewById(R.id.marqueeSeguirLeyendo);
        seguirLeyendo.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        seguirLeyendo.setSelected(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bmnSeguirLeyendo);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bmnMenuPrincipal) {
                    Intent intent = new Intent(SeguirLeyendoActivity.this, MenuPrincipalActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
                return false;
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectorPdfActivity.class);
                intent.putExtra("id_categoria", idCategoria);
                startActivity(intent);
            }
        });
        getUltimaCategoria();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUltimaCategoria();
    }


    private void getUltimaCategoria() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                idCategoria = Integer.parseInt(object.getString("id"));
                                Glide.with(getApplicationContext()).load(object.getString("image")).into(image);
                                name.setText(object.getString("nombre"));
                                descripcion.setText(object.getString("descripcion"));
                                int num = object.getInt("unlocked");
                                porcentaje.setText(num+" módulo"+(num!=1?"s":"")+" desbloqueado"+(num!=1?"s":""));
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Utiles utiles = new Utiles(SeguirLeyendoActivity.this);
                utiles.toast(error.toString().trim());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario", sm.getId() + "");
                params.put("id_ultima_categoria", sm.getUltimaCategoria() + "");
                return params;
            }
        };
        Volley.newRequestQueue(SeguirLeyendoActivity.this).add(stringRequest);
    }



}