package adapters;

import android.content.Context;
import android.content.Intent;
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
import clases.Pdf;
import clases.SessionManager;

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
    private SessionManager sm;
    private static final String URL = Constantes.IP + "/login/update.php";

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
        sm = new SessionManager(container.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.preview_pdf_adapter, container, false);
        TextView txtName = view.findViewById(R.id.titlePdfAdapter);
        ImageButton imageButton = view.findViewById(R.id.imagePdfAdapter);
        TextView descripcion = view.findViewById(R.id.introductionPdfAdapter);
        Button button = view.findViewById(R.id.btnEntendidoPdfAdapter);
        LinearLayout layout = view.findViewById(R.id.linearLayoutPdfAdapter);


        if (position == getCount() - 1) {
            layout.setBackground(ContextCompat.getDrawable(context,R.drawable.background_last_pdf));
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
                Toast.makeText(context, context.getResources().getString(R.string.nuevo_módulo), Toast.LENGTH_SHORT).show();
                sm.setUltimaCategoria(pdf.getCategoriaId());
                //Intent intent = new Intent(context, SelectorPdfActivity.class);
                //context.startActivity(intent);
                button.setClickable(false);
            }
        });


        container.addView(view);
        return view;

    }

    public void unlockNextPdf(int id_categoria, int id_pdf) {
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_categoria", (id_categoria) + "");
                params.put("id_pdf", id_pdf + "");
                params.put("id_usuario", sm.getId() + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}

