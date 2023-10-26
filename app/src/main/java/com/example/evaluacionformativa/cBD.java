package com.example.evaluacionformativa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class cBD extends SQLiteOpenHelper {
    //... constructor
    public cBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //... primitivos
    @Override
    public void onCreate(SQLiteDatabase db) {
        //... crear tabla profesor
        db.execSQL("create table tProfesor(dni text primary key unique not null, apellidos text not null, " +
                "nombres text not null, sexo int not null, eCivil int not null, fechaN text not null, " +
                "fechaR text not null)");
        //... crear tabla Estudiante
        db.execSQL("create table tEstudiante(dni text primary key unique not null, apellidos text not null, " +
                "nombres text not null, sexo int not null, eCivil int not null, fechaN text not null, " +
                "celular text not null, dniP text not null, fechaR text not null)");
        //... crear tabla EBR
        db.execSQL("create table tEBR(codigo text primary key unique not null, denominacion text not null, fechaR text not null)");
        db.execSQL("insert into tEBR (codigo, denominacion,fechaR) values('01', 'Inicial', '01/05/2022')");
        db.execSQL("insert into tEBR (codigo, denominacion,fechaR) values('02', 'Primaria', '01/05/2022')");
        db.execSQL("insert into tEBR (codigo, denominacion,fechaR) values('03', 'Secundaria', '01/05/2022')");
        //... crear tabla Ciclo
        db.execSQL("create table tCiclo(codigoEBR text not null, codigo text not null," +
                "denominacion text not null, fechaR text not null, primary key (codigoEBR, codigo))");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('01', '01', '03 años', '01/05/2022')");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('01', '02', '04 años', '01/05/2022')");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('01', '03', '05 años', '01/05/2022')");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('02', '01', '1er grado', '01/05/2022')");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('02', '02', '2do grado', '01/05/2022')");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('02', '03', '3er grado', '01/05/2022')");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('02', '04', '4to grado', '01/05/2022')");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('02', '05', '5to grado', '01/05/2022')");
        db.execSQL("insert into tCiclo (codigoEBR, codigo, denominacion, fechaR) values('02', '06', '6to grado', '01/05/2022')");
        //... crear tabla carga academica
        db.execSQL("create table tCarga(codigo integer primary key autoincrement, denominacion text not null, " +
                "anio text not null, dni text not null, codigoEBR text not null, codigoC text not null, fechaR text not null)");
        //... crear tabla plan estudio
        db.execSQL("create table tPlanEstudio(codigoEBR text not null, codigo text not null, denominacion text not null, " +
                "sigla text not null, fechaR text not null, primary key (codigoEBR, codigo))");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('01', '01', 'Personal social', 'PS', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('01', '02', 'Psicomotriz', 'Ps', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('01', '03', 'Comunicación', 'Co', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('01', '04', 'Castellano como segunda lengua', 'Ca', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('01', '05', 'Matemática', 'Ma', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('01', '06', 'Ciencia y tecnología', 'CT', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '01', 'Personal social', 'PS', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '02', 'Educación religiosa', 'ER', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '03', 'Educación física', 'EF', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '04', 'Comunicación', 'Co', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '05', 'Castellano como segunda lengua', 'Ca', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '06', 'Ingles', 'In', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '07', 'Arte y cultura', 'AC', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '08', 'Matemática', 'Ma', '01/05/2022')");
        db.execSQL("insert into tPlanEstudio(codigoEBR, codigo, denominacion, sigla, fechaR) values('02', '09', 'Ciencia y tecnología', 'CT', '01/05/2022')");
        //... crear tabla competencia
        db.execSQL("create table tCompetencia(codigoEBR text not null, codigoPE text not null, codigo text not null, " +
                "denominacion text not null, sigla text not null, fechaR text not null, primary key (codigoEBR, codigoPE, codigo))");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '01', '01', 'Construye su identidad', 'Identidad', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '01', '02', 'Convive y participa', 'Convive', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '01', '03', 'Comprende que es una persona amada por Dios', 'Dios', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '02', '01', 'Se desenvuelve de manera autónoma a través de su motricidad', 'Motricidad', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '03', '01', 'Se comunica oralmente en lengua materna', 'Comunica', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '03', '02', 'Lee diversos tipos de textos escritos', 'Lee', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '03', '03', 'Escribe diversos tipos de textos', 'Escribe', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '03', '04', 'Crea proyectos artísticos', 'Crea', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '04', '01', 'Se comunica oralmente en castellano como segunda lengua', 'Castellano', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '05', '01', 'Construye la noción de cantidad', 'Cantidad', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '05', '02', 'Establece relaciones espaciales', 'Espacio', '01/05/2022')");
        db.execSQL("insert into tCompetencia(codigoEBR, codigoPE, codigo, denominacion, sigla, fechaR) values('01', '06', '01', 'Explora su entorno para conocerlo', 'Explora', '01/05/2022')");
        //... crear tabla Proyecto sesion
        db.execSQL("create table tSesion(codigoEBR text not null, codigoCi text not null, codigoCa int not null, " +
                "dniE text not null, codigoPE text not null, codigoCo text not null, fecha text not null, " +
                "denominacion text not null, nota text, aplicacion text, estrategia text, dniP text not null, fechaR text not null, " +
                "primary key(codigoEBR, codigoCi, codigoCa, dniE, codigoPE, codigoCo, fecha))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
