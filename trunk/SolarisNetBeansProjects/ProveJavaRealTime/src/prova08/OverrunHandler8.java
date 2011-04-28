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
public class OverrunHandler8 extends BoundAsyncEventHandler {

    private SchedulableLog log = new SchedulableLog();
    private WorkerThread8 thread;

    public SchedulableLog getLog() {
        return log;
    }

    public void setLog(SchedulableLog log) {
        this.log = log;
    }

    public WorkerThread8 getThread() {
        return thread;
    }

    public void setThread(WorkerThread8 thread) {
        this.thread = thread;
    }

    @Override
    public void handleAsyncEvent() {
        log.writeStartJob();
        thread.setEndJob(true);
        ReleaseParameters rp = thread.getReleaseParameters();

        System.out.println("Costo del thread:"+ rp.getCost() );
        rp.setCost(new RelativeTime(Long.MAX_VALUE, 0));
        System.out.println("overrun handler: entrato in azione");
        log.writeEndJob();
    }



    

}
