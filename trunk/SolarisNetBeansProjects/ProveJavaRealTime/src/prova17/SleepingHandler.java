/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova17;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.RealtimeThread;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.schedulables.DeadlineMissedHandler;

/**
 *
 * @author Marco Nanni: handler bizzarro che, prima di
 * rischedulare il thread si addormenta per un numero settabile
 * di millisecondi
 */
public class SleepingHandler extends DeadlineMissedHandler {

    private long sleepingTime =0;
   

    public SleepingHandler() {
        super();
    }

    public SleepingHandler(RealtimeThread controlledThread, int priority, String name, long sleepingTime) {
        super(controlledThread, priority, name);
        this.sleepingTime=sleepingTime;

    }

    @Override
    public void handleAsyncEvent() {
        super.getLog().writeDeadlineMissed(this.getControlledThread().getName());

      
            super.getLog().writeGenericMessage("handler chiamato da "+Thread.currentThread().getName()+" id "+Thread.currentThread().getId());
            try {
            Thread.sleep(sleepingTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(SleepingHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
               
            super.getLog().writeGenericMessage("schedule periodic");
            super.getControlledThread().schedulePeriodic();
       

    }







    public long getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(long sleepingTime) {
        this.sleepingTime = sleepingTime;
    }



}
