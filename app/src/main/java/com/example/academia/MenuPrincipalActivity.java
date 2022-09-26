package com.example.academia;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;
import java.util.Random;

//TODO
//  cambiar el tamaño de las frases si son muy grandes
// cuando se clica el boton de entendido del pdf, se puede llevar a una activity o animación que le diga que
// se ha desbloqueado un nuevo pdf o módulo o ejercicio


public class MenuPrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] FRASES = {"Bienvenido", "¿Qué tal?"};

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.cvModulos:
                intent = new Intent(MenuPrincipalActivity.this, SelectorCategoriaActivity.class);
                break;
            case R.id.cvQueMePasa:
                intent = new Intent(MenuPrincipalActivity.this, SintomasActivity.class);
                break;
            case R.id.cvWidgets:
                //intent = new Intent(MenuPrincipalActivity.this, WidgetsActivity.class);
                toastCorrecto("¡Próximamente!");
                break;
            case R.id.cvMyStoic:
                //intent = new Intent(MenuPrincipalActivity.this, MyStoicActivity.class);
                toastCorrecto("¡Próximamente!");
                break;
            case R.id.cvAjustes:
                intent = new Intent(MenuPrincipalActivity.this, AjustesActivity.class);
                break;
            case R.id.cvInfo:
                intent = new Intent(MenuPrincipalActivity.this, InformacionActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
    private void toastCorrecto(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_ok, findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        RelativeLayout relativeLayout = findViewById(R.id.layoutMenuPrincipal);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay>=5 && timeOfDay<12){
        relativeLayout.setBackground(getDrawable(R.drawable.morning_gradient_list));
        }else if(timeOfDay>=12 && timeOfDay<18){
            relativeLayout.setBackground(getDrawable(R.drawable.blue_gradient_list));
        }else if(timeOfDay>=18 && timeOfDay<22){
            relativeLayout.setBackground(getDrawable(R.drawable.evening_gradient_list));
        }else{
            relativeLayout.setBackground(getDrawable(R.drawable.night_gradient_list));
        }
        //TODO si se pone la animacion en un thread aparte, no se reseteará al cambiar de activity?

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        TextView frase = findViewById(R.id.txtBienvenido);
        frase.setText(FRASES[new Random().nextInt(FRASES.length)]);

        CardView misModulos = findViewById(R.id.cvModulos);
        CardView queMePasa = findViewById(R.id.cvQueMePasa);
        CardView widgets = findViewById(R.id.cvWidgets);
        CardView myStoic = findViewById(R.id.cvMyStoic);
        CardView ajustes = findViewById(R.id.cvAjustes);
        CardView info = findViewById(R.id.cvInfo);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bmnMenuPrincipal);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bmnSeguirLeyendo) {
                    Intent intent = new Intent(getApplicationContext(), SeguirLeyendoActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
                return false;
            }
        });


        misModulos.setOnClickListener(this);
        queMePasa.setOnClickListener(this);
        widgets.setOnClickListener(this);
        myStoic.setOnClickListener(this);
        ajustes.setOnClickListener(this);
        info.setOnClickListener(this);
    }

}
