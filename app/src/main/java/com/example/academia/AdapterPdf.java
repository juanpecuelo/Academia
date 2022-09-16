package com.example.academia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class AdapterPdf extends RecyclerView.Adapter<ViewHolderPdf> {
    private final Context context;
    private List<Pdf> pdfFiles;

    @NonNull
    public ViewHolderPdf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderPdf(LayoutInflater.from(context).inflate(R.layout.rv_item_pdfs,parent,false));
    }

    public AdapterPdf(Context context, List<Pdf> pdfFiles) {
        this.context = context;
        this.pdfFiles = pdfFiles;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPdf holder, int position) {
        final Pdf pdf = pdfFiles.get(position);
        holder.txtName.setText(pdfFiles.get(position).getTitle());
        holder.txtName.setSelected(true);
        Glide.with(context).load(pdf.getImage()).into(holder.imageButton);
        holder.imageButton.setBackground(holder.imageButton.getDrawable());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PreviewPdfActivity.class);
                intent.putExtra("id",pdf.getId());
                intent.putExtra("image", pdf.getImage());
                intent.putExtra("title", pdf.getTitle());
                intent.putExtra("introduction", pdf.getIntroduction());
                intent.putExtra("pdf", pdf.getPdfPath());
                intent.putExtra("id_categoria", pdf.getCategoriaId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }
}