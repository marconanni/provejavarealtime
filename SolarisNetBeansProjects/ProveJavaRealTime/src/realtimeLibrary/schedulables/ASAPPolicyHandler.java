/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;
import javax.realtime.AsyncEventHandler;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;
import realtimeLibrary.logging.SchedulableLog;


/**
 *
 * @author Marco Nanni: template per l'handler di deadline
 * relativo ad un thread periodico
 * nn fa niente solo scrive l'evento sul log e rischedula il thread controllato
 */
public class ASAPPolicyHandler extends DeadlineMissedHandler   {

    private int pendingReleases;


   

    public ASAPPolicyHandler(PeriodicThread controlledThread, int priority, String name) {
      super(controlledThread, priority, name);
    }

    public ASAPPolicyHandler() {
        super();
       
    }



    @Override
    /**
     * questa gestione non fa altro che scrivere l'evento sul log
     * e rischedulare il thread che ha sforato la deadline,
     */
    public void handleAsyncEvent() {
        Thread.currentThread().setName(this.getName());
        this.getLog().writeDeadlineMissed(this.getControlledThread().getName());
        this.getControlledThread().setPendingMode(true);
        this.incrementPendingReleases();
        this.getControlledThread().schedulePeriodic();

    }

    @Override
    public void doPendingJob(PeriodicThread managedThread) {
       // non c'è bisogno di indicare il nome del thread perchè il thread che sta eseguendo questo codice è il thread controllato
        this.getLog().writeStartPendingJob();
        this.getControlledThread().doJob();
        this.getLog().writeEndPendingJob();
        this.decrementPendingReleases();
        if(this.getPendingReleases()==0)
            this.getControlledThread().setPendingMode(false);
    }

    /**
     *
     * @return il numero di release pendenti che
     * bisogna recuperare
     */
    public int getPendingReleases() {
        return pendingReleases;
    }

    /**
     *
     * @param pendingReleases il numero di release pendenti che
     * bisogna recuperare
     */
    public void setPendingReleases(int pendingReleases) {
        this.pendingReleases = pendingReleases;
    }

    /**
     * incrementa il numero di release pendenti che
     * bisogna recuperare
     */
    public void incrementPendingReleases(){
        this.pendingReleases++;
    }

    /**
     * decrementa il numero di release pendenti che
     * bisogna recuperare
     */
    public void decrementPendingReleases(){
        this.pendingReleases--;
    }














}
