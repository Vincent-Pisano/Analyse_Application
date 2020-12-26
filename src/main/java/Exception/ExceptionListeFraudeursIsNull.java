package Exception;

public class ExceptionListeFraudeursIsNull extends Exception {

    public ExceptionListeFraudeursIsNull() {
        super("Aucun fraudeurs dans la base de données");
    }
}
