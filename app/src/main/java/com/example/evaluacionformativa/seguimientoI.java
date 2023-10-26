package com.example.evaluacionformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class seguimientoI extends AppCompatActivity {
    //... atributos
    private TextView aDNI, aDNIE, aCodigo, aCodigoS, aEBR, aAnio, aCiclo, aAula, aFecha, aFechaR;
    private EditText aEstrategia;
    private GridView aTabla;
    private ArrayAdapter aLista;
    private cComun aMC;
    private SQLiteDatabase aBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_i);
        //... establecer enlace
        aDNI= findViewById(R.id.tvDNISeI);
        aDNIE= findViewById(R.id.tvDNIESeI);
        aCodigo= findViewById(R.id.tvCodigoSeI);
        aCodigoS= findViewById(R.id.tvCodigoSSeI);
        aEBR= findViewById(R.id.tvEBRSeI);
        aAnio= findViewById(R.id.tvAnioSeI);
        aCiclo= findViewById(R.id.tvCicloSeI);
        aAula= findViewById(R.id.tvAulaSeI);
        aFecha= findViewById(R.id.tvFechaSeI);
        aEstrategia= findViewById(R.id.etEstrategiaSeI);
        aFechaR= findViewById(R.id.tvFechaRSeI);
        aTabla=findViewById(R.id.gvTablaSeI);
        //... recuperar datos
        aDNI.setText((getIntent().getStringArrayExtra("datos")[0]));
        aCodigo.setText((getIntent().getStringArrayExtra("datos")[1]));
        aFechaR.setText((getIntent().getStringArrayExtra("datos")[2]));
        aMC= new cComun();
        mostrarTitulo();
        mostrarTabla();
        aTabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>5) {
                    aDNIE.setText(aTabla.getItemAtPosition(4+(position/6)*6).toString());
                    aCodigoS.setText(aTabla.getItemAtPosition(5+(position/6) * 6).toString());
                    aFecha.setText(aTabla.getItemAtPosition(3+(position/6) * 6).toString());
                }else{
                    aDNIE.setText("---"); aCodigoS.setText("---"); aFecha.setText("---");
                }
            }
        });

    }
    //... metodos privados
    private void mostrarTitulo(){
        aBD= aMC.abrirBaseDatos(this);
        //... recuperar datos
        aMC.mostrarTitulo(aBD, aDNI.getText().toString(), aCodigo.getText().toString(), aEBR, aCiclo, aAula, aAnio);
        //... cerrar base de datos
        aBD.close();
    }
    private void limpiar(){
        aEstrategia.setText("");
        aDNIE.setText("---"); aCodigoS.setText("---"); aFecha.setText("---");
        aEstrategia.requestFocus();
    }
    //... metodos
    private void mostrarTabla(){
        //... mostrar tabla final
        aTabla.setNumColumns(6);
        //... abrir base de datos
        aBD= aMC.abrirBaseDatos(this);
        aLista= aMC.tablaSeguimiento(this, aBD, aDNI.getText().toString(), aCodigo.getText().toString());
        aTabla.setAdapter(aLista);
        //... cerrar base de datos
        aBD.close();
    }
    //... control de botones
    public void anterior(View view){
        Intent sig= new Intent(this, MainActivity.class);
        startActivity(sig);
        finish();
    }
    public void estrategia(View view){
        int r= 0;
        if(aDNIE.getText().toString().equals("---") || aCodigoS.getText().toString().equals("---") ||
                aFecha.getText().toString().equals("---") || aEstrategia.getText().toString().equals(""))
            Toast.makeText(this, "Seleccione un estudiante o planifique una estrategia", Toast.LENGTH_SHORT).show();
        else{
            aBD= aMC.abrirBaseDatos(this);
            //... modificar datos
            r= aMC.modificarEstrategia(aBD, aCodigo.getText().toString(), aDNIE.getText().toString(),
                    aCodigoS.getText().toString(), aFecha.getText().toString(), "Si", aEstrategia.getText().toString(),
                    aDNI.getText().toString(), aFechaR.getText().toString());
            //... cerrar base de datos
            aBD.close();
        }
        //... mensaje
        if(r==1){
            Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_LONG).show();
            mostrarTabla();
            limpiar();
        }else if(r==2)
            Toast.makeText(this, "Campos incorrectos", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show();
    }
    public void buscar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... recuperar datos
        int r= aMC.mostrarEstrategia(aBD, aCodigo.getText().toString(), aDNIE.getText().toString(),
                aCodigoS.getText().toString(), aFecha.getText().toString(), aDNI.getText().toString(), aEstrategia);
        //... cerrar base de datos
        aBD.close();
        //... mensaje
        if(r==1)
            Toast.makeText(this, "Busqueda exitosa", Toast.LENGTH_LONG).show();
        else{
            if(r==0)
                Toast.makeText(this, "Ingrese DNI", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "DNI incorrecto", Toast.LENGTH_LONG).show();
            limpiar();
        }
    }
}