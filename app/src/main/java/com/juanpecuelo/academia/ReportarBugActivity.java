package com.juanpecuelo.academia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ReportarBugActivity extends AppCompatActivity {

    private static final String BASE_URL = Constantes.IP + "/login/sendBug.php";

    private EditText editTextBug;
    private SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_bugs);
        sm = new SessionManager(getApplicationContext());
        editTextBug = findViewById(R.id.etBugs);
        Button buttonEnviar = findViewById(R.id.buttonBug);
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBug(editTextBug.getText().toString());
            }
        });
    }

    private void sendBug(String mensaje) {
        Utiles utiles = new Utiles(ReportarBugActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if (response.equals("OK\n")) {
                            utiles.toast(getString(R.string.gracias_bug));
                            editTextBug.setText("");
                        } else if (response.equals("ERROR")) {
                            utiles.toast(getString(R.string.error));
                        } else {
                            utiles.toast(response);
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
                params.put("id_usuario", sm.getId() + "");
                params.put("bug", mensaje);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ReportarBugActivity.this);
        requestQueue.add(stringRequest);
    }

}