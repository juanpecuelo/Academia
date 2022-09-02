package com.example.academia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectorPdfActivity extends AppCompatActivity {
    //TODO
    // sería interesante intentar hacer una clase previewpdf para cualquier tema

    private MainAdapter adapter;
    private RecyclerView recyclerView;

    private List<Pdf> listaTextos;

    private static final String BASE_URL = "http://192.168.1.18/login/getPdfs.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_pdf);
        recyclerView = findViewById(R.id.rvPreview);
        recyclerView.setLayoutManager(new GridLayoutManager(SelectorPdfActivity.this, 3));
        listaTextos = new ArrayList<>();
        getPdfs();
    }

    private void toastError(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastError);
        txtMensaje.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    private void getPdfs() {
        recyclerView.setHasFixedSize(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                //Log.i("tagconvertstr","["+response+"]");
                                JSONObject object = array.getJSONObject(i);
                                int id = Integer.parseInt(object.getString("id"));
                                String image = object.getString("image");
                                String title = object.getString("title");
                                String introduction = object.getString("introduction");
                                String pdf = object.getString("pdf");
                                Pdf texto = new Pdf(id, image, title, introduction, pdf);
                                listaTextos.add(texto);
                            }

                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                        adapter = new MainAdapter(SelectorPdfActivity.this, listaTextos);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastError(error.toString().trim());
            }
        });
        Volley.newRequestQueue(SelectorPdfActivity.this).add(stringRequest);
    }
}
