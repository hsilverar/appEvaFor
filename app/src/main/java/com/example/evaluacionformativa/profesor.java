package com.example.evaluacionformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class profesor extends AppCompatActivity {
    //... atributos
    private EditText aDNI, aApellidos, aNombres, aFechaN;
    private RadioButton aMasculino, aFemenino;
    private Spinner aEcivil;
    private TextView aFechaR;
    private GridView aTabla;
    private cComun aMC;
    private SQLiteDatabase aBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        //... establecer enlace
        aDNI= findViewById(R.id.etDNIP);
        aApellidos= findViewById(R.id.etApellidosP);
        aNombres= findViewById(R.id.etNombresP);
        aMasculino= findViewById(R.id.rbMasculinoP);
        aFemenino= findViewById(R.id.rbFemeninoP);
        aEcivil= findViewById(R.id.spEcivilP);
        aFechaN= findViewById(R.id.etFechaNP);
        aFechaR= findViewById(R.id.tvFechaRP);
        aTabla= findViewById(R.id.gvTablaP);
        //... recuperar datos
        aMC= new cComun();
        aEcivil.setAdapter(aMC.eCivil(this));
        aFechaR.setText(getIntent().getStringExtra("fechaR"));
        mostrarTabla();
        //... control de tablas
        aTabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>2){
                    aDNI.setText(aTabla.getItemAtPosition((position/3)*3).toString());
                }else{
                    aDNI.setText("");
                }
            }
        });
    }
    //... botones
    public void fechaN(View view){ aMC.calendario(this, aFechaN); }
    public void anterior(View view){
        Intent sig= new Intent(this, MainActivity.class);
        startActivity(sig);
        finish();
    }
    //... base de datos
    public void registrar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... registrar datos
        int r= 0;
        if(!aMC.existeProfesor(aBD, aDNI.getText().toString())) {
            r = aMC.registrarProfesor(aBD, aDNI.getText().toString(), aApellidos.getText().toString(),
                    aNombres.getText().toString(), (aMasculino.isChecked()) ? 1 : 0, aEcivil.getSelectedItemPosition(),
                    aFechaN.getText().toString(), aFechaR.getText().toString());
        }
        else
            Toast.makeText(this, "El DNI ya se encuentra regitrado", Toast.LENGTH_LONG).show();
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
        int r= aMC.mostrarProfesor(aBD, aDNI.getText().toString(), aApellidos, aNombres, aMasculino,
                aFemenino, aEcivil, aFechaN);
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
    public void eliminar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... eliminar datos
        int r= aMC.eliminarProfesor(aBD, aDNI.getText().toString());
        //... cerrar base de datos
        aBD.close();
        //... mensaje
        if(r==1) {
            Toast.makeText(this, "Eliminación exitosa", Toast.LENGTH_LONG).show();
            mostrarTabla();
        }
        else if(r==2)
            Toast.makeText(this, "DNI incorrecto", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Ingrese el DNI", Toast.LENGTH_LONG).show();
        limpiar();
    }
    public void modificar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... modificar datos
        int r= aMC.modificarProfesor(aBD, aDNI.getText().toString(), aApellidos.getText().toString(),
                aNombres.getText().toString(), (aMasculino.isChecked())?1:0, aEcivil.getSelectedItemPosition(),
                aFechaN.getText().toString(), aFechaR.getText().toString());
        //... cerrar base de datos
        aBD.close();
        //... mensaje
        if(r==1){
            Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_LONG).show();
            limpiar();
            mostrarTabla();
        }else if(r==2)
            Toast.makeText(this, "DNI incorrecto", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Ingrese DNI y campos", Toast.LENGTH_LONG).show();
    }
    //... metodos privados
    private void mostrarTabla(){
        //... mostrar datos
        aTabla.setNumColumns(3);
        //... abrir base de datos
        aBD= aMC.abrirBaseDatos(this);
        aTabla.setAdapter(aMC.tablaProfesores(this, aBD));
        //... cerrar base de datos
        aBD.close();
    }
    private void limpiar(){
        aDNI.setText(""); aApellidos.setText(""); aNombres.setText("");
        aMasculino.setChecked(true); aEcivil.setSelection(0); aFechaN.setText("");
        aDNI.requestFocus();
    }
}