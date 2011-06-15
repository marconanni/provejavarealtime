/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova17;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.RealtimeThread;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.schedulables.ASAPPolicyHandler;
import realtimeLibrary.schedulables.PeriodicThread;

/**
 *
 * @author Marco Nanni: handler bizzarro che, prima di
 * rischedulare il thread si addormenta per un numero settabile
 * di millisecondi
 */
public class SleepingHandler extends ASAPPolicyHandler {

    private long sleepingTime =0;
    static boolean firstTime=true;
   

    public SleepingHandler() {
        super();
    }

    public SleepingHandler(PeriodicThread controlledThread, int priority, String name, long sleepingTime) {
        super(controlledThread, priority, name);
        this.sleepingTime=sleepingTime;

    }

    @Override
    public void handleAsyncEvent() {
        super.getLog().writeDeadlineMissed(this.getControlledThread().getName());
        try {


            Thread.sleep(sleepingTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(SleepingHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            
              super.getLog().writeGenericMessage("SchedulePeriodic");
              super.getControlledThread().schedulePeriodic();
        
            
       

    }







    public long getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(long sleepingTime) {
        this.sleepingTime = sleepingTime;
    }



}
