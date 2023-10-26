package com.example.evaluacionformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //... atributos
    private GridView aTablaP, aTablaC, aTablaS;
    private TextView aDNI, aDenominacionP, aDenominacionC, aDenominacionS, aFechaR, aCodigoC, aFecha;
    private cComun aMC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //... establecer enlace
        aTablaP= findViewById(R.id.gvTablaPM);
        aTablaC= findViewById(R.id.gvTablaCM);
        aTablaS= findViewById(R.id.gvTablaMM);
        aDNI= findViewById(R.id.tvDNIM);
        aDenominacionP= findViewById(R.id.tvDenominacionPM);

        aFechaR= findViewById(R.id.tvFechaRM);
        aCodigoC= findViewById(R.id.tvCodigoCM);
        aFecha= findViewById(R.id.tvFechaSeM);
        aDenominacionC= findViewById(R.id.tvDenominacionCM);
        aDenominacionS= findViewById(R.id.tvDenominacionSM);
        //... mostrar profesores
        aMC= new cComun();
        aFechaR.setText(aMC.fechaActual());
        mostrarTablaP();
        //... control de tablas
        aTablaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>2){
                    aDNI.setText(aTablaP.getItemAtPosition((position/3)*3).toString());
                    aDenominacionP.setText(aTablaP.getItemAtPosition((position/3)*3+2).toString());
                }else{
                    aDNI.setText("---");
                    aDenominacionP.setText("");
                }
            }
        });
        aTablaC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>3){
                    aCodigoC.setText(aTablaC.getItemAtPosition((position/4)*4).toString());
                    aDenominacionC.setText(aTablaC.getItemAtPosition((position/4)*4+1).toString());
                }else{
                    aCodigoC.setText("---");
                    aDenominacionC.setText("");
                }
            }
        });
        aTablaS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>4) {
                    aDenominacionS.setText(aTablaS.getItemAtPosition((position / 5) * 5).toString());
                    aFecha.setText(aTablaS.getItemAtPosition((position / 5) * 5 + 1).toString());
                }else{
                    aDenominacionS.setText("");
                    aFecha.setText("---");
                }
            }
        });
    }
    //... metodos privados
    private void mostrarTablaP(){
        SQLiteDatabase bd= aMC.abrirBaseDatos(this);
        //... mostrar datos
        aTablaP.setNumColumns(3);
        ArrayAdapter lista= aMC.tablaProfesores(this, bd);
        //... cerrar base de datos
        bd.close();
        //... si hay docentes registrados activar el primero
        aTablaP.setAdapter(lista);
    }
    private void mostrarTablaC(){
        if(!aDNI.getText().toString().equals("---")){
            SQLiteDatabase bd= aMC.abrirBaseDatos(this);
            //... mostrar datos
            aTablaC.setNumColumns(4);
            ArrayAdapter<String> lista= aMC.tablaCarga(this, bd, aDNI.getText().toString());
            //... cerrar base de datos
            bd.close();
            //... si hay docentes registrados activar el primero
            aTablaC.setAdapter(lista);
        }
    }
    private void mostrarTablaS(){
        if(!aDNI.getText().toString().equals("---")){
            SQLiteDatabase bd= aMC.abrirBaseDatos(this);
            //... mostrar datos
            aTablaS.setNumColumns(5);
            ArrayAdapter lista= aMC.tablaSesion(this, bd, aDNI.getText().toString(), aCodigoC.getText().toString());
            //... cerrar base de datos
            bd.close();
            //... si hay docentes registrados activar el primero
            aTablaS.setAdapter(lista);
        }
    }
    //... procesos
    public void cargaAcademica(View view){
        if(aDNI.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione un profesor o registrese como uno", Toast.LENGTH_LONG).show();
        else
            mostrarTablaC();
    }
    public void sesion(View view){
        if(aCodigoC.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione un aula o registre uno", Toast.LENGTH_LONG).show();
        else
            mostrarTablaS();
    }
    public void proyecto(View view){
        if(aFecha.getText().toString().equals("---"))
            Toast.makeText(this, "Selecciones una sesion", Toast.LENGTH_LONG).show();
        else{
            String[] datos= new String[]{aDNI.getText().toString(), aCodigoC.getText().toString(), aDenominacionS.getText().toString(), aFecha.getText().toString(), aFechaR.getText().toString()};
            Intent sig= new Intent(this, proyectoI.class);
            sig.putExtra("datos", datos);
            startActivity(sig);
            finish();
        }
    }
    public void seguimiento(View view){
        if(aCodigoC.getText().toString().equals("---"))
            Toast.makeText(this, "Selecciones un aula o registre uno", Toast.LENGTH_LONG).show();
        else{
            String[] datos= new String[]{aDNI.getText().toString(), aCodigoC.getText().toString(), aFechaR.getText().toString()};
            Intent sig= new Intent(this, seguimientoI.class);
            sig.putExtra("datos", datos);
            startActivity(sig);
            finish();
        }
    }
    public void salir(View view){
        finish();
    }
    //... menu flotante
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mantenimiento, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id == R.id.miProfesor){
            Intent sig= new Intent(this, profesor.class);
            sig.putExtra("fechaR", aFechaR.getText().toString());
            startActivity(sig);
            finish();
        }
        if(aDNI.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione un profesor o registrese como uno", Toast.LENGTH_LONG).show();
        else{
            String[] datos= new String[]{aDNI.getText().toString(), aFechaR.getText().toString()};
            Intent sig= null;
            switch (id){
                case R.id.miCarga: sig= new Intent(this, aula.class); break;
                case R.id.miEBR: sig= new Intent(this, ebr.class); break;
                case R.id.miCiclo: sig= new Intent(this, ciclo.class); break;
                case R.id.miPLan: sig= new Intent(this, planEstudio.class); break;
                case R.id.miEstudiante:sig= new Intent(this, estudiante.class); break;
            }
            if(sig!=null){
                sig.putExtra("datos", datos);
                startActivity(sig);
                finish();
            }
        }
        if(aDNI.getText().toString().equals("---") || aCodigoC.getText().toString().equals("---"))
            Toast.makeText(this, "Seleccione un docente y su aula para iniciar", Toast.LENGTH_LONG).show();
        else {
            if (id == R.id.miSesion) {
                String[] datos= new String[]{aDNI.getText().toString(), aCodigoC.getText().toString(), aFechaR.getText().toString()};
                Intent sig;
                if(aCodigoC.getText().toString().substring(0,2).equals("01"))
                    sig = new Intent(this, sesionI.class);
                else
                    sig = new Intent(this, sesionI.class);
                sig.putExtra("datos", datos);
                startActivity(sig);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}