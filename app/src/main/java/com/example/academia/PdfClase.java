package com.example.academia;

import android.graphics.pdf.PdfDocument;
import android.widget.Button;
import android.widget.ImageButton;

public class PdfClase{
    private ImageButton imagen;
    private String título;
    private String introducción;
    private PdfDocument pdf;
    private Button yaLeido;

    public PdfClase(PdfDocument pdf, ImageButton imagen, String título, String introducción) {
        this.pdf = pdf;
        this.imagen = imagen;
        this.título = título;
        this.introducción = introducción;
    }

    public void setPdf(PdfDocument pdf) {
        this.pdf = pdf;
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
