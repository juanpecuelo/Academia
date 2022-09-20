package com.example.academia;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderCategoria extends RecyclerView.ViewHolder {
    public TextView txtName;
    public ImageButton imageButton;
    public CardView cardView;
    public LinearLayout linearLayout;
    public TextView txtDescripcion;
    public TextView txtPorcentaje;
    public ProgressBar progressBar;

    public ViewHolderCategoria(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.categoria_textName);
        cardView = itemView.findViewById(R.id.categoria_cardView);
        linearLayout = itemView.findViewById(R.id.linearLayoutCategoria);
        imageButton = itemView.findViewById(R.id.imageButtonCategoria);
        txtPorcentaje = itemView.findViewById(R.id.txtPorcentajePbCategorias);
        txtDescripcion = itemView.findViewById(R.id.categoria_textDescripcion);
        progressBar = itemView.findViewById(R.id.progressBarCategorias);
    }
}
