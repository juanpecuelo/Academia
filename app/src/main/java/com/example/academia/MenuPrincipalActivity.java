package com.example.academia;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

//TODO
//  Hacer que el banner de Bienvenido cambie con frases aleatorias
// cambiar el icon de qué me pasa (se ve borroso)
// probar con distintos colores para los distintos cardviews??
// cuando se clica el boton de entendido del pdf, se puede llevar a una activity o animación que le diga que
// se ha desbloqueado un nuevo pdf o módulo o ejercicio

public class MenuPrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.cvModulos:
                intent = new Intent(MenuPrincipalActivity.this, SelectorCategoriaActivity.class);
                break;
            case R.id.cvQueMePasa:
                intent = new Intent(MenuPrincipalActivity.this, SintomasActivity.class);
                break;
            case R.id.cvWidgets:
                //intent = new Intent(MenuPrincipalActivity.this, WidgetsActivity.class);
                break;
            case R.id.cvReportarBug:
                intent = new Intent(MenuPrincipalActivity.this, ReportarBugActivity.class);
                break;
            case R.id.cvAjustes:
                intent = new Intent(MenuPrincipalActivity.this, AjustesActivity.class);
                break;
            case R.id.cvInfo:
                intent = new Intent(MenuPrincipalActivity.this, InformacionActivity.class);
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        RelativeLayout relativeLayout = findViewById(R.id.layoutMenuPrincipal);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        CardView misModulos = findViewById(R.id.cvModulos);
        CardView queMePasa = findViewById(R.id.cvQueMePasa);
        CardView widgets = findViewById(R.id.cvWidgets);
        CardView bugs = findViewById(R.id.cvReportarBug);
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
        bugs.setOnClickListener(this);
        ajustes.setOnClickListener(this);
        info.setOnClickListener(this);
    }

}
