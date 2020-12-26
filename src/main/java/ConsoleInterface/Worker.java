package ConsoleInterface;

import java.util.List;
import java.util.Map;

public class Worker implements Runnable {
    protected Thread thread;
    protected boolean running;
    protected Map<FactureClee, List<String>> syncTreeMapFactures;

    public Worker(Map<FactureClee, List<String>> syncTreeMapFactures) {
        this.syncTreeMapFactures = syncTreeMapFactures;
    }

    void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        running = false;
    }

    void attendre() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void interrompre()
    {
        running = false;
        thread.interrupt();
    }

    public boolean getEtat()
    {
        synchronized (syncTreeMapFactures)
        {
            return running;
        }
    }

    @Override
    public void run() { }
}