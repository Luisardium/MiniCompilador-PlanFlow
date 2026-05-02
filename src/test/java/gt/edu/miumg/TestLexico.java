package gt.edu.miumg;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestLexico {

    @Test
    void testProgramaValido() {
        String input = " INICIO CREAR_TAREA tarea1, \" Mi primera tarea \"; MOSTRAR_TAREAS; FIN ";

        assertDoesNotThrow(() -> {
            A_Lexico lexer = new A_Lexico(new StringReader(input));
            Parser parser = new Parser(lexer);
            parser.parse();
        });
    }
}

