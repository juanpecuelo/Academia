package com.example.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PdfView extends AppCompatActivity {
    private PDFView pdfView;
    //TODO
    // hacer que se visualice el pdf sin descargarlo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view);
        pdfView = findViewById(R.id.pdf_viewer);
        Bundle extras = getIntent().getExtras();
        String path =extras.getString("path");
       // File file = new File(path);

        FileLoader.with(this)
                .load(path)
                .fromDirectory("PDFFiles",FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        File file = response.getBody();
                        pdfView.fromFile(file).load();
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Toast.makeText(PdfView.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}


