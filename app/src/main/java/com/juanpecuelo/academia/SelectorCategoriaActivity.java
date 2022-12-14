package com.juanpecuelo.academia;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;

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

import adapters.AdapterCategoria;
import clases.Categoria;
import clases.Constantes;
import clases.SessionManager;
import clases.Utiles;

public class SelectorCategoriaActivity extends AppCompatActivity {

    private AdapterCategoria adapter;
    private RecyclerView recyclerView;

    private Parcelable recyclerViewState;

    private List<Categoria> listaCategorias;
    private SessionManager sm;

    private static final String BASE_URL = Constantes.IP + "/login/getCategorias.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_pdf);
        sm = new SessionManager(getApplicationContext());
        recyclerView = findViewById(R.id.rvPreview);
        recyclerView.setLayoutManager(new GridLayoutManager(SelectorCategoriaActivity.this, 1));

        listaCategorias = new ArrayList<>();
        getCategorias();
    }





    //TODO conseguir que se guarde el estado del recyclerview al cambiar de activity
    @Override
    protected void onPause() {
        super.onPause();
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        listaCategorias = new ArrayList<>();
        getCategorias();
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

    }

    private void getCategorias() {
        recyclerView.setHasFixedSize(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            TypedArray colors = getResources().obtainTypedArray(R.array.colors);
                            int color;
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                //Log.i("tagconvertstr","["+response+"]");
                                JSONObject object = array.getJSONObject(i);
                                int id = Integer.parseInt(object.getString("id"));
                                String image = object.getString("image");
                                String nombre = object.getString("nombre");
                                String descripcion = object.getString("descripcion");
                                int unlocked = object.getInt("unlocked");
                                color = colors.getColor(i, getResources().getColor(R.color.complementary_theme_color));
                                Categoria categoria = new Categoria(id, image, nombre, descripcion, unlocked, color);
                                listaCategorias.add(categoria);
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        adapter = new AdapterCategoria(SelectorCategoriaActivity.this, listaCategorias);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utiles utiles = new Utiles(SelectorCategoriaActivity.this);
                utiles.toast(error.toString().trim());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", sm.getId() + "");
                return params;
            }
        };
        Volley.newRequestQueue(SelectorCategoriaActivity.this).add(stringRequest);
    }
}
