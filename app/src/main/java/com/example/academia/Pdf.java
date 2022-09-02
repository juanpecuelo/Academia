package com.example.academia;

public class Pdf {
    private final int id;
    private final String image;
    private final String title;
    private final String introduction;
    private final String pdfPath;

    public Pdf(int id, String image, String title, String introduction, String pdfPath) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.introduction = introduction;
        this.pdfPath = pdfPath;
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
