/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.busyWait;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.RealtimeThread;

/**
 *
 * @author Marco Nanni
 * Classe per l'esecuzione della funzionalità della calibrazione
 * di busy wait. Deve esere eseguita al massimo livello di priorità
 * affinchè non subisca interruzioni.
 */
public class InitializerMasterThread extends RealtimeThread {

    private long iterationCount=0;
    private long initializationTime=5000;
    private InitializerServerThread initializerServerThread;

    public InitializerMasterThread() {
        super();
    }
    public InitializerMasterThread(long initializationTime) {
        super();
        this.setInitializationTime(initializationTime);
    }

    @Override
    /**
     * il thread fa partire un initializerServerThread, si sospende
     * per initializationTime millisecondi. al suo risveglio interrompe l'attività
     * del thread server e guarda quanti cicli ha eseguito nel frattempo.
     */
    public void run() {
        initializerServerThread = new InitializerServerThread();
        initializerServerThread.setPriority(this.getPriority()-1);
        initializerServerThread.start();
        try {
            Thread.sleep(initializationTime); // vedi di usare un timer realtime anzichè il metodo sleep
            initializerServerThread.interrupt();
        } catch (InterruptedException ex) {
            Logger.getLogger(InitializerMasterThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        iterationCount= initializerServerThread.getIterationCount();
    }





    public long getInitializationTime() {
        return initializationTime;
    }

    public void setInitializationTime(long initializationTime) {
        this.initializationTime = initializationTime;
    }

    public long getIterationCount() {
        return iterationCount;
    }







}
