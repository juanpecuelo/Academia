package com.example.academia;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PreviewPdfActivity extends AppCompatActivity {
    //TODO
    // el pdfList tendría que ir dentro de la clase PdfClase
    // la idea es hacer un arraylist con todos los pdfs de este tema en concreto
    // sería interesante intentar hacer una clase previewpdf para cualquier tema

    private MainAdapter adapter;
    private List<File> pdfList;
    private RecyclerView recyclerView;
    private ArrayList<PdfClase> listaPdfs;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_activity);
        listaPdfs.add(new PdfClase())
    }
}
