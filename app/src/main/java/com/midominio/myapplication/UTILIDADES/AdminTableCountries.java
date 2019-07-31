package com.midominio.myapplication.UTILIDADES;

public class AdminTableCountries {
    public static final String TABLE_NAME = "Countries";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_VERBO = " ";
    public static final String COLUMNA_PAST_SIMPLE = "PAST_SIMPLE";
    public static final String COLUMNA_PAST_PARTICIPLE ="PAST_PARTICIPLE";
    public static final String COLUMNA_TRADUCCION = "ESPAÑOL ";
    public static final String COLUMNA_INSTRUCCION ="Escribe el ";
    public static final String [] descripciones = {COLUMNA_INSTRUCCION,"PAST SIMPLE","PAST PARTICIPLE","ESPAÑOL"};
    public static final String FOR_GET = COLUMNA_VERBO + "," + COLUMNA_PAST_SIMPLE + "," + COLUMNA_PAST_PARTICIPLE + "," + COLUMNA_TRADUCCION;

    public static final String CREATE_TABLE = "create table "+ TABLE_NAME + "(" + COLUMNA_ID +
            " integer primary key, "+
            COLUMNA_VERBO + " text not null, " +
            COLUMNA_PAST_SIMPLE +" text not null, " +
            COLUMNA_PAST_PARTICIPLE + " text not null, "+
            COLUMNA_TRADUCCION + " text not null)";


}
