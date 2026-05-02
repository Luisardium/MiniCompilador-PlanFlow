package gt.edu.miumg;

import java_cup.runtime.*;
import java.util.ArrayList;

%%
%public
%class A_Lexico
%cupsym sym
%cup
%unicode
%line
%column

%{
    // -------------------- codigo para manejo de la tabla de errores ----------------


    public static class Simbolo {
        String lexema;
        String tipo;
        int linea;
        int columna;

    public Simbolo(String lexema, String tipo, int linea, int columna) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public String toString() {
        return lexema + " - " + tipo + " - Línea: " + linea + " - Col: " + columna;
    }
}
public static ArrayList<Simbolo> tablaSimbolos = new ArrayList<>();


    // -------------- Codigo para Tabla de Errores lexicos ---------------

    public static class ErrorLexico {
    String lexema;
    String descripcion;
    int linea;
    int columna;

    public ErrorLexico(String lexema, String descripcion, int linea, int columna) {
        this.lexema = lexema;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Error: " + lexema + " - " + descripcion + " - Línea: " + linea + " - Col: " + columna;
    }
}

public static ArrayList<ErrorLexico> tablaErrores = new ArrayList<>();

//------------------ Implementacion con CUP ------------------------------
      private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
//---------------------- Tabla de errores Lexicos ----------------
public static ArrayList<String> errores = new ArrayList<>();

    public void agregarError(String desc, String causa) {
    errores.add(String.format("%-35s %-10d %-10d %-30s",
        desc,
        yyline + 1,
        yycolumn + 1,
        causa
    ));
}

%}

%eofval{
    return symbol(ParserSym.EOF);
%eofval}

LETRA = [a-zA-Z]
DIGITO = [0-9]
ID = {LETRA}({LETRA}|{DIGITO})*
NUM = {DIGITO}+
CADENA = \"([^\"\n])*\"


%%

// Palabras reservadas
"INICIO"            { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.INICIO);}
"FIN"               { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.FIN);}
"CREAR_TAREA"       { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.CREAR_TAREA);}
"ELIMINAR_TAREA"    { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.ELIMINAR_TAREA);}
"COMPLETAR"         { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.COMPLETAR);}
"MOSTRAR"           { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.MOSTRAR);}
"MOSTRAR_TAREAS"    { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.MOSTRAR_TAREAS);}
"SI"                { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.SI);}
"ENTONCES"          { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.ENTONCES);}
"SINO"              { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.SINO);}
"MIENTRAS"          { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.MIENTRAS);}
"HACER"             { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.HACER);}
"VERIFICAR"         { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.VERIFICAR);}
"ESTADO"            { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.ESTADO);}
"PENDIENTE"         { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.PENDIENTE);}
"TERMINADA"         { tablaSimbolos.add(new Simbolo(yytext(), "PALABRA_RESERVADA", yyline, yycolumn)); return symbol(ParserSym.TERMINADA);}

//  Identificadores
{ID} {
    tablaSimbolos.add(new Simbolo(yytext(), "ID", yyline, yycolumn));
    return symbol(ParserSym.ID, yytext());
    }

//  Números
{NUM} {
    tablaSimbolos.add(new Simbolo(yytext(), "NUM", yyline, yycolumn));
    return symbol(ParserSym.NUM, yytext());
    }

//  Cadenas
{CADENA} {
    tablaSimbolos.add(new Simbolo(yytext(), "CADENA", yyline, yycolumn));
    return symbol(ParserSym.CADENA, yytext());
}

// Operadores
"="     { tablaSimbolos.add(new Simbolo(yytext(), "OPERADOR", yyline, yycolumn));return symbol(ParserSym.IGUAL);}
"=="    { tablaSimbolos.add(new Simbolo(yytext(), "OPERADOR", yyline, yycolumn));return symbol(ParserSym.IGUAL_IGUAL);}
"!="    { tablaSimbolos.add(new Simbolo(yytext(), "OPERADOR", yyline, yycolumn));return symbol(ParserSym.DIFERENTE);}
">"     { tablaSimbolos.add(new Simbolo(yytext(), "OPERADOR", yyline, yycolumn));return symbol(ParserSym.MAYOR);}
"<"     { tablaSimbolos.add(new Simbolo(yytext(), "OPERADOR", yyline, yycolumn));return symbol(ParserSym.MENOR);}

// Delimitadores
";"     {tablaSimbolos.add(new Simbolo(yytext(), "DELIMITADOR", yyline, yycolumn));return symbol(ParserSym.PUNTO_COMA); }
","     {tablaSimbolos.add(new Simbolo(yytext(), "DELIMITADOR", yyline, yycolumn));return symbol(ParserSym.COMA); }
"("     {tablaSimbolos.add(new Simbolo(yytext(), "DELIMITADOR", yyline, yycolumn));return symbol(ParserSym.PARENTESIS_A); }
")"     {tablaSimbolos.add(new Simbolo(yytext(), "DELIMITADOR", yyline, yycolumn));return symbol(ParserSym.PARENTESIS_C); }

// Espacios
[ \t\r\n\f]+   { /* ignorar */ }

// IDENTIFICADORES INVÁLIDOS (empiezan con número)
[0-9]+[a-zA-Z_]+ {
    agregarError("Identificador inválido", yytext());
}

// Error léxico
. {
     agregarError("Carácter no permitido", yytext());
}
