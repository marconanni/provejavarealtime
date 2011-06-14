/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.scheduler;

import javax.realtime.AbsoluteTime;
import javax.realtime.PriorityParameters;

/**
 *
 * @author Marco Nanni
 *classe che modella i parametri di schedeuling necessari per la strategia EDF
 * ha un AbsoluteTime che indica la prossima deadline del processo
 *
 */
public class EDFSchedulingParameters extends PriorityParameters{
    
    private AbsoluteTime nextDeadline; // il tempo assoluto della prossima deadline

    public EDFSchedulingParameters() {
        super (EDFScheduler.getReleasePriority());

    }

    public AbsoluteTime getNextDeadline() {
        return nextDeadline;
    }

    public void setNextDeadline(AbsoluteTime nextDeadline) {
        this.nextDeadline = nextDeadline;
    }

    



}
