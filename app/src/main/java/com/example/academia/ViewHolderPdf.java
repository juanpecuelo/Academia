package com.example.academia;

import android.text.Layout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderPdf extends RecyclerView.ViewHolder {
    public TextView txtName;
    public ImageButton imageButton;
    public CardView cardView;
    public LinearLayout rowLayout;

    public ViewHolderPdf(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.pdf_textName);
        cardView = itemView.findViewById(R.id.pdf_cardView);
        imageButton = itemView.findViewById(R.id.imageButtonPreviewPdf);
        rowLayout = itemView.findViewById(R.id.pdf_layout);
    }
}
