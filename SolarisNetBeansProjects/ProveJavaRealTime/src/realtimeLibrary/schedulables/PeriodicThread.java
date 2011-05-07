/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

import javax.realtime.AbsoluteTime;
import javax.realtime.AsyncEventHandler;
import javax.realtime.HighResolutionTime;
import javax.realtime.PeriodicParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import javax.realtime.ReleaseParameters;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.logging.SchedulableLog;

/**
 *
 * @author Marco Nanni
 * classe che modella un generico thread periodico,
 * giusto un abbozzo
 */
public class PeriodicThread extends RealtimeThread {

    private long excecutionTime;
    private SchedulableLog log;
    private int numberOfIterations;

    public PeriodicThread(String name,long excecutionTime, int numberOfIterations, int priority, RelativeTime period,RelativeTime startTime, RelativeTime deadline, AsyncEventHandler deadlineHandler) {

        super();
        this.excecutionTime = excecutionTime;
        this.numberOfIterations = numberOfIterations;
        super.setPriority(priority);
        this.setReleaseParameters(new PeriodicParameters(startTime, period, null, deadline, null, deadlineHandler) );
        log = new SchedulableLog();
        this.setName(name);
    }

    public PeriodicThread (){
        super();
        log = new SchedulableLog();

    }

    @Override
    public void run() {
       
        for (int i =0; i< this.getNumberOfIterations(); i++){
           this.getLog().writeStartJob();
            doJob();
            this.getLog().writeEndJob();
            PeriodicThread.waitForNextPeriod();
        }
    }

    protected void doJob() {
        BusyWait.getInstance().doJobFor(this.getExcecutionTime());
    }

    public void setDeadlineMissedHandler(AsyncEventHandler missHandler){
        PeriodicParameters currentParameters = (PeriodicParameters)this.getReleaseParameters();
        currentParameters.setDeadlineMissHandler(missHandler);
    }



    public long getExcecutionTime() {
        return excecutionTime;
    }

    public void setExcecutionTime(long excecutionTime) {
        this.excecutionTime = excecutionTime;
    }

    public SchedulableLog getLog() {
        return log;
    }

    public void setLog(SchedulableLog log) {
        this.log = log;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    

}
