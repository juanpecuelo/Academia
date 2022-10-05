package com.juanpecuelo.academia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.AdapterPreviewPdf;
import clases.Constantes;
import clases.Pdf;

public class SelectorPdfActivity extends AppCompatActivity {
    //TODO
    // hacer un selector de pdf tipo instagram que gire hacia la derecha
    // eliminar el botón del último pdf

    private List<Pdf> listaTextos;
    private int idCategoria;

    private HorizontalInfiniteCycleViewPager infiniteViewPager;
    private AdapterPreviewPdf adapter;

    private static final String BASE_URL = Constantes.IP + "/login/getPdfs.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infinite_cycle_viewpager);
        //TODO con el auto scroll, hacer que hasta que llegue al final haga autoscroll rápido
        infiniteViewPager = findViewById(R.id.viewPager);
        infiniteViewPager.setAdapter(adapter);
        TextView nombreCategoria = findViewById(R.id.nombreCategoriaViewPager);
        //LinearLayout linearLayout = findViewById(R.id.viewPagerBackground);
        Bundle extras = getIntent().getExtras();
        //linearLayout.setBackgroundColor(extras.getInt("color"));
        nombreCategoria.setText(extras.getString("nombre_categoria"));
        idCategoria = extras.getInt("id_categoria");
        listaTextos = new ArrayList<>();

        getPdfs();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        listaTextos = new ArrayList<>();
        getPdfs();
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

    private void getPdfs() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                int id = Integer.parseInt(object.getString("id"));
                                String image = object.getString("image");
                                String title = object.getString("title");
                                String introduction = object.getString("introduction");
                                String pdf = object.getString("pdf");
                                Pdf texto = new Pdf(id, image, title, introduction, pdf, 0, idCategoria);
                                listaTextos.add(texto);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        adapter = new AdapterPreviewPdf(SelectorPdfActivity.this, listaTextos);
                        infiniteViewPager.setAdapter(adapter);
                        infiniteViewPager.notifyDataSetChanged();
                        infiniteViewPager.setCurrentItem(listaTextos.size()-1);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastError(error.toString().trim());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER, 0);
                Bundle extras = getIntent().getExtras();
                int id = extras.getInt("id_categoria");
                params.put("id_categoria", id + "");
                params.put("id_usuario", sp.getInt("user_id", -1) + "");
                return params;
            }
        };
        Volley.newRequestQueue(SelectorPdfActivity.this).add(stringRequest);
    }
}
