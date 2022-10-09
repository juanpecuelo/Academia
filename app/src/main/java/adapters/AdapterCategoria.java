package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import clases.Categoria;
import com.juanpecuelo.academia.R;
import com.juanpecuelo.academia.SelectorPdfActivity;
import viewholders.ViewHolderCategoria;

import java.util.List;


public class AdapterCategoria extends RecyclerView.Adapter<ViewHolderCategoria> {
    private final Context context;
    private final List<Categoria> listaCategoria;

    @NonNull
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderCategoria(LayoutInflater.from(context).inflate(R.layout.rv_item_categoria, parent, false));
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
        // TODO poner esto es un string resource
        holder.txtPorcentaje.setText(porcentaje + " módulo" + (porcentaje != 1 ? "s" : "") + " desbloqueado" + (porcentaje != 1 ? "s" : ""));
        holder.txtName.setSelected(true);
        //holder.progressBar.setProgress(porcentaje);
        Glide.with(context).load(categoria.getImage()).into(holder.imageButton);
        //TODO al borrar la línea de abajo, se consigue que no se repita el background
        //  pero hay mucho margen
        int color = categoria.getColor();
        if (position < getItemCount()) {
            holder.imageButton.setBackgroundColor(color);
            holder.linearLayout.setBackgroundColor(color);
        }
        //holder.imageButton.setBackground(holder.imageButton.getDrawable());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectorPdfActivity.class);
                intent.putExtra("id_categoria", categoria.getId());
                intent.putExtra("nombre_categoria", categoria.getNombre());
                // por algún motivo me manda el color de la categoría anterior
                intent.putExtra("color", categoria.getColor());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }
}
