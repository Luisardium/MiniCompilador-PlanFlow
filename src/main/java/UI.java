import gt.edu.miumg.A_Lexico;
import gt.edu.miumg.Parser;
import gt.edu.miumg.ParserSym;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class    UI {
    private JPanel panel1;
    private JButton btnAnalizar;
    private JTextArea txtIngresarTexto;
    private JTextArea txtTablaErrores;
    private JTextArea txtAnalisisSemantico;
    private JButton btnLimpiar;
    private JTextArea txtTokens;

    public UI() {

        btnAnalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                txtTokens.setText("");
                txtTablaErrores.setText("");
                txtAnalisisSemantico.setText("");

                try {
                    String texto = txtIngresarTexto.getText();

                    A_Lexico.errores.clear();

                    A_Lexico lexer = new A_Lexico(new java.io.StringReader(texto));
                    java_cup.runtime.Symbol token;

                    while (true) {
                        token = lexer.next_token();

                        if (token.sym == ParserSym.EOF) {
                            break;
                        }

                        String tipo = obtenerTipoToken(token.sym);

                        String lexema;
                        if (token.value != null) {
                            lexema = token.value.toString();
                        } else {
                            lexema = ParserSym.terminalNames[token.sym];
                        }

                        txtTokens.append(lexema + " --> " + tipo + "\n");
                    }

                    // ENCABEZADO TABLA ERRORES
                    if (!A_Lexico.errores.isEmpty()) {

                        txtTablaErrores.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

                        txtTablaErrores.append(
                                String.format("%-30s %-8s %-8s %-20s%n",
                                        "DESCRIPCIÓN", "LÍNEA", "COLUMNA", "CAUSA")
                        );

                        txtTablaErrores.append(
                                "--------------------------------------------------------------\n"
                        );

                        for (String error : A_Lexico.errores) {
                            txtTablaErrores.append(error + "\n");
                        }
                    }

                    // 3. ANÁLISIS SINTÁCTICO (CUP)

                    Parser.erroresSintacticos.clear();

                    // NUEVO LEXER
                    A_Lexico lexer2 = new A_Lexico(new java.io.StringReader(texto));

                    Parser parser = new Parser(lexer2);
                    parser.parse();

                    //  MOSTRAR ERRORES SINTÁCTICOS

                    if (!Parser.erroresSintacticos.isEmpty()) {

                        txtAnalisisSemantico.append(
                                String.format("%-35s %-10s %-10s %-20s%n",
                                        "DESCRIPCIÓN", "LÍNEA", "COLUMNA", "TOKEN")
                        );

                        txtAnalisisSemantico.append(
                                "--------------------------------------------------------------------------\n"
                        );

                        for (String error : Parser.erroresSintacticos) {
                            txtAnalisisSemantico.append(error + "\n");
                        }

                    } else {
                        txtAnalisisSemantico.append("Análisis sintáctico correcto");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al analizar: " + ex.getMessage());
                }
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTokens.setText("");
                txtTablaErrores.setText("");
                txtAnalisisSemantico.setText("");
                txtIngresarTexto.setText("");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PlanFlow");
        frame.setContentPane(new UI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    private String obtenerTipoToken(int token) {

        switch (token) {

            // Palabras reservadas
            case ParserSym.INICIO:
            case ParserSym.FIN:
            case ParserSym.CREAR_TAREA:
            case ParserSym.ELIMINAR_TAREA:
            case ParserSym.COMPLETAR:
            case ParserSym.MOSTRAR:
            case ParserSym.MOSTRAR_TAREAS:
            case ParserSym.SI:
            case ParserSym.ENTONCES:
            case ParserSym.SINO:
            case ParserSym.MIENTRAS:
            case ParserSym.HACER:
            case ParserSym.VERIFICAR:
            case ParserSym.ESTADO:
            case ParserSym.PENDIENTE:
            case ParserSym.TERMINADA:
                return "Palabra reservada";

            // Identificadores
            case ParserSym.ID:
                return "Identificador";

            // Números
            case ParserSym.NUM:
                return "Número";

            // Cadenas
            case ParserSym.CADENA:
                return "Cadena";

            // Operadores
            case ParserSym.IGUAL:
            case ParserSym.IGUAL_IGUAL:
            case ParserSym.DIFERENTE:
            case ParserSym.MAYOR:
            case ParserSym.MENOR:
                return "Operador";

            // Símbolos
            case ParserSym.PUNTO_COMA:
            case ParserSym.COMA:
            case ParserSym.PARENTESIS_A:
            case ParserSym.PARENTESIS_C:
                return "Símbolo";

            default:
                return "Desconocido";
        }
    }

}



