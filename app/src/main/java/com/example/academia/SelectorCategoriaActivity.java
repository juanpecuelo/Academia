package com.example.academia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectorCategoriaActivity extends AppCompatActivity {

    private AdapterCategoria adapter;
    private RecyclerView recyclerView;

    private List<Categoria> listaCategorias;

    private static final String BASE_URL = Constantes.IP+"/login/getCategorias.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_pdf);
        recyclerView = findViewById(R.id.rvPreview);
        recyclerView.setLayoutManager(new GridLayoutManager(SelectorCategoriaActivity.this, 1));
        listaCategorias = new ArrayList<>();
        getCategorias();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        listaCategorias = new ArrayList<>();
        getCategorias();
    }

    private void getCategorias() {
        recyclerView.setHasFixedSize(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sp = getSharedPreferences("user_id", 0);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                //Log.i("tagconvertstr","["+response+"]");
                                JSONObject object = array.getJSONObject(i);
                                int id = Integer.parseInt(object.getString("id"));
                                String image = object.getString("image");
                                String nombre = object.getString("nombre");
                                String descripcion = object.getString("descripcion");
                                int unlocked = object.getInt("unlocked");
                                Categoria categoria = new Categoria(id,image,nombre,descripcion,unlocked);
                                    listaCategorias.add(categoria);
                            }

                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                        adapter = new AdapterCategoria(SelectorCategoriaActivity.this, listaCategorias);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastError(error.toString().trim());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER, 0);
                params.put("id", sp.getInt("user_id", -1)+"");
                return params;
            }
        };
        Volley.newRequestQueue(SelectorCategoriaActivity.this).add(stringRequest);
    }
}
