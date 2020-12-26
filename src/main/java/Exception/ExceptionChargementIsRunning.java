package Exception;

public class ExceptionChargementIsRunning extends Exception{

    public ExceptionChargementIsRunning() {
        super("Patientez pendant la fin du chargement");
    }
}
