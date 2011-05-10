/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;
import prova14.*;
import javax.realtime.AsyncEventHandler;
import javax.realtime.RelativeTime;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.schedulables.*;

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
    /**
     * il corpo del metodo è simile a quello della superclasse @link PeriodicThread
     * solo che nel ciclo badIteration fa una busy wait pari a badExcecutionTime
     */
    public void run() {

    if (this.getSkipNumber()==0){

        BusyWait busyWait = BusyWait.getInstance();

            for (int i =0; i< this.getNumberOfIterations(); i++){
               this.getLog().writeStartJob();

               if (i==(this.getBadIteration()-1))// il contatore del for parte da 0
                    busyWait.doJobFor(this.getBadExcecutionTime());
                else
                     busyWait.doJobFor(this.getExcecutionTime());


                this.getLog().writeEndJob();
                PeriodicThread.waitForNextPeriod();
             }

        }
         else{
            this.decrementSkipNumber();

         }




        BusyWait busyWait = BusyWait.getInstance();
        for (int k =0; k< this.getNumberOfIterations(); k++){
            this.getLog().writeStartJob();
            if (k==0)
                busyWait.doJobFor(this.getBadExcecutionTime());
            else
            busyWait.doJobFor(this.getExcecutionTime());
            this.getLog().writeEndJob();
            BadThread.waitForNextPeriod();

        }
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