/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;
import javax.realtime.AsyncEventHandler;
import javax.realtime.RelativeTime;
import realtimeLibrary.busyWait.BusyWait;

/**
 *
 * @author Marco Nanni
 * Classe che simula il comportamentdo di un thread che ha un comporamento anomalo
 * in questa implementazione esegue un'esecuzione di durata parima badExcecutionTime
 * millisecondi nel ciclo badIteration.
 * Per ottenere un comportamento diverso sovrascrivere il metodo doJob
 */
public class BadThread extends PeriodicThread {

    private long badExcecutionTime; // è il tempo di esecuzione nel job che ha un'esecuzione più lunga del normale
    private int badIteration; // indica in quale job fare l'esecuzione lunga

    public BadThread(String name, long excecutionTime, long badExcecutionTime , int badIteration, int numberOfIterations, int priority, RelativeTime period, RelativeTime startTime, RelativeTime deadline, AsyncEventHandler deadlineHandler) {
        super (name, excecutionTime, numberOfIterations, priority, period, startTime, deadline, deadlineHandler);
        this.badExcecutionTime = badExcecutionTime;
        this.badIteration = badIteration;
    }

    public BadThread() {
        super ();
    }

    @Override
    protected void doJob() {
        if(this.getBadIteration()== super.getCurrentIteration())
            BusyWait.getInstance().doJobFor(this.getBadExcecutionTime());
        else
            BusyWait.getInstance().doJobFor(super.getExcecutionTime());

    }



   






    public long getBadExcecutionTime() {
        return badExcecutionTime;
    }

    public void setBadExcecutionTime(long badExcecutionTime) {
        this.badExcecutionTime = badExcecutionTime;
    }

    public int getBadIteration() {
        return badIteration;
    }

    public void setBadIteration(int badIteration) {
        this.badIteration = badIteration;
    }

    





}
