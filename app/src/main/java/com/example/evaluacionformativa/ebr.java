package com.example.evaluacionformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class ebr extends AppCompatActivity {
    //... atributos
    private TextView aDNI, aFechaR;
    private EditText aCodigo, aDenominacion;
    private GridView aTabla;
    private cComun aMC;
    private SQLiteDatabase aBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebr);
        //... establecer enlace
        aDNI= findViewById(R.id.tvDNIEBR);
        aFechaR= findViewById(R.id.tvFechaREBR);
        aCodigo= findViewById(R.id.etCodigoEBR);
        aDenominacion= findViewById(R.id.etDenominacionEBR);
        aTabla= findViewById(R.id.gvTablaEBR);
        //... recuperar datos
        aMC= new cComun();
        aDNI.setText((getIntent().getStringArrayExtra("datos")[0]));
        aFechaR.setText((getIntent().getStringArrayExtra("datos")[1]));
        mostrarTabla();
        //... control de tablas
        aTabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>1)
                    aCodigo.setText(aTabla.getItemAtPosition((position/2)*2).toString());
                else aCodigo.setText("");
            }
        });
    }
    //... metodos privados
    private void mostrarTabla(){
        //... mostrar datos
        aTabla.setNumColumns(2);
        //... abrir base de datos
        aBD= aMC.abrirBaseDatos(this);
        aTabla.setAdapter(aMC.tablaEBR(this, aBD));
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