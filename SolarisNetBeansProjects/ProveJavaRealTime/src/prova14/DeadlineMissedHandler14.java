/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova14;

import javax.realtime.PeriodicParameters;
import javax.realtime.RelativeTime;
import realtimeLibrary.schedulables.DeadlineMissedHandler;
import realtimeLibrary.schedulables.PeriodicThread;

/**
 *
 * @author Marco Nanni
 *
 * Handler per l'evento di deadline missed che gioca
 * con il fire count. Nell'sesempio 14 l'handler
 * viene chiamato due volte, visto che il thread1
 * sfora due deadline. Solo la seconda gioco con il
 * fire count
 */
public class DeadlineMissedHandler14  extends DeadlineMissedHandler{

    private int missCount =0;

    DeadlineMissedHandler14(PeriodicThread controlledThread, int priority, String name) {
        super(controlledThread,priority,name);
    }

    @Override
    public void handleAsyncEvent() {
        
        this.setMissCount((this.getMissCount())+1);
        // visto che nell'esempio sforo la deadline due volte,
        // qui metto i miei giochini
        
         super.handleAsyncEvent();


        if(this.getMissCount()==2){
            this.getControlledThread().deschedulePeriodic();
            this.getControlledThread().setReleaseParameters(new PeriodicParameters(new RelativeTime(900, 0), new RelativeTime(1000, 0)));
            this.getControlledThread().schedulePeriodic();
        }

            
  
        }

    

    public int getMissCount() {
        return missCount;
    }

    protected void setMissCount(int missCount) {
        this.missCount = missCount;
    }





}
