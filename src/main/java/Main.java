import GraphicInterface.GUIFraudeurAPP;
import ConsoleInterface.FraudeApp;

public class Main {

    public static void main(String[] args) {
        FraudeApp fraudeApp = new ConsoleInterface.FraudeApp(".\\..\\files\\factures.csv");
        GUIFraudeurAPP guiFraudeurAPP = new GUIFraudeurAPP(fraudeApp);
    }

}
