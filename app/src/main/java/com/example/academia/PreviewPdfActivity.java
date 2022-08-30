package com.example.academia;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PreviewPdfActivity extends AppCompatActivity {
    //TODO
    // el pdfList tendría que ir dentro de la clase PdfClase
    // la idea es hacer un arraylist con todos los pdfs de este tema en concreto
    // sería interesante intentar hacer una clase previewpdf para cualquier tema

    private MainAdapter adapter;
    private RecyclerView recyclerView;

    private PdfClase pdfFelicidad;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ArrayList<File> pdfList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            File f = new File("C:\\Users\\Estudio\\AndroidStudioProjects\\Academia\\app\\src\\main\\assets\\pdf\\felicidad\\LaFelicidad(" + i + ").pdf");
            pdfList.add(f);
        }
        //TODO
        // un poco redundante todo esto
        pdfFelicidad = new PdfClase(pdfList, "La Felicidad");
        displayPdf(pdfList);
    }



    public void displayPdf(ArrayList<File> pdfList) {
        recyclerView = findViewById(R.id.rvPreview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        AssetManager am = getAssets();
        adapter = new MainAdapter(this, pdfList);
        recyclerView.setAdapter(adapter);
    }
}
