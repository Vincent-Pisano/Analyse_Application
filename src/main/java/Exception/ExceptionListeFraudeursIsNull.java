package Exception;

public class ExceptionListeFraudeursIsNull extends Exception {

    public ExceptionListeFraudeursIsNull() {
        super("Aucun fraudeur dans la base de donnees");
    }
}
