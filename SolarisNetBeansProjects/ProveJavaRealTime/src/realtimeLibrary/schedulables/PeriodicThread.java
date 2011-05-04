/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

import javax.realtime.AbsoluteTime;
import javax.realtime.AsyncEventHandler;
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
        BusyWait busyWait = BusyWait.getInstance();
        for (int k =0; k< this.getNumberOfIterations(); k++){
           this.getLog().writeStartJob();
            busyWait.doJobFor(excecutionTime);
            this.getLog().writeEndJob();
            this.waitForNextPeriod();
        }
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
