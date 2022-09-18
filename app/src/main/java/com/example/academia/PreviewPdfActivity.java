package com.example.academia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    private static final String URL = Constantes.IP+"/login/update.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_pdf);
        int id_categoria;
        int id_pdf;

        ImageButton image = findViewById(R.id.image);
        TextView title = findViewById(R.id.title);
        TextView introduction = findViewById(R.id.introduction);
        Button boton = findViewById(R.id.btnEntendido);
        //boton.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        id_categoria = extras.getInt("id_categoria");
        id_pdf = extras.getInt("id");
        if(extras.getBoolean("boton_activado")){
            boton.setVisibility(View.VISIBLE);
        }
        Glide.with(this).load(extras.get("image")).into(image);
        //holder.imageButton.setBackground(holder.imageButton.getDrawable());;
        title.setText(extras.getString("title"));
        introduction.setText(extras.getString("introduction"));
        String path = extras.getString("pdf");

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unlockNextPdf(view, id_categoria, id_pdf);
                //TODO se presiona el botón, se envía un boolean extra al activity de SelectorPdfActivity,
                // en el onRestart, si el extra es positivo, se cierra esa activity. Mientras tanto, al
                // haber presionado el botón, se envía a SelectorPdfActivity donde se enseña el nuevo pdf.

                toastCorrecto("¡Se ha desbloqueado un nuevo artículo!");
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(MainActivity.PREFS_ULTIMA_CATEGORIA, id_categoria);
                editor.commit();
                Intent intent = new Intent(PreviewPdfActivity.this, SelectorPdfActivity.class);
                boton.setClickable(false);
            }
        });



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PdfView.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }
    public void unlockNextPdf(View view, int id_categoria, int id_pdf){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(PreviewPdfActivity.this, response, Toast.LENGTH_SHORT).show();
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
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_USER, 0);
                params.put("id_categoria", (id_categoria)+"");
                params.put("id_pdf", id_pdf+"" +
                        "");
                params.put("id_usuario",sp.getInt("user_id",-1)+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PreviewPdfActivity.this);
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

}
