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
 * @author Marco: classe che modella il generico gestore di deadline missed
 */
public class DeadlineMissedHandler extends AsyncEventHandler implements IPendingJobManager {
    private PeriodicThread controlledThread;
    private SchedulableLog log;
    private String name;

    public DeadlineMissedHandler(PeriodicThread controlledThread, int priority, String name) {
        this.controlledThread = controlledThread;
        this.controlledThread.setPendingJobManager(this);
        this.setPriority(priority);
        this.name = name;
        this.log= new SchedulableLog();
    }



    public DeadlineMissedHandler() {
        super();
        this.log= new SchedulableLog();
    }

    public void doPendingJob(PeriodicThread managedThread) {
        this.getControlledThread().doJob();
    }



    public PeriodicThread getControlledThread() {
        return controlledThread;
    }

    public SchedulableLog getLog() {
        return log;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return ((PriorityParameters) this.getSchedulingParameters()).getPriority();
    }

    public void setControlledThread(PeriodicThread controlledThread) {
        this.controlledThread = controlledThread;
        this.getControlledThread().setPendingJobManager(this);
    }

    public void setLog(SchedulableLog log) {
        this.log = log;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.setSchedulingParameters(new PriorityParameters(priority));
    }

}
