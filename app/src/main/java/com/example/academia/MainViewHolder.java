package com.example.academia;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName;
    public ImageButton imageButton;
    public CardView cardView;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.pdf_textName);
        cardView = itemView.findViewById(R.id.pdf_cardView);
        imageButton = itemView.findViewById(R.id.imageButtonPreviewPdf);
    }
}
