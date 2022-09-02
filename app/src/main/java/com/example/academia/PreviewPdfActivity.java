package com.example.academia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PreviewPdfActivity extends AppCompatActivity {

    //TODO
    //  cambiar la el tamaño del texto si la longitud supera X carácteres


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_pdf);
        ImageButton image = findViewById(R.id.image);
        TextView title = findViewById(R.id.title);
        TextView introduction = findViewById(R.id.introduction);
        Button boton = findViewById(R.id.btnEntendido);
        Bundle extras = getIntent().getExtras();
        Glide.with(this).load(extras.get("image")).into(image);
        title.setText(extras.getString("title"));
        introduction.setText(extras.getString("introduction"));

        String path = extras.getString("pdf");

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                intent.putExtra("path", path);
                startActivity(intent);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boton.setVisibility(View.VISIBLE);
            }
        });
    }

}
