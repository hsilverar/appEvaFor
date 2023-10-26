package com.example.evaluacionformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class sesionI extends AppCompatActivity {
    //... atributos
    private TextView aDNI, aCodigo, aFechaR, aFecha, aEBR, aCiclo, aAula, aAnio, aSelector, aEstudiante;
    private EditText aDenominacion;
    private GridView aTablaI, aTablaF;
    private ArrayAdapter aListaI, aListaF;
    private RadioButton aIdentidad, aConvive, aDios, aMotricidad, aComunica, aLee, aEscribe, aCrea, aCastellano, aCantidad, aEspacio, aExplora;
    private cComun aMC;
    private SQLiteDatabase aBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_i);
        //... establecer enlace
        aDenominacion= findViewById(R.id.etDenominacionSi);
        aDNI= findViewById(R.id.tvDNISi);
        aCodigo= findViewById(R.id.tvCodigoSi);
        aFechaR= findViewById(R.id.tvFechaRSi);
        aFecha= findViewById(R.id.tvFechaSSi);
        aEBR= findViewById(R.id.tvEBRSi);
        aCiclo= findViewById(R.id.tvCicloSi);
        aAula= findViewById(R.id.tvAulaSi);
        aAnio= findViewById(R.id.tvAnioSi);
        aTablaI= findViewById(R.id.gvTablaInSi);
        aTablaF= findViewById(R.id.gvTablaFiSi);
        aSelector= findViewById(R.id.tvSelectorSi);
        aEstudiante= findViewById(R.id.tvEstudianteSi);
        aIdentidad= findViewById(R.id.rbIdentidadSi); aConvive= findViewById(R.id.rbConviveSi);
        aDios= findViewById(R.id.rbDiosSi); aMotricidad= findViewById(R.id.rbMotricidadSi);
        aComunica= findViewById(R.id.rbComunicaSi); aLee= findViewById(R.id.rbLeeSi);
        aEscribe= findViewById(R.id.rbEscribeSi); aCrea= findViewById(R.id.rbCreaSi);
        aCastellano= findViewById(R.id.rbCastellanoSi); aCantidad= findViewById(R.id.rbCantidadSi);
        aEspacio= findViewById(R.id.rbEspacioSi); aExplora= findViewById(R.id.rbExploraSi);
        //... recuperar datos
        aDNI.setText((getIntent().getStringArrayExtra("datos")[0]));
        aCodigo.setText((getIntent().getStringArrayExtra("datos")[1]));
        aFechaR.setText((getIntent().getStringArrayExtra("datos")[2]));
        aMC= new cComun();
        aFecha.setText(aMC.fechaActual());
        mostrarTitulo();
        mostrarTablaE();
        mostrarTablaS();
        aTablaI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>2) {
                    aSelector.setText(aTablaI.getItemAtPosition((position/3)*3).toString());
                    aEstudiante.setText(aTablaI.getItemAtPosition(2+(position/3)*3).toString()+" "+aTablaI.getItemAtPosition(1+(position/3)*3).toString());
                }else {aSelector.setText("---"); aEstudiante.setText("");}
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
    private void mostrarTablaE() {
        //... mostrar tabla inicial
        aTablaI.setNumColumns(3);
        //... abrir base de datos
        aBD = aMC.abrirBaseDatos(this);
        aListaI = aMC.tablaEstudiante(this, aBD);
        aTablaI.setAdapter(aListaI);
        //... cerrar base de datos
        aBD.close();
    }
    private void mostrarTablaS(){
        //... mostrar tabla final
        aTablaF.setNumColumns(3);
        //... abrir base de datos
        aBD= aMC.abrirBaseDatos(this);
        aListaF= aMC.tablaSesionEstudiante(this, aBD, aDNI.getText().toString(), aCodigo.getText().toString(), aFecha.getText().toString());
        aTablaF.setAdapter(aListaF);
        //... cerrar base de datos
        aBD.close();
    }
    //... botones
    public void fecha(View view){
        if(aDenominacion.getText().toString().isEmpty())
            Toast.makeText(this, "Registre el tema de la sesion", Toast.LENGTH_SHORT).show();
        else {
            aMC.calendario(this, aFecha);
            aDenominacion.setEnabled(false);
        }
    }
    public void anterior(View view){
        Intent sig= new Intent(this, MainActivity.class);
        startActivity(sig);
        finish();
    }
    //... control de botones
    public void registrarSesion(View view){
        if(aSelector.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione a un estudiante", Toast.LENGTH_LONG).show();
        else{
            aBD= aMC.abrirBaseDatos(this);
            //... registrar datos
            String codEBR= aCodigo.getText().toString().substring(0,2), codC= aCodigo.getText().toString().substring(3,5), cod= aCodigo.getText().toString().substring(6);
            int r= 0;
            if (aIdentidad.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "01", "01", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aConvive.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "01", "02", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aDios.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "01", "03", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aMotricidad.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "02", "01", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aComunica.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "03", "01", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aLee.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "03", "02", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aEscribe.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "03", "03", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aCrea.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "03", "04", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aCastellano.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "04", "01", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aCantidad.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "05", "01", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aEspacio.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "05", "02", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            if (aExplora.isChecked())
                r= aMC.registrarSesion(aBD, codEBR, codC, Integer.parseInt(cod), aSelector.getText().toString(),
                        "06", "01", aFecha.getText().toString(), aDenominacion.getText().toString(), "", "No", "",
                        aDNI.getText().toString(), aFechaR.getText().toString());
            //... cerrar base de datos
            aBD.close();
            //... mensaje
            if (r == 0)
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
                aSelector.setText("---");
                mostrarTablaS();
            }
        }
    }
    public void eliminar(View view){
        if(aSelector.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione a un estudiante", Toast.LENGTH_LONG).show();
        else {
            aBD= aMC.abrirBaseDatos(this);
            //... eliminar datos
            String codEBR= aCodigo.getText().toString().substring(0,2), codC= aCodigo.getText().toString().substring(3,5), cod= aCodigo.getText().toString().substring(6);
            int r = aBD.delete("tSesion", "codigoEBR='"+codEBR+"' and codigoCi='"+codC+"' and codigoCa="+cod+" and dniE='"+aDNI.getText().toString()+"' and fecha='"+aFecha.getText().toString()+"'", null);
            //... cerrar base de datos
            aBD.close();
            //... mensaje
            if(r==1) {
                Toast.makeText(this, "Eliminación exitosa", Toast.LENGTH_LONG).show();
                mostrarTablaS();
            }
            else if(r==2)
                Toast.makeText(this, "DNI incorrecto", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Ingrese el DNI", Toast.LENGTH_LONG).show();
            aSelector.setText("---");
        }
    }
}