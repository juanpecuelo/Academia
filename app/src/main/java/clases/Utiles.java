package clases;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.juanpecuelo.academia.R;

public class Utiles {
    private final Activity activity;
    public Utiles(Activity activity){
        this.activity = activity;
    }
    public void toast(String msg) {
        LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_ok, activity.findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);
        Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
