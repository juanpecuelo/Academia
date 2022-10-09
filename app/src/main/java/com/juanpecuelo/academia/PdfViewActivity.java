package com.juanpecuelo.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfViewActivity extends AppCompatActivity {
    private PDFView pdfView;
    //TODO
    // hacer que se visualice el pdf sin descargarlo


    class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL uri = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            boolean nightMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;

            pdfView.fromStream(inputStream).nightMode(nightMode)
                    .password("Your Password")
                    .load();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view);
        pdfView = findViewById(R.id.pdf_viewer);

        Bundle extras = getIntent().getExtras();
        String path = extras.getString("path");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        new RetrivePDFStream().execute(path);
    }


}



