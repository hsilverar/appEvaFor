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

public class estudiante extends AppCompatActivity {
    //... atributos
    private EditText aDNIE, aApellidos, aNombres, aFechaN, aCelular;
    private RadioButton aMasculino, aFemenino;
    private Spinner aEcivil;
    private TextView aDNIP, aFechaR;
    private GridView aTabla;
    private cComun aMC;
    private SQLiteDatabase aBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        //... estableer enlace
        aDNIE= findViewById(R.id.etDNIE);
        aDNIP= findViewById(R.id.tvDNIPE);
        aApellidos= findViewById(R.id.etApellidosE);
        aNombres= findViewById(R.id.etNombresE);
        aFechaN= findViewById(R.id.etFechaNE);
        aCelular= findViewById(R.id.etCelularE);
        aMasculino= findViewById(R.id.rbMasculinoE);
        aFemenino= findViewById(R.id.rbFemeninoE);
        aEcivil= findViewById(R.id.spEcivilE);
        aFechaR= findViewById(R.id.tvFechaRE);
        aTabla= findViewById(R.id.gvTablaE);
        //... recuperar datos
        aMC= new cComun();
        aEcivil.setAdapter(aMC.eCivil(this));
        aDNIP.setText((getIntent().getStringArrayExtra("datos")[0]));
        aFechaR.setText((getIntent().getStringArrayExtra("datos")[1]));
        mostrarTabla();
        //... control de tablas
        aTabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>3)
                    aDNIE.setText(aTabla.getItemAtPosition((position/4)*4).toString());
                else aDNIE.setText("");
            }
        });
    }
    //... metodos privados
    private void mostrarTabla(){
        //... mostrar datos
        aTabla.setNumColumns(4);
        //... abrir base de datos
        aBD= aMC.abrirBaseDatos(this);
        aTabla.setAdapter(aMC.tablaEstudiantes(this, aBD));
        //... cerrar base de datos
        aBD.close();
    }
    private void limpiar(){
        aDNIE.setText(""); aApellidos.setText(""); aNombres.setText("");
        aMasculino.setChecked(true); aEcivil.setSelection(0); aFechaN.setText("");
        aCelular.setText("");
        aDNIE.requestFocus();
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
        if(!aMC.existeEstudiante(aBD, aDNIE.getText().toString())) {
            r = aMC.registrarEstudiante(aBD, aDNIE.getText().toString(), aApellidos.getText().toString(),
                    aNombres.getText().toString(), (aMasculino.isChecked()) ? 1 : 0, aEcivil.getSelectedItemPosition(),
                    aFechaN.getText().toString(), aCelular.getText().toString(), aDNIP.getText().toString(), aFechaR.getText().toString());
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
        int r= aMC.mostrarEstudiante(aBD, aDNIE.getText().toString(), aApellidos, aNombres, aMasculino,
                aFemenino, aEcivil, aFechaN, aCelular);
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
    public void modificar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... modificar datos
        int r= aMC.modificarEstudiante(aBD, aDNIE.getText().toString(), aApellidos.getText().toString(),
                aNombres.getText().toString(), (aMasculino.isChecked())?1:0, aEcivil.getSelectedItemPosition(),
                aFechaN.getText().toString(), aCelular.getText().toString(), aDNIP.getText().toString(), aFechaR.getText().toString());
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
    public void eliminar(View view){
        aBD= aMC.abrirBaseDatos(this);
        //... eliminar datos
        int r= aMC.eliminarEstudiante(aBD, aDNIE.getText().toString());
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
}