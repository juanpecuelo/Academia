package com.example.academia;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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

    protected Pdf(Parcel in) {
        id = in.readInt();
        image = in.readString();
        title = in.readString();
        introduction = in.readString();
        pdfPath = in.readString();
        categoriaId = in.readInt();
        unlocked = in.readInt();
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

    @Override
    public String toString() {
        return "-----------------" +
                "Pdf{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                ", categoriaId=" + categoriaId +
                ", unlocked=" + unlocked +
                '}';
    }


}
