package com.example.academia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class AdapterCategoria extends RecyclerView.Adapter<ViewHolderCategoria> {
    private final Context context;
    private List<Categoria> listaCategoria;

    @NonNull
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderCategoria(LayoutInflater.from(context).inflate(R.layout.rv_item_categoria,parent,false));
    }


    public AdapterCategoria(Context context, List<Categoria> listaCategoria) {
        this.context = context;
        this.listaCategoria = listaCategoria;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategoria holder, int position) {
        final Categoria categoria = listaCategoria.get(position);
        int porcentaje = listaCategoria.get(position).getProgressBarPorcentaje();
        holder.txtName.setText(listaCategoria.get(position).getNombre());
        holder.txtDescripcion.setText(listaCategoria.get(position).getDescripcion());
        holder.txtPorcentaje.setText(porcentaje+" módulo"+(porcentaje!=1?"s":"")+" desbloqueado"+(porcentaje!=1?"s":""));
        holder.txtName.setSelected(true);
        //holder.progressBar.setProgress(porcentaje);
        Glide.with(context).load(categoria.getImage()).into(holder.imageButton);
        //TODO al borrar la línea de abajo, se consigue que no se repita el background
        //  pero hay mucho margen

        //holder.imageButton.setBackground(holder.imageButton.getDrawable());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectorPdfActivity.class);
                intent.putExtra("id_categoria",categoria.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }
}
