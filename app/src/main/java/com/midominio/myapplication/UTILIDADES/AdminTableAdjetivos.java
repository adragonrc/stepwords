package com.midominio.myapplication.UTILIDADES;

public class AdminTableAdjetivos {
    public static final String TABLE_NAME = "Adjetivos";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_ADJETIVO = "ADJECTIVE";
    public static final String COLUMNA_COMPARATIVO = "COMPARATIVE";
    public static final String COLUMNA_SUPERLATIVO ="SUPERLATIVE";
    public static final String COLUMNA_TRADUCCION = "SPANISH";
    public static final String COLUMNA_INSTRUCCION ="CHANGE TO ";
    public static final String [] descripciones = {COLUMNA_INSTRUCCION,COLUMNA_COMPARATIVO,COLUMNA_SUPERLATIVO, COLUMNA_TRADUCCION};
    public static final String FOR_GET = COLUMNA_ADJETIVO + "," + COLUMNA_COMPARATIVO + "," + COLUMNA_SUPERLATIVO + "," + COLUMNA_TRADUCCION;
    public static int Max = 149;
    public static String namePosPalabraMax = "spAdjPosMax";
    public static int posPalabraMax = 35;
    public static final String CREATE_TABLE = "create table "+ TABLE_NAME + "(" + COLUMNA_ID +
            " integer primary key, "+
            COLUMNA_ADJETIVO + " text not null, " +
            COLUMNA_COMPARATIVO +" text not null, " +
            COLUMNA_SUPERLATIVO + " text not null, "+
            COLUMNA_TRADUCCION + " text not null)";
}
