package com.example.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

//TODO
//  Hacer que el banner de Bienvenido cambie con frases aleatorias
// cambiar el icon de qué me pasa (se ve borroso)
// quitar o modificar el UI para que se vea el logo de la app (o eliminarlo completamente)
// probar con distintos colores para los distintos cardviews??

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.cvModulos:
                intent = new Intent(PrincipalActivity.this, SelectorPdfActivity.class);
                break;
            case R.id.cvQueMePasa:
                intent = new Intent(PrincipalActivity.this, SintomasActivity.class);
                break;
            case R.id.cvWidgets:
                //intent = new Intent(PrincipalActivity.this, WidgetsActivity.class);
                break;
            case R.id.cvReportarBug:
                //intent = new Intent(PrincipalActivity.this, ReportarBugActivity.class);
                break;
            case R.id.cvAjustes:
                //intent = new Intent(PrincipalActivity.this, AjustesActivity.class);
                break;
            case R.id.cvInfo:
                //intent = new Intent(PrincipalActivity.this, InfoActivity.class);
                break;
            default:
                intent = new Intent(PrincipalActivity.this, PrincipalActivity.class);
                finish();
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        CardView misModulos = findViewById(R.id.cvModulos);
        CardView queMePasa = findViewById(R.id.cvQueMePasa);
        CardView widgets = findViewById(R.id.cvWidgets);
        CardView bugs = findViewById(R.id.cvReportarBug);
        CardView ajustes = findViewById(R.id.cvAjustes);
        CardView info = findViewById(R.id.cvInfo);


        misModulos.setOnClickListener(this);
        queMePasa.setOnClickListener(this);
        widgets.setOnClickListener(this);
        bugs.setOnClickListener(this);
        ajustes.setOnClickListener(this);
        info.setOnClickListener(this);
    }

}
