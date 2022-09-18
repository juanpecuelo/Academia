package com.example.academia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MiCuentaActivity extends AppCompatActivity {
    private static final String URL_BORRAR_USUARIO = Constantes.IP + "/login/borrarUsuario.php";
    private static final String URL_PDFS_DESBLOQUEADOS = Constantes.IP + "/login/getPdfsDesbloqueados.php";


    private ListView listView;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_reusable);
        String[] opciones = {"Módulos desbloqueados: ", "Email: ", "Cambiar email", "Cambiar contraseña", "Borrar usuario"};
        getPdfsDesbloqueados(opciones);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item_reusable, opciones);
        listView = findViewById(R.id.listViewReusable);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 2:

                        break;
                    case 4:
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
        titulo.setText("¿Estás seguro?");
        TextView apoyo = view.findViewById(R.id.dialogTxtApoyo);
        apoyo.setText("Si borras tu cuenta, perderás todo tu progreso");
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
                toastError(getResources().getString(R.string.error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER, 0);
                params.put("id_usuario", sp.getInt("user_id", -1) + "");
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
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(LoginActivity.HAS_LOGGED_IN);
                editor.commit();
                Intent intent = new Intent(MiCuentaActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                toastCorrecto("Se ha borrado tu cuenta con éxito");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastError(getResources().getString(R.string.error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER, 0);
                params.put("id_usuario", sp.getInt("user_id", -1) + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MiCuentaActivity.this);
        requestQueue.add(request);
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