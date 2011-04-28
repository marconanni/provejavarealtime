/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova08;



import javax.realtime.*;
import realtimeLibrary.logging.SchedulableLog;

/**
 *
 * @author root
 */

public class WorkerThread8 extends RealtimeThread {

    private boolean endJob = false;
    private SchedulableLog log = new SchedulableLog();

    public SchedulableLog getLog() {
        return log;
    }

    public void setLog(SchedulableLog log) {
        this.log = log;
    }

    public boolean isEndJob() {
        return endJob;
    }

    public void setEndJob(boolean endJob) {
        this.endJob = endJob;
    }

    @Override
    public void run() {
        this.doJob();
    }

    private void doJob() {
        int k=6;
        log.writeStartJob();
        System.out.println("Inizio del job");
        while (!endJob){

            k++;
            k--;

        }

        System.out.println("fine del job");

        log.writeEndJob();
    }



    




}
