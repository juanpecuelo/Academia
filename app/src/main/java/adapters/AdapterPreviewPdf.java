package adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import clases.Constantes;
import com.juanpecuelo.academia.LoginActivity;
import com.juanpecuelo.academia.MainActivity;
import clases.Pdf;
import com.juanpecuelo.academia.PdfViewActivity;
import com.juanpecuelo.academia.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO
// como arriba sobra mucho espacio, se puede poner un cambiador de categorias con flechas hacia los lados para cambiar rápidamente de categoría

public class AdapterPreviewPdf extends PagerAdapter {
    private final Context context;
    private final List<Pdf> pdfFiles;

    private static final String URL = Constantes.IP+"/login/update.php";

    public AdapterPreviewPdf(Context context, List<Pdf> pdfFiles) {
        this.context = context;
        this.pdfFiles = pdfFiles;
    }


    @Override
    public int getCount() {
        return pdfFiles.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.preview_pdf_adapter,container, false);
        TextView txtName = view.findViewById(R.id.titlePrueba);
        ImageButton imageButton = view.findViewById(R.id.imagePrueba);
        TextView descripcion = view.findViewById(R.id.introductionPrueba);
        Button button = view.findViewById(R.id.btnEntendidoPrueba);
        LinearLayout layout = view.findViewById(R.id.linearLayoutPrueba);



        if(position == getCount()-1){
            layout.setBackgroundColor(ContextCompat.getColor(context, R.color.last_pdf_color));
            button.setVisibility(View.VISIBLE);
        }
        final Pdf pdf = pdfFiles.get(position);

        txtName.setText(pdf.getTitle());
        txtName.setSelected(true);
        descripcion.setText(pdf.getIntroduction());
        Glide.with(context).load(pdf.getImage()).into(imageButton);
        //de esta forma el fondo de la imagen, que no escala con esta, se ve como el layout
        imageButton.setBackgroundColor(layout.getSolidColor());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PdfViewActivity.class);
                intent.putExtra("path", pdf.getPdfPath());
                context.startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unlockNextPdf(pdf.getCategoriaId(), pdf.getId());
                //TODO se presiona el botón, se envía un boolean extra al activity de SelectorPdfActivity,
                // en el onRestart, si el extra es positivo, se cierra esa activity. Mientras tanto, al
                // haber presionado el botón, se envía a SelectorPdfActivity donde se enseña el nuevo pdf.
                Toast.makeText(context, context.getResources().getString(R.string.nuevo_módulo), Toast.LENGTH_SHORT).show();
                SharedPreferences sp = context.getSharedPreferences(LoginActivity.PREFS_USER, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(MainActivity.PREFS_ULTIMA_CATEGORIA, pdf.getCategoriaId());
                editor.commit();
                //Intent intent = new Intent(context, SelectorPdfActivity.class);
                //context.startActivity(intent);

                button.setClickable(false);
            }
        });


        container.addView(view);
        return view;

    }
    public void unlockNextPdf(int id_categoria, int id_pdf){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(PreviewPdfActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //toastError(context.getResources().getString(R.string.error));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = context.getSharedPreferences(LoginActivity.PREFS_USER, 0);
                params.put("id_categoria", (id_categoria)+"");
                params.put("id_pdf", id_pdf+"" +
                        "");
                params.put("id_usuario",sp.getInt("user_id",-1)+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //TOAST
/*
    private void toastCorrecto(String msg){

        View view = LayoutInflater.from(context).inflate(R.layout.toast_ok,container, false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.toast_ok, findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
    private void toastError(String msg){
        LayoutInflater inflater = container.getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error, findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastError);
        txtMensaje.setText(msg);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
*/


}

    /*@Override
    public void onBindViewHolder(@NonNull ViewHolderPdf holder, int position) {
        final Pdf pdf = pdfFiles.get(holder.getAdapterPosition());
        holder.txtName.setText(pdfFiles.get(holder.getAdapterPosition()).getTitle());
        holder.txtName.setSelected(true);
        Glide.with(context).load(pdf.getImage()).into(holder.imageButton);
        if(holder.getAdapterPosition() == getItemCount()-1){
            holder.rowLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow_dark));
        }
        holder.imageButton.setBackground(holder.imageButton.getDrawable());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectorPdfActivity.class);
                intent.putExtra("id",pdf.getId());
                intent.putExtra("image", pdf.getImage());
                intent.putExtra("title", pdf.getTitle());
                intent.putExtra("introduction", pdf.getIntroduction());
                intent.putExtra("pdf", pdf.getPdfPath());
                intent.putExtra("id_categoria", pdf.getCategoriaId());
                if(holder.getAdapterPosition() == getCount()-1){
                    intent.putExtra("boton_activado", true);
                }
                context.startActivity(intent);
            }
        });
    }

     */
