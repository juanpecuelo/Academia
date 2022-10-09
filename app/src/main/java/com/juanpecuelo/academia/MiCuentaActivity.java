package com.juanpecuelo.academia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import clases.Constantes;
import clases.SessionManager;
import clases.Utiles;

public class MiCuentaActivity extends AppCompatActivity {
    private static final String URL_BORRAR_USUARIO = Constantes.IP + "/login/borrarUsuario.php";
    private static final String URL_PDFS_DESBLOQUEADOS = Constantes.IP + "/login/getPdfsDesbloqueados.php";

    private SessionManager sm;
    private Utiles utiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_reusable);
        sm = new SessionManager(getApplicationContext());
        utiles = new Utiles(MiCuentaActivity.this);
        String[] opciones = getResources().getStringArray(R.array.opciones_mi_cuenta);
        getPdfsDesbloqueados(opciones);
        TextView txtTab = findViewById(R.id.reusableTextView);
        txtTab.setText(getResources().getString(R.string.mi_cuenta));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_reusable, opciones);
        ListView listView = findViewById(R.id.listViewReusable);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 2:

                        break;
                    case 0:
                        mostrarDialogo(view);
                        break;
                }
            }
        });
    }

    public void mostrarDialogo(View view2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MiCuentaActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView titulo = view.findViewById(R.id.dialogTxtTitulo);
        titulo.setText(getResources().getString(R.string.estas_seguro));
        TextView apoyo = view.findViewById(R.id.dialogTxtApoyo);
        apoyo.setText(getResources().getString(R.string.pierdes_progreso));
        Button aceptar = view.findViewById(R.id.dialogBtnAceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarUsuario(view2);
                dialog.dismiss();
            }
        });
        Button cancelar = view.findViewById(R.id.dialogBtnCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void getPdfsDesbloqueados(String [] opciones) {
        StringRequest request = new StringRequest(Request.Method.POST, URL_PDFS_DESBLOQUEADOS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int num = object.getInt("pdfs_desbloqueados");
                        opciones[0] += num;
                        System.out.println(opciones[0]);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("id_usuario", sm.getId() + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MiCuentaActivity.this);
        requestQueue.add(request);
    }


    public void borrarUsuario(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, URL_BORRAR_USUARIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sm.setLogin(false);
                Intent intent = new Intent(MiCuentaActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                utiles.toast("Se ha borrado tu cuenta con éxito");
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
        RequestQueue requestQueue = Volley.newRequestQueue(MiCuentaActivity.this);
        requestQueue.add(request);
    }




}