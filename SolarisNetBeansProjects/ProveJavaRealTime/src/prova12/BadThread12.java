/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova12;
import javax.realtime.AsyncEventHandler;
import javax.realtime.RelativeTime;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.schedulables.*;

/**
 *
 * @author root
 */
public class BadThread12 extends PeriodicThread {

    private long badExcecutionTime;

    public BadThread12(String name, long excecutionTime, long badExcecutionTime , int numberOfIterations, int priority, RelativeTime period, RelativeTime startTime, RelativeTime deadline, AsyncEventHandler deadlineHandler) {
        super (name, excecutionTime, numberOfIterations, priority, period, startTime, deadline, deadlineHandler);
        this.badExcecutionTime = badExcecutionTime;
    }

    public BadThread12() {
        super ();
    }



@Override
    public void run() {
        BusyWait busyWait = BusyWait.getInstance();
        for (int k =0; k< this.getNumberOfIterations(); k++){
            this.getLog().writeStartJob();
            if (k==1)
                busyWait.doJobFor(this.getBadExcecutionTime());
            else
            busyWait.doJobFor(this.getExcecutionTime());
            this.getLog().writeEndJob();
            this.waitForNextPeriod();

        }
    }





    public long getBadExcecutionTime() {
        return badExcecutionTime;
    }

    public void setBadExcecutionTime(long badExcecutionTime) {
        this.badExcecutionTime = badExcecutionTime;
    }





}
