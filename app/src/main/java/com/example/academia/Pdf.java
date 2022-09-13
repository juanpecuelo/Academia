package com.example.academia;

public class Pdf {
    private final int id;
    private final String image;
    private final String title;
    private final String introduction;
    private final String pdfPath;
    private final int categoriaId;
    private int unlocked;

    public Pdf(int id, String image, String title, String introduction, String pdfPath, int unlocked, int userId) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.introduction = introduction;
        this.pdfPath = pdfPath;
        this.unlocked = unlocked;
        this.categoriaId = userId;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public int getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getPdfPath() {
        return pdfPath;
    }
}
