/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova10;

import javax.realtime.RealtimeThread;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.logging.SchedulableLog;


/**
 *
 * @author Marco Nanni
 */
public class WorkerThread10 extends RealtimeThread {

    private int iterations;
    private SchedulableLog log;
    private long workTime;

    @Override
    public void run() {
        
        BusyWait busyWait = BusyWait.getInstance();
        for (int k=0; k< this.getIterations();k++){
            log.writeStartJob();
            busyWait.doJobFor(this.getWorkTime());
            log.writeEndJob();
            waitForNextPeriod();
        }

    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public SchedulableLog getLog() {
        return log;
    }

    public void setLog(SchedulableLog log) {
        this.log = log;
    }

    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }





}
