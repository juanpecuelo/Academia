package com.example.academia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;


public class PreviewPdfActivity extends AppCompatActivity {

    //TODO
    //  cambiar la el tamaño del texto si la longitud supera X carácteres
    private static final String URL = "http://"+Constantes.IP+"/login/update.php";
    private int id_categoria;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_pdf);
        ImageButton image = findViewById(R.id.image);
        TextView title = findViewById(R.id.title);
        TextView introduction = findViewById(R.id.introduction);
        Button boton = findViewById(R.id.btnEntendido);
        boton.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        id_categoria = extras.getInt("id_categoria");
        Glide.with(this).load(extras.get("image")).into(image);
        title.setText(extras.getString("title"));
        introduction.setText(extras.getString("introduction"));


        String path = extras.getString("pdf");

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unlockNextPdf(view);
                //TODO por que el toast se hace con el metodo?
                // ¿cambiar el toast a blanco?
                Toast.makeText(PreviewPdfActivity.this, "¡Se ha desbloqueado un nuevo artículo!", Toast.LENGTH_SHORT).show();
            }
        });



        image.setOnClickListener(new View.OnClickListener() {
            // TODO esto es lo que hay que cambiar para que se vea el PDF sin descargarlo
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                intent.putExtra("path", path);
                //startActivity(intent);
                    //Thread.sleep(2000);

                //boton.setVisibility(View.VISIBLE);
            }
        });
    }
    public void unlockNextPdf(View view){

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(PreviewPdfActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PreviewPdfActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER, 0);
                params.put("id_categoria", (id_categoria)+"");
                params.put("id_usuario",sp.getInt("user_id",-1)+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PreviewPdfActivity.this);
        requestQueue.add(request);
    }

}
