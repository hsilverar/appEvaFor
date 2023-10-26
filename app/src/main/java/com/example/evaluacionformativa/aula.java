package com.example.evaluacionformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class aula extends AppCompatActivity {
    //... atributos
    private TextView aDNI, aCodigoEBR, aFechaR;
    private ListView aEBR;
    private Spinner aCiclo;
    private EditText aCodigo, aDenominacion, aAnio;
    private GridView aTabla;
    private cComun aMC;
    private SQLiteDatabase aBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);
        //... establecer enlace
        aDNI= findViewById(R.id.tvDNIC);
        aCodigoEBR= findViewById(R.id.tvCodigoEBRC);
        aFechaR= findViewById(R.id.tvFechaRC);
        aEBR= findViewById(R.id.lvEBRC);
        aCiclo= findViewById(R.id.spCicloC);
        aCodigo= findViewById(R.id.etCodigoC);
        aDenominacion= findViewById(R.id.etDenominacionC);
        aAnio= findViewById(R.id.etAnioC);
        aTabla= findViewById(R.id.gvTablaC);
        //... recuperar datos
        aDNI.setText(getIntent().getStringArrayExtra("datos")[0]);
        aFechaR.setText(getIntent().getStringArrayExtra("datos")[1]);
        //... abrir base de datos
        aMC= new cComun();
        aBD= aMC.abrirBaseDatos(this);
        aEBR.setAdapter(aMC.listaEBR(this, aBD));
        //... cerrar base de datos
        aBD.close();
        Context contexto= this;
        aEBR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aCodigoEBR.setText(aEBR.getItemAtPosition(position).toString().substring(0, 2));
                listarCiclo();
            }
        });
        mostrarTabla();
        //... control de tablas
        aTabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>3)
                    aCodigo.setText(aTabla.getItemAtPosition((position/4)*4).toString());
                else
                    aCodigo.setText("");
            }
        });
    }
    //... metodos privados
    private void listarCiclo(){
        if(aCodigoEBR.getText().equals("---"))
            Toast.makeText(this, "Seleciones un EBR", Toast.LENGTH_LONG).show();
        else{
            aBD= aMC.abrirBaseDatos(this);
            aCodigo.setText(aCodigoEBR.getText().toString());
            aCiclo.setAdapter(aMC.listaCiclo(this, aBD, aCodigoEBR.getText().toString()));
            //... cerrar base de datos
            aBD.close();
        }
    }
    private void mostrarTabla(){
        //... mostrar datos
        aTabla.setNumColumns(4);
        //... abrir base de datos
        aBD= aMC.abrirBaseDatos(this);
        aTabla.setAdapter(aMC.tablaCarga(this, aBD, aDNI.getText().toString()));
        //... cerrar base de datos
        aBD.close();
    }
    private void limpiar(){
        aCodigo.setText(""); aDenominacion.setText(""); aAnio.setText("");
        aCodigo.requestFocus();
    }
    //... botones
    public void anterior(View view){
        Intent sig= new Intent(this, MainActivity.class);
        startActivity(sig);
        finish();
    }
    //... base de datos
    public void registrar(View view){
        //... registrar datos
        aBD= aMC.abrirBaseDatos(this);
        int r= 0;
        if(!aCodigoEBR.getText().equals("---"))
            r= aMC.registrarCarga(aBD, aDenominacion.getText().toString(), aAnio.getText().toString(),
                    aDNI.getText().toString(), aCodigoEBR.getText().toString(), aCiclo.getSelectedItem().toString().substring(0, 2),
                    aFechaR.getText().toString());
        //... cerrar base de datos
        aBD.close();
        //... mensaje
        if (r == 0)
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
            limpiar();
            mostrarTabla();
        }
    }
    public void buscar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... recuperar datos
        int r= aMC.mostrarCarga(aBD, aDNI.getText().toString(), aCodigo.getText().toString(), aDenominacion, aAnio);
        //... cerrar base de datos
        aBD.close();
        //... mensaje
        if(r==1)
            Toast.makeText(this, "Busqueda exitosa", Toast.LENGTH_LONG).show();
        else{
            if(r==0)
                Toast.makeText(this, "Complete los campo", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Código incorrecto", Toast.LENGTH_LONG).show();
            limpiar();
        }
    }
    public void modificar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... modificar datos
        int r= aMC.modificarCarga(aBD, aDNI.getText().toString(), aCodigo.getText().toString(), aDenominacion.getText().toString(), aAnio.getText().toString(), aFechaR.getText().toString());
        //... cerrar base de datos
        aBD.close();
        //... mensaje
        if(r==1){
            Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_LONG).show();
            limpiar();
            mostrarTabla();
        }else if(r==2)
            Toast.makeText(this, "Código incorrecto", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Ingrese código y campos", Toast.LENGTH_LONG).show();
    }

    public void eliminar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... eliminar datos
        int r= aMC.eliminarCarga(aBD, aDNI.getText().toString(), aCodigo.getText().toString());
        //... cerrar base de datos
        aBD.close();
        //... mensaje
        if(r==1) {
            Toast.makeText(this, "Eliminación exitosa", Toast.LENGTH_LONG).show();
            mostrarTabla();
        }
        else if(r==2)
            Toast.makeText(this, "Código incorrecto", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_LONG).show();
        limpiar();
    }
}