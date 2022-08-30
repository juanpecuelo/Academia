package com.example.academia;

import android.graphics.pdf.PdfDocument;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.util.ArrayList;

public class PdfClase{
    private ImageButton imagen;
    private String título;
    private String introducción;
    private final ArrayList<File> listaPdf;
    private Button yaLeido;

    public PdfClase(ArrayList<File> listaPdf, String titulo) {
        this.listaPdf = listaPdf;
        this.título=titulo;
    }

    public PdfClase(ArrayList<File> listaPdf, ImageButton imagen, String título, String introducción) {
        this.listaPdf = listaPdf;
        this.imagen = imagen;
        this.título = título;
        this.introducción = introducción;
    }

    public ArrayList<File> getListaPdf() {
        return new ArrayList<>(listaPdf);
    }

    public ImageButton getImagen() {
        return imagen;
    }

    public void setImagen(ImageButton imagen) {
        this.imagen = imagen;
    }

    public String getTítulo() {
        return título;
    }

    public void setTítulo(String título) {
        this.título = título;
    }

    public String getIntroducción() {
        return introducción;
    }

    public void setIntroducción(String introducción) {
        this.introducción = introducción;
    }

    public Button getYaLeido() {
        return yaLeido;
    }

    public void setYaLeido(Button yaLeido) {
        this.yaLeido = yaLeido;
    }
}
