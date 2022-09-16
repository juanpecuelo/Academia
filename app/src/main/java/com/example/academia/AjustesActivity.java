package com.example.academia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;

public class AjustesActivity extends AppCompatActivity {

    //TODO
    //  session manager para cerrar la sesión

    private static final String URL_BORRAR_USUARIO = Constantes.IP+"/login/borrarUsuario.php";
    private static final String[] ajustes = {"Mi cuenta", "Modo oscuro","Cerrar sesión","Borrar cuenta"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_reusable);

        TextView textView = findViewById(R.id.reusableTextView);
        textView.setText(getResources().getString(R.string.ajustes));
        ListView listView = findViewById(R.id.listViewReusable);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_reusable, ajustes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s ="";
                switch (i){
                    case 1:
                        //Intent intent = new Intent(getApplicationContext(), MiCuentaActivity.class);
                        //startActivity(intent);
                        break;
                    case 2:
                        SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_NAME,0);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.remove(LoginActivity.HAS_LOGGED_IN);
                        edit.commit();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(AjustesActivity.this, "Se ha cerrado sesión con éxito", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        mostrarDialogo(view);
                        break;

                }
            }
        });
    }
    public void borrarUsuario(View view){
        StringRequest request = new StringRequest(Request.Method.POST, URL_BORRAR_USUARIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(LoginActivity.HAS_LOGGED_IN);
                editor.commit();
                Intent intent = new Intent(AjustesActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                toastCorrecto("Se ha borrado tu cuenta con éxito");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastError(getResources().getString(R.string.error));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER,0);
                params.put("id_usuario",sp.getInt("user_id",-1)+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AjustesActivity.this);
        requestQueue.add(request);
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
    public void mostrarDialogo(View view2){
        AlertDialog.Builder builder = new AlertDialog.Builder(AjustesActivity.this);
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
}