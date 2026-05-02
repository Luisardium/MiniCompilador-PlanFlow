package gt.edu.miumg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.StringReader;
import java_cup.runtime.Symbol;

public class Interfaz extends JFrame {

    private JTextArea inputArea;
    private JTable tabla;
    private DefaultTableModel modelo;

    public Interfaz() {
        setTitle("MiniCompilador - Tabla de Símbolos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inputArea = new JTextArea(5, 40);
        JButton btnAnalizar = new JButton("Analizar");

        modelo = new DefaultTableModel(new Object[]{"Lexema", "Tipo"}, 0);
        tabla = new JTable(modelo);

        btnAnalizar.addActionListener(e -> analizar());

        setLayout(new BorderLayout());
        add(new JScrollPane(inputArea), BorderLayout.NORTH);
        add(btnAnalizar, BorderLayout.CENTER);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);
    }

    private void analizar() {
        modelo.setRowCount(0); // limpiar tabla

        try {
            A_Lexico lexer = new A_Lexico(new StringReader(inputArea.getText()));
            Symbol token;

            while ((token = lexer.next_token()).sym != ParserSym.EOF) {

                String tipo = ParserSym.terminalNames[token.sym];
                String lexema = (token.value != null) ? token.value.toString() : tipo;

                modelo.addRow(new Object[]{lexema, tipo});
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interfaz().setVisible(true));
    }
}

