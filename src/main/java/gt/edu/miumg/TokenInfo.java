package gt.edu.miumg;

public class TokenInfo {
    private String lexema;
    private String tipo;

    public TokenInfo(String lexema, String tipo) {
        this.lexema = lexema;
        this.tipo = tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipo() {
        return tipo;
    }
}

