package com.example.evaluacionformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class proyectoI extends AppCompatActivity {
    //... atributos
    private TextView aDNI, aDNIE, aCodigo, aCodigoS, aDenominacion, aEBR, aAnio, aCiclo, aAula, aFecha, aFechaR;
    private GridView aTabla;
    private ArrayAdapter aLista;
    private cComun aMC;
    private SQLiteDatabase aBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto_i);
        //... establecer enlace
        aDNI= findViewById(R.id.tvDNIAc);
        aDNIE= findViewById(R.id.tvDNIEAc);
        aCodigo= findViewById(R.id.tvCodigoAc);
        aCodigoS= findViewById(R.id.tvCodigoSAc);
        aDenominacion= findViewById(R.id.tvTituloSAc);
        aEBR= findViewById(R.id.tvEBRAc);
        aAnio= findViewById(R.id.tvAnioAc);
        aCiclo= findViewById(R.id.tvCicloAc);
        aAula= findViewById(R.id.tvAulaAc);
        aFecha= findViewById(R.id.tvFechaAc);
        aFechaR= findViewById(R.id.tvFechaRAc);
        aTabla=findViewById(R.id.gvTablaAc);
        //... recuperar datos
        aDNI.setText((getIntent().getStringArrayExtra("datos")[0]));
        aCodigo.setText((getIntent().getStringArrayExtra("datos")[1]));
        aDenominacion.setText(getIntent().getStringArrayExtra("datos")[2]);
        aFecha.setText((getIntent().getStringArrayExtra("datos")[3]));
        aFechaR.setText((getIntent().getStringArrayExtra("datos")[4]));
        aMC= new cComun();
        mostrarTitulo();
        mostrarTabla();
        //... control tabla
        aTabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>4) {
                    aDNIE.setText(aTabla.getItemAtPosition(3+(position/5)*5).toString());
                    aCodigoS.setText(aTabla.getItemAtPosition(4+(position/5)*5).toString());
                }else{
                    aDNIE.setText("---"); aCodigoS.setText("---");
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
    private void mostrarTabla(){
        //... mostrar tabla final
        aTabla.setNumColumns(5);
        //... abrir base de datos
        aBD= aMC.abrirBaseDatos(this);
        aLista= aMC.tablaAreaCEstudiante(this, aBD, aDNI.getText().toString(), aCodigo.getText().toString(), aFecha.getText().toString());
        aTabla.setAdapter(aLista);
        //... cerrar base de datos
        aBD.close();
    }
    public void anterior(View view){
        Intent sig= new Intent(this, MainActivity.class);
        startActivity(sig);
        finish();
    }
    //... procesos
    public void inicio(View view){
        int r= 0;
        if(aDNIE.getText().toString().equals("---") || aCodigoS.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione un estudiante", Toast.LENGTH_SHORT).show();
        else{
            aBD= aMC.abrirBaseDatos(this);
            //... modificar datos
            r= aMC.modificarSesion(aBD, aCodigo.getText().toString(), aDNIE.getText().toString(), aCodigoS.getText().toString(),
                    aFecha.getText().toString(), "C", aDNI.getText().toString(), aFechaR.getText().toString());
            //... cerrar base de datos
            aBD.close();
        }
        //... mensaje
        if(r==1){
            Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_LONG).show();
            mostrarTabla();
        }else if(r==2)
            Toast.makeText(this, "Campos incorrectos", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show();
    }
    public void proceso(View view){
        int r= 0;
        if(aDNIE.getText().toString().equals("---") || aCodigoS.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione un estudiante", Toast.LENGTH_SHORT).show();
        else{
            aBD= aMC.abrirBaseDatos(this);
            //... modificar datos
            r= aMC.modificarSesion(aBD, aCodigo.getText().toString(), aDNIE.getText().toString(), aCodigoS.getText().toString(),
                    aFecha.getText().toString(), "B", aDNI.getText().toString(), aFechaR.getText().toString());
            //... cerrar base de datos
            aBD.close();
        }
        //... mensaje
        if(r==1){
            Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_LONG).show();
            mostrarTabla();
        }else if(r==2)
            Toast.makeText(this, "Campos incorrectos", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show();
    }
    public void esperado(View view){
        int r= 0;
        if(aDNIE.getText().toString().equals("---") || aCodigoS.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione un estudiante", Toast.LENGTH_SHORT).show();
        else{
            aBD= aMC.abrirBaseDatos(this);
            //... modificar datos
            r= aMC.modificarSesion(aBD, aCodigo.getText().toString(), aDNIE.getText().toString(), aCodigoS.getText().toString(),
                    aFecha.getText().toString(), "A", aDNI.getText().toString(), aFechaR.getText().toString());
            //... cerrar base de datos
            aBD.close();
        }
        //... mensaje
        if(r==1){
            Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_LONG).show();
            mostrarTabla();
        }else if(r==2)
            Toast.makeText(this, "Campos incorrectos", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show();
    }
    public void destacado(View view){
        int r= 0;
        if(aDNIE.getText().toString().equals("---") || aCodigoS.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione un estudiante", Toast.LENGTH_SHORT).show();
        else{
            aBD= aMC.abrirBaseDatos(this);
            //... modificar datos
            r= aMC.modificarSesion(aBD, aCodigo.getText().toString(), aDNIE.getText().toString(), aCodigoS.getText().toString(),
                    aFecha.getText().toString(), "AD", aDNI.getText().toString(), aFechaR.getText().toString());
            //... cerrar base de datos
            aBD.close();
        }
        //... mensaje
        if(r==1){
            Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_LONG).show();
            mostrarTabla();
        }else if(r==2)
            Toast.makeText(this, "Campos incorrectos", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show();
    }
}