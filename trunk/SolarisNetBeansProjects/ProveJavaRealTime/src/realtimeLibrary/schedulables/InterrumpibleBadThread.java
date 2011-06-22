/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.AsyncEventHandler;
import javax.realtime.AsynchronouslyInterruptedException;
import javax.realtime.RelativeTime;
import realtimeLibrary.busyWait.BusyWait;

/**
 *
 * @author root
 */
public class InterrumpibleBadThread extends InterrumpiblePeriodicThread{

    private long badExcecutionTime; // è il tempo di esecuzione nel job che ha un'esecuzione più lunga del normale
    private int badIteration; // indica in quale job fare l'esecuzione lunga

   

    public InterrumpibleBadThread() {
        super ();
    }
    
    @Override
    protected void doJob() {
       
       
            try {
                 if(this.getBadIteration()== super.getCurrentIteration())
                     BusyWait.getInstance().doInterrumpibleJobFor(this.getBadExcecutionTime());
                  else
                        BusyWait.getInstance().doInterrumpibleJobFor(this.getExcecutionTime());

            } catch (AsynchronouslyInterruptedException ex) {
            ex.clear();
            this. getLog().writeInterruptedJob(this.getName());
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
