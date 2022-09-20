package com.example.academia;

public class Categoria {
    private final int id;
    private final String image;
    private final String nombre;
    private final String descripcion;
    private int progressBarPorcentaje;
    private final int color;
    public Categoria(int id, String image, String nombre, String descripcion, int progressBarPorcentaje, int color) {
        this.id = id;
        this.image = image;
        this.nombre = nombre;
        this.progressBarPorcentaje = progressBarPorcentaje;
        this.descripcion = descripcion;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getProgressBarPorcentaje() {
        return progressBarPorcentaje;
    }

    public void setProgressBarPorcentaje(int progressBarPorcentaje) {
        this.progressBarPorcentaje = progressBarPorcentaje;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getNombre() {
        return nombre;
    }
}
