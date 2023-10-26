package com.example.evaluacionformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

public class ciclo extends AppCompatActivity {
    //... atributos
    private TextView aDNI, aFechaR;
    private Spinner aEBR;
    private EditText aCodigo, aDenominacion;
    private GridView aTabla;
    private cComun aMC;
    private SQLiteDatabase aBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo);
        //... establecer enlace
        aEBR= findViewById(R.id.spEBRCi);
        aDNI= findViewById(R.id.tvDNICi);
        aFechaR= findViewById(R.id.tvFechaRCi);
        aCodigo= findViewById(R.id.etCodigoCi);
        aDenominacion= findViewById(R.id.etDenominacionCi);
        aTabla= findViewById(R.id.gvTablaCi);
        //... recuperar datos
        aDNI.setText((getIntent().getStringArrayExtra("datos")[0]));
        aFechaR.setText((getIntent().getStringArrayExtra("datos")[1]));
        //... abrir base de datos
        aMC= new cComun();
        aBD= aMC.abrirBaseDatos(this);
        aEBR.setAdapter(aMC.listaEBR(this, aBD));
        //... cerrar base de datos
        aBD.close();
        mostrarTabla();
        //... control de tablas
        aTabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>2)
                    aCodigo.setText(aTabla.getItemAtPosition((position/3)*3).toString());
                else aCodigo.setText("");
            }
        });
    }
    //... metodos privados
    private void mostrarTabla(){
        //... mostrar datos
        aTabla.setNumColumns(3);
        //... abrir base de datos
        aBD= aMC.abrirBaseDatos(this);
        aTabla.setAdapter(aMC.tablaCiclo(this, aBD));
        //... cerrar base de datos
        aBD.close();
    }
    //... botones
    public void anterior(View view){
        Intent sig= new Intent(this, MainActivity.class);
        startActivity(sig);
        finish();
    }
}