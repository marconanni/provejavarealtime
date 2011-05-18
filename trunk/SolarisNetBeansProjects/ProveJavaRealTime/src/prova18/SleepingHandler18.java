/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova18;

import prova17.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.PeriodicParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.schedulables.DeadlineMissedHandler;

/**
 *
 * @author Marco Nanni: handler bizzarro che, prima di
 * rischedulare il thread si addormenta per un numero settabile
 * di millisecondi
 */
public class SleepingHandler18 extends DeadlineMissedHandler {

    private long sleepingTime =0;
    static boolean firstTime=true;
   

    public SleepingHandler18() {
        super();
    }

    public SleepingHandler18(RealtimeThread controlledThread, int priority, String name, long sleepingTime) {
        super(controlledThread, priority, name);
        this.sleepingTime=sleepingTime;

    }

    @Override
    public void handleAsyncEvent() {
        super.getLog().writeDeadlineMissed(this.getControlledThread().getName());
        

      
                 
            try {
               if (firstTime)
                Thread.sleep(sleepingTime);
               firstTime=false;
        } catch (InterruptedException ex) {
            Logger.getLogger(SleepingHandler18.class.getName()).log(Level.SEVERE, null, ex);
        }
               
            super.getLog().writeGenericMessage(" changsupering release parameters");
            super.getControlledThread().schedulePeriodic();

          
         
            
       

    }







    public long getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(long sleepingTime) {
        this.sleepingTime = sleepingTime;
    }



}
