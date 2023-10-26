package com.example.evaluacionformativa;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class cComun {
    //... atributos
    private Calendar aCalendario;
    //... constructor
    protected cComun(){
        aCalendario= Calendar.getInstance();
    }
    //... metodos
    public SQLiteDatabase abrirBaseDatos(Context pContexto){
        cBD ef= new cBD(pContexto, "bdEF", null, 1);
        //... abrir base de datos lectura / escritura
        SQLiteDatabase bd= ef.getWritableDatabase();
        return bd;
    }
    public boolean isNumeric(String pNumero){
        try{
            Integer.parseInt(pNumero);
            return true;
        }catch (NumberFormatException nfe){ return false; }
    }
    public String fechaActual(){
        int dia= aCalendario.get(Calendar.DAY_OF_MONTH), mes= aCalendario.get(Calendar.MONTH)+1, anio= aCalendario.get(Calendar.YEAR);
        String fecha= anio+"/"+mes+"/"+dia;
        if(dia<10 && mes<10)
            fecha= anio+"/0"+mes+"/0"+dia;
        if(dia<10 && mes>9)
            fecha= anio+"/"+mes+"/0"+dia;
        if(dia>9 && mes<10)
            fecha= anio+"/0"+mes+"/"+dia;
        return fecha;
    }
    public void calendario(Context pContexto, EditText pFecha){
        int dia= aCalendario.get(Calendar.DAY_OF_MONTH), mes= aCalendario.get(Calendar.MONTH), anio= aCalendario.get(Calendar.YEAR);
        DatePickerDialog dpd= new DatePickerDialog(pContexto, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pFecha.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                if(dayOfMonth<10 && month+1<10)
                    pFecha.setText(year+"/0"+(month+1)+"/0"+dayOfMonth);
                if(dayOfMonth<10 && month+1>9)
                    pFecha.setText(year+"/"+(month+1)+"/0"+dayOfMonth);
                if(dayOfMonth>9 && month+1<10)
                    pFecha.setText(year+"/0"+(month+1)+"/"+dayOfMonth);
            }
        }, anio, mes, dia);
        dpd.show();
    }
    public ArrayAdapter<String> eCivil(Context pContexto){
        ArrayAdapter<String> aLista= new ArrayAdapter<>(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("Soltero (a)"); aLista.add("Casado (a)"); aLista.add("Viudo (a)"); aLista.add("Divorsiado (a)");
        return aLista;
    }
    public void calendario(Context pContexto, TextView pFecha){
        int dia= aCalendario.get(Calendar.DAY_OF_MONTH), mes= aCalendario.get(Calendar.MONTH), anio= aCalendario.get(Calendar.YEAR);
        DatePickerDialog dpd= new DatePickerDialog(pContexto, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },anio, mes, dia);
        dpd.show();
    }
    //... registros
    public int registrarProfesor(SQLiteDatabase pBD, String pDNI, String pAp,
                                 String pNom, int pS, int pEc, String pFn, String pFr){
        int r= 0;
        //... validad datos
        if(!pDNI.isEmpty() && !pAp.isEmpty() && !pNom.isEmpty() && !pFn.isEmpty()){
            //... cargar valores
            ContentValues registro= new ContentValues();
            registro.put("dni", pDNI);
            registro.put("apellidos", pAp);
            registro.put("nombres", pNom);
            registro.put("sexo",pS);
            registro.put("eCivil", pEc);
            registro.put("fechaN", pFn);
            registro.put("fechaR", pFr);
            //... cargar datos a la tabla
            pBD.insert("tProfesor", null, registro);
            r= 1;
        }
        return r;
    }
    public int registrarEstudiante(SQLiteDatabase pBD, String pDNIe, String pAp, String pNom, int pS,
                                   int pEc, String pFn, String pCel, String pDNIp, String pFr){
        int r= 0;
        //... validad datos
        if(!pDNIe.isEmpty() && !pAp.isEmpty() && !pNom.isEmpty() && !pFn.isEmpty() && !pCel.isEmpty() && !pDNIp.isEmpty()){
            //... cargar valores
            ContentValues registro= new ContentValues();
            registro.put("dni", pDNIe);
            registro.put("apellidos", pAp);
            registro.put("nombres", pNom);
            registro.put("sexo",pS);
            registro.put("eCivil",pEc);
            registro.put("fechaN", pFn);
            registro.put("celular", pCel);
            registro.put("dniP", pDNIp);
            registro.put("fechaR", pFr);
            //... cargar datos a la tabla
            pBD.insert("tEstudiante", null, registro);
            r= 1;
        }
        return r;
    }
    public int registrarCarga(SQLiteDatabase pBD, String pDen,String pAnio, String pDNI, String pCodigoEBR, String pCodigoC, String pFr){
        int r= 0;
        //... validad datos
        if(!pDen.isEmpty() && !pAnio.isEmpty() && !pDNI.isEmpty() && !pCodigoEBR.isEmpty() && !pCodigoC.isEmpty()){
            //... cargar valores
            ContentValues registro= new ContentValues();
            registro.put("denominacion", pDen);
            registro.put("anio", pAnio);
            registro.put("dni", pDNI);
            registro.put("codigoEBR", pCodigoEBR);
            registro.put("codigoC", pCodigoC);
            registro.put("fechaR", pFr);
            //... cargar datos a la tabla
            pBD.insert("tCarga", null, registro);
            r= 1;
        }
        return r;
    }
    public int registrarSesion(SQLiteDatabase pBD, String pCodEBR, String pCodCi, int pCodCa, String pDNIE,
                               String pCodPE, String pCodCo, String pFecha, String pDen, String pNota,
                               String pAp, String pEs, String pDNIP, String pFr){
        int r= 0;
        //... validad datos
        if(!pCodEBR.isEmpty() && !pCodCi.isEmpty() && !pDNIE.isEmpty() && !pCodPE.isEmpty() && !pCodCo.isEmpty() && !pFecha.isEmpty() && !pDen.isEmpty() && !pDNIP.isEmpty() && !pFr.isEmpty()){
            //... cargar valores
            ContentValues registro= new ContentValues();
            registro.put("codigoEBR", pCodEBR);
            registro.put("codigoCi", pCodCi);
            registro.put("codigoCa", pCodCa);
            registro.put("dniE", pDNIE);
            registro.put("codigoPE", pCodPE);
            registro.put("codigoCo", pCodCo);
            registro.put("fecha", pFecha);
            registro.put("denominacion", pDen);
            registro.put("nota", pNota);
            registro.put("aplicacion", pAp);
            registro.put("estrategia", pEs);
            registro.put("dniP", pDNIP);
            registro.put("fechaR", pFr);
            //... cargar datos a la tabla
            pBD.insert("tSesion", null, registro);
            r= 1;
        }
        return r;
    }
    //... consultas
    public boolean existeProfesor(SQLiteDatabase pBD, String pDNI){
        boolean r= false;
        //... validar datos
        if(!pDNI.isEmpty()){
            Cursor fila= pBD.rawQuery("select nombres from tProfesor where dni="+pDNI, null);
            r= fila.moveToFirst();
        }
        return r;
    }
    public boolean existeEstudiante(SQLiteDatabase pBD, String pDNI){
        boolean r= false;
        //... validar datos
        if(!pDNI.isEmpty()){
            Cursor fila= pBD.rawQuery("select nombres from tEstudiante where dni="+pDNI, null);
            r= fila.moveToFirst();
        }
        return r;
    }
    public ArrayAdapter tablaProfesores(Context pContexto, SQLiteDatabase pBD){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("DNI"); aLista.add("APELLIDOS"); aLista.add("NOMBRES");
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select dni, apellidos, nombres from tProfesor", null);
        if(fila.moveToFirst()){
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1));
            aLista.add(fila.getString(2));
        }
        while(fila.moveToNext()){
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1));
            aLista.add(fila.getString(2));
        }
        return aLista;
    }
    public ArrayAdapter tablaEstudiantes(Context pContexto, SQLiteDatabase pBD){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("DNI"); aLista.add("APELLIDOS"); aLista.add("NOMBRES"); aLista.add("CELULAR");
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select dni, apellidos, nombres, celular from tEstudiante", null);
        if(fila.moveToFirst()){
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1));
            aLista.add(fila.getString(2)); aLista.add(fila.getString(3));
        }
        while(fila.moveToNext()){
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1));
            aLista.add(fila.getString(2)); aLista.add(fila.getString(3));
        }
        return aLista;
    }
    public ArrayAdapter tablaEstudiante(Context pContexto, SQLiteDatabase pBD){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("DNI"); aLista.add("APELLIDOS"); aLista.add("NOMBRES");
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select dni, apellidos, nombres from tEstudiante", null);
        if(fila.moveToFirst()){
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1));
            aLista.add(fila.getString(2));
        }
        while(fila.moveToNext()){
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1));
            aLista.add(fila.getString(2));
        }
        return aLista;
    }
    public ArrayAdapter tablaEBR(Context pContexto, SQLiteDatabase pBD){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("CÓDIGO"); aLista.add("DENOMINACIÓN");
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select codigo, denominacion from tEBR", null);
        if(fila.moveToFirst()) {
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1));
        }
        while(fila.moveToNext()) {
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1));
        }
        return aLista;
    }
    public ArrayAdapter tablaCarga(Context pContexto, SQLiteDatabase pBD, String pDNI){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("CÓDIGO"); aLista.add("DENOMINACIÓN"); aLista.add("EBR"); aLista.add("AÑO LECTIVO");
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select codigoEBR, codigoC, c.codigo, c.denominacion, e.denominacion, anio " +
                "from tCarga c " +
                "inner join tEBR e on codigoEBR=e.codigo " +
                "where dni="+pDNI, null);
        if(fila.moveToFirst()) {
            aLista.add(fila.getString(0)+"-"+fila.getString(1)+"-"+fila.getString(2));
            aLista.add(fila.getString(3));aLista.add(fila.getString(4)); aLista.add(fila.getString(5));
        }
        while(fila.moveToNext()) {
            aLista.add(fila.getString(0)+"-"+fila.getString(1)+"-"+fila.getString(2));
            aLista.add(fila.getString(3));aLista.add(fila.getString(4)); aLista.add(fila.getString(5));
        }
        return aLista;
    }

    public ArrayAdapter tablaCiclo(Context pContexto, SQLiteDatabase pBD){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("CÓDIGO"); aLista.add("EBR"); aLista.add("DENOMINACIÓN");
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select c.codigoEBR, c.codigo, e.denominacion, c.denominacion " +
                "from tCiclo c " +
                "inner join tEBR e on c.codigoEBR = e.codigo", null);
        if(fila.moveToFirst()) {
            aLista.add(fila.getString(0)+"-"+fila.getString(1));
            aLista.add(fila.getString(2)); aLista.add(fila.getString(3));
        }
        while(fila.moveToNext()) {
            aLista.add(fila.getString(0)+"-"+fila.getString(1));
            aLista.add(fila.getString(2)); aLista.add(fila.getString(3));
        }
        return aLista;
    }
    public ArrayAdapter tablaPlan(Context pContexto, SQLiteDatabase pBD){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("EBR"); aLista.add("CÓDIGO"); aLista.add("DENOMINACIÓN");
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select e.denominacion, p.codigo, p.denominacion " +
                "from tPlanEstudio p " +
                "inner join tEBR e on p.codigoEBR = e.codigo", null);
        if(fila.moveToFirst()) {
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1)); aLista.add(fila.getString(2));
        }
        while(fila.moveToNext()) {
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1)); aLista.add(fila.getString(2));
        }
        return aLista;
    }
    public ArrayAdapter tablaSesion(Context pContexto, SQLiteDatabase pBD, String pDNI, String pCod){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("SESION"); aLista.add("FECHA"); aLista.add("EBR"); aLista.add("NIVEL"); aLista.add("GRADO");
        String codEBR= pCod.substring(0,2), codC= pCod.substring(3,5), cod= pCod.substring(6);
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select distinct s.denominacion, fecha, e.denominacion, c.denominacion, a.denominacion " +
                "from tSesion s " +
                "inner join tEBR e on s.codigoEBR=e.codigo " +
                "inner join tCiclo c on s.codigoEBR=c.codigoEBR and s.codigoCi=c.codigo " +
                "inner join tCarga a on s.codigoEBR=a.codigoEBR and s.codigoCi=a.codigoC and s.codigoCa=a.codigo " +
                "where s.dniP='"+pDNI+"' and s.codigoEBR='"+codEBR+"' and s.codigoCi='"+codC+"' and s.codigoCa="+cod+" "+
                "order by s.codigoEBR, c.codigo, a.codigo, fecha", null);
        if(fila.moveToFirst()) {
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1)); aLista.add(fila.getString(2));
            aLista.add(fila.getString(3)); aLista.add(fila.getString(4));
        }
        while(fila.moveToNext()) {
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1)); aLista.add(fila.getString(2));
            aLista.add(fila.getString(3)); aLista.add(fila.getString(4));
        }
        return aLista;
    }
    public ArrayAdapter tablaSesionEstudiante(Context pContexto, SQLiteDatabase pBD, String pDNI, String pCod, String pFecha){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("DNI"); aLista.add("APELLIDOS"); aLista.add("NOMBRES");
        String codEBR= pCod.substring(0,2), codC= pCod.substring(3,5), cod= pCod.substring(6);
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select distinct s.dniE, e.apellidos, e.nombres " +
                "from tSesion s " +
                "inner join tEstudiante e on s.dniE=e.dni " +
                "where s.dniP='"+pDNI+"' and codigoEBR='"+codEBR+"' and codigoCi='"+codC+"' and codigoCa="+cod +" and fecha='"+pFecha+"' "+
                "order by e.apellidos", null);
        if(fila.moveToFirst()) {
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1)); aLista.add(fila.getString(2));
        }
        while(fila.moveToNext()) {
            aLista.add(fila.getString(0)); aLista.add(fila.getString(1)); aLista.add(fila.getString(2));
        }
        return aLista;
    }
    public ArrayAdapter tablaAreaCEstudiante(Context pContexto, SQLiteDatabase pBD, String pDNI, String pCod, String pFecha){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("NOMBRES - APELLIDOS"); aLista.add("COMPETENCIA"); aLista.add("CALIF.");
        aLista.add("DNI"); aLista.add("CODIGO");
        String codEBR= pCod.substring(0,2), codC= pCod.substring(3,5), cod= pCod.substring(6);
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select e.nombres, e.apellidos, p.sigla, c.sigla, nota, s.dniE, s.codigoPE || s.codigoCo " +
                "from tSesion s " +
                "inner join tEstudiante e on s.dniE=e.dni " +
                "inner join tPlanEstudio p on s.codigoEBR=p.codigoEBR and s.codigoPE=p.codigo " +
                "inner join tCompetencia c on s.codigoEBR=c.codigoEBR and s.codigoPE=c.codigoPE and s.codigoCo=c.codigo " +
                "where s.dniP='"+pDNI+"' and s.codigoEBR='"+codEBR+"' and s.codigoCi='"+codC+"' and s.codigoCa="+cod+" and fecha='"+pFecha+"' "+
                "order by s.dniE", null);
        //... cargar datos
        if(fila.moveToFirst()) {
            aLista.add(fila.getString(0)+"-"+fila.getString(1));
            aLista.add(fila.getString(2)+"-"+fila.getString(3)); aLista.add(fila.getString(4));
            aLista.add(fila.getString(5)); aLista.add(fila.getString(6));
        }
        while(fila.moveToNext()) {
            aLista.add(fila.getString(0)+"-"+fila.getString(1));
            aLista.add(fila.getString(2)+"-"+fila.getString(3)); aLista.add(fila.getString(4));
            aLista.add(fila.getString(5)); aLista.add(fila.getString(6));
        }
        return aLista;
    }
    public ArrayAdapter tablaSeguimiento(Context pContexto, SQLiteDatabase pBD, String pDNI, String pCod){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        aLista.add("NOMBRES - APELLIDOS"); aLista.add("COMPETENCIA"); aLista.add("CAL.-EST.");
        aLista.add("FECHA"); aLista.add("DNI"); aLista.add("CODIGO");
        String codEBR= pCod.substring(0,2), codC= pCod.substring(3,5), cod= pCod.substring(6);
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select e.nombres, e.apellidos, p.sigla, c.sigla, nota, aplicacion, fecha, s.dniE, s.codigoPE || s.codigoCo " +
                "from tSesion s " +
                "inner join tEstudiante e on s.dniE=e.dni " +
                "inner join tPlanEstudio p on s.codigoEBR=p.codigoEBR and s.codigoPE=p.codigo " +
                "inner join tCompetencia c on s.codigoEBR=c.codigoEBR and s.codigoPE=c.codigoPE and s.codigoCo=c.codigo " +
                "where s.dniP='"+pDNI+"' and s.codigoEBR='"+codEBR+"' and s.codigoCi='"+codC+"' and s.codigoCa="+cod+" "+
                "order by fecha, s.dniE", null);
        //... cargar datos
        if(fila.moveToFirst()) {
            aLista.add(fila.getString(0)+"-"+fila.getString(1));
            aLista.add(fila.getString(2)+"-"+fila.getString(3));
            aLista.add(fila.getString(4)+"-"+fila.getString(5)); aLista.add(fila.getString(6));
            aLista.add(fila.getString(7)); aLista.add(fila.getString(8));
        }
        while(fila.moveToNext()) {
            aLista.add(fila.getString(0)+"-"+fila.getString(1));
            aLista.add(fila.getString(2)+"-"+fila.getString(3));
            aLista.add(fila.getString(4)+"-"+fila.getString(5)); aLista.add(fila.getString(6));
            aLista.add(fila.getString(7)); aLista.add(fila.getString(8));
        }
        return aLista;
    }
    public ArrayAdapter listaEBR(Context pContexto, SQLiteDatabase pBD){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select codigo, denominacion from tEBR", null);
        if(fila.moveToFirst())
            aLista.add(fila.getString(0)+": "+fila.getString(1));
        while(fila.moveToNext())
            aLista.add(fila.getString(0)+": "+fila.getString(1));
        return aLista;
    }
    public ArrayAdapter listaCiclo(Context pContexto, SQLiteDatabase pBD, String pCodigoEBR){
        ArrayAdapter aLista= new ArrayAdapter(pContexto, android.R.layout.simple_spinner_item);
        //... realizar consulta
        Cursor fila= pBD.rawQuery("select codigo, denominacion from tCiclo where codigoEBR='"+pCodigoEBR+"'", null);
        if(fila.moveToFirst())
            aLista.add(fila.getString(0)+": "+fila.getString(1));
        while(fila.moveToNext())
            aLista.add(fila.getString(0)+": "+fila.getString(1));
        return aLista;
    }
    //... mostrar titulo
    public void mostrarTitulo(SQLiteDatabase pBD, String pDNI, String pCod, TextView pEBR, TextView pCi, TextView pAu, TextView pAnio) {
        //... validar datos
        if (!pDNI.isEmpty() && !pCod.isEmpty()) {
            String codEBR= pCod.substring(0,2), codC= pCod.substring(3,5), cod= pCod.substring(6);
            if(isNumeric(cod)) {
                //... realizar consulta
                Cursor fila= pBD.rawQuery("select e.denominacion, ci.denominacion, ca.denominacion, anio " +
                        "from tCarga ca " +
                        "inner join tEBR e on ca.codigoEBR=e.codigo " +
                        "inner join tCiclo ci on ca.codigoEBR=ci.codigoEBR and ca.codigoC=ci.codigo " +
                        "where ca.codigoEBR='"+codEBR+"' and ca.codigoC='"+codC+"' and ca.codigo="+cod+" and dni='"+pDNI+"'", null);
                //... verificar si la fila contiene datos
                if (fila.moveToFirst()) {
                    pEBR.setText("EBR: "+fila.getString(0));
                    pCi.setText("Nivel: "+fila.getString(1));
                    pAu.setText("Aula: "+fila.getString(2));
                    pAnio.setText("Año académico: "+fila.getString(3));
                }
            }
        }
    }
    //... mostrar
    public int mostrarProfesor(SQLiteDatabase pBD, String pDNI, EditText pAp, EditText pNom, RadioButton pM,
                               RadioButton pF, Spinner pEc, EditText pFn) {
        int r = 0;
        //... validar datos
        if (!pDNI.isEmpty()) {
            Cursor fila = pBD.rawQuery("select apellidos, nombres, sexo, eCivil, fechaN from tProfesor where dni=" + pDNI, null);
            //... verificar si la fila contiene datos
            if (fila.moveToFirst()) {
                pAp.setText(fila.getString(0));
                pNom.setText(fila.getString(1));
                if (fila.getInt(2) == 1)
                    pM.setChecked(true);
                else
                    pF.setChecked(true);
                pEc.setSelection(fila.getInt(3));
                pFn.setText(fila.getString(4));
                r = 1; //... encontrado
            } else { r = 2; }
        }
        return r;
    }
    public int mostrarCarga(SQLiteDatabase pBD, String pDNI, String pCod, EditText pDen, EditText pAnio) {
        int r = 0;
        //... validar datos
        if (!pDNI.isEmpty() && !pCod.isEmpty()) {
            String codEBR= pCod.substring(0,2), codC= pCod.substring(3,5), cod= pCod.substring(6);
            if(isNumeric(cod)) {
                Cursor fila= pBD.rawQuery("select denominacion, anio from tCarga where codigoEBR='" + codEBR + "' and codigoC='" + codC + "' and codigo=" + cod + " and dni=" + pDNI, null);
                //... verificar si la fila contiene datos
                if (fila.moveToFirst()) {
                    pDen.setText(fila.getString(0));
                    pAnio.setText(fila.getString(1));
                    r = 1; //... encontrado
                } else { r = 2; }
            }
        }
        return r;
    }
    public int mostrarEstudiante(SQLiteDatabase pBD, String pDNI, EditText pAp, EditText pNom, RadioButton pM, RadioButton pF,
                                 Spinner pEc, EditText pFn, EditText pCel) {
        int r = 0;
        //... validar datos
        if (!pDNI.isEmpty()) {
            Cursor fila = pBD.rawQuery("select apellidos, nombres, sexo, eCivil, fechaN, celular from tEstudiante where dni=" + pDNI, null);
            //... verificar si la fila contiene datos
            if (fila.moveToFirst()) {
                pAp.setText(fila.getString(0));
                pNom.setText(fila.getString(1));
                if (fila.getInt(2) == 1)
                    pM.setChecked(true);
                else
                    pF.setChecked(true);
                pEc.setSelection(fila.getInt(3));
                pFn.setText(fila.getString(4));
                pCel.setText(fila.getString(5));
                r = 1; //... encontrado
            } else { r = 2; }
        }
        return r;
    }
    public int mostrarEstrategia(SQLiteDatabase pBD, String pCod, String pDNIE, String pCodS, String pFecha, String pDNI, EditText pEst) {
        int r = 0;
        //... validar datos
        if (!pCod.isEmpty() && !pDNIE.isEmpty() && !pCodS.isEmpty() && !pFecha.isEmpty() && !pDNI.isEmpty()) {
            String codEBR = pCod.substring(0, 2), codC = pCod.substring(3, 5), cod = pCod.substring(6);
            String codPE = pCodS.substring(0, 2), codCo = pCodS.substring(2);
            Cursor fila = pBD.rawQuery("select estrategia from tSesion where dniP='"+pDNI+"' " +
                    "and codigoEBR='"+codEBR+"' and codigoCi='"+codC+"' and codigoCa="+cod+" and " +
                    "codigoPE='"+codPE+"' and codigoCo='"+codCo+"' and fecha='"+pFecha+"'", null);
            //... verificar si la fila contiene datos
            if (fila.moveToFirst()) {
                pEst.setText(fila.getString(0));
                r = 1; //... encontrado
            } else { r = 2; }
        }
        return r;
    }
    //... modificar
    public int modificarProfesor(SQLiteDatabase pBD, String pDNI, String pAp, String pNom, int pS,
                                 int pEc, String pFn, String pFr){
        int r= 0;
        //... validar datos
        if(!pDNI.isEmpty() && !pAp.isEmpty() && !pNom.isEmpty() && !pFn.isEmpty()){
            //... cargar valores
            ContentValues registro= new ContentValues();
            registro.put("dni", pDNI);
            registro.put("apellidos", pAp);
            registro.put("nombres", pNom);
            registro.put("sexo",pS);
            registro.put("eCivil", pEc);
            registro.put("fechaN", pFn);
            registro.put("fechaR", pFr);
            //... modificar
            r= pBD.update("tProfesor", registro, "dni="+pDNI, null);
        }
        return r;
    }
    public int modificarEstudiante(SQLiteDatabase pBD, String pDNIe, String pAp, String pNom, int pS,
                                   int pEc, String pFn, String pCel, String pDNIp, String pFr){
        int r= 0;
        //... validar datos
        if(!pDNIe.isEmpty() && !pAp.isEmpty() && !pNom.isEmpty() && !pFn.isEmpty() && !pCel.isEmpty() && !pDNIp.isEmpty()){
            //... cargar valores
            ContentValues registro= new ContentValues();
            registro.put("dni", pDNIe);
            registro.put("apellidos", pAp);
            registro.put("nombres", pNom);
            registro.put("sexo", pS);
            registro.put("eCivil", pEc);
            registro.put("fechaN", pFn);
            registro.put("celular", pCel);
            registro.put("dniP", pDNIp);
            registro.put("fechaR", pFr);
            //... modificar
            r= pBD.update("tEstudiante", registro, "dni="+pDNIe, null);
        }
        return r;
    }
    public int modificarCarga(SQLiteDatabase pBD, String pDNI, String pCod, String pDen, String pAnio, String pFr) {
        int r = 0;
        //... validar datos
        if (!pDNI.isEmpty() && !pCod.isEmpty() && !pDen.isEmpty() && !pAnio.isEmpty()) {
            String codEBR = pCod.substring(0, 2), codC = pCod.substring(3, 5), cod = pCod.substring(6);
            if (isNumeric(cod)) {
                //... cargar valores
                ContentValues registro = new ContentValues();
                registro.put("denominacion", pDen);
                registro.put("anio", pAnio);
                registro.put("fechaR", pFr);
                //... modificar
                r= pBD.update("tCarga", registro, "codigoEBR='"+codEBR+"' and codigoC='"+codC+"' and codigo="+cod+" and dni="+pDNI, null);
            }
        }
        return r;
    }
    public int modificarSesion(SQLiteDatabase pBD, String pCod, String pDNIE, String pCodS, String pFecha, String pNota, String pDNI, String pFr) {
        int r = 0;
        //... validar datos
        if (!pCod.isEmpty() &&  !pDNIE.isEmpty() && !pCodS.isEmpty() && !pNota.isEmpty() && !pDNI.isEmpty() && !pFr.isEmpty()) {
            String codEBR = pCod.substring(0, 2), codC = pCod.substring(3, 5), cod = pCod.substring(6);
            String codPE = pCodS.substring(0, 2), codCo = pCodS.substring(2);
            if (isNumeric(cod)) {
                //... cargar valores
                ContentValues registro = new ContentValues();
                registro.put("nota", pNota);
                registro.put("fechaR", pFr);
                //... modificar
                r= pBD.update("tSesion", registro, "codigoEBR='"+codEBR+"' and codigoCi='"+
                        codC+"' and codigoCa="+cod+" and dniE='"+pDNIE+"' and codigoPE='"+codPE+"' and codigoCo='"+
                        codCo+"' and fecha='"+pFecha+"' and dniP="+pDNI, null);
            }
        }
        return r;
    }
    public int modificarEstrategia(SQLiteDatabase pBD, String pCod, String pDNIE, String pCodS, String pFecha, String pApl, String pEst, String pDNI, String pFr) {
        int r = 0;
        //... validar datos
        if (!pCod.isEmpty() &&  !pDNIE.isEmpty() && !pCodS.isEmpty() && !pFecha.isEmpty() && !pApl.isEmpty() && !pEst.isEmpty() && !pFr.isEmpty()) {
            String codEBR = pCod.substring(0, 2), codC = pCod.substring(3, 5), cod = pCod.substring(6);
            String codPE = pCodS.substring(0, 2), codCo = pCodS.substring(2);
            if (isNumeric(cod)) {
                //... cargar valores
                ContentValues registro = new ContentValues();
                registro.put("aplicacion", pApl);
                registro.put("estrategia", pEst);
                registro.put("fechaR", pFr);
                //... modificar
                r= pBD.update("tSesion", registro, "codigoEBR='"+codEBR+"' and codigoCi='"+
                        codC+"' and codigoCa="+cod+" and dniE='"+pDNIE+"' and codigoPE='"+codPE+"' and codigoCo='"+
                        codCo+"' and fecha='"+pFecha+"' and dniP="+pDNI, null);
            }
        }
        return r;
    }
    //... eliminar
    public int eliminarProfesor(SQLiteDatabase pBD, String pDNI){
        int r= 0;
        //... validar datos
        if(!pDNI.isEmpty()) {
            //... eliminar
            r = pBD.delete("tProfesor", "dni=" + pDNI, null);
        }
        return r;
    }
    public int eliminarEstudiante(SQLiteDatabase pBD, String pDNI){
        int r= 0;
        //... validar datos
        if(!pDNI.isEmpty()) {
            //... eliminar
            r = pBD.delete("tEstudiante", "dni=" + pDNI, null);
        }
        return r;
    }
    public int eliminarCarga(SQLiteDatabase pBD, String pDNI, String pCod){
        int r= 0;
        //... validar datos
        if(!pDNI.isEmpty() && !pCod.isEmpty()) {
            String codEBR = pCod.substring(0, 2), codC = pCod.substring(3, 5), cod = pCod.substring(6);
            if(isNumeric(cod))
                r= pBD.delete("tCarga","codigoEBR='"+codEBR+"' and codigoC='"+codC+"' and codigo="+cod+" and dni="+pDNI, null);
        }
        return r;
    }
}
