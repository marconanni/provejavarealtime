/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova13;
import javax.realtime.AsyncEventHandler;
import javax.realtime.RelativeTime;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.schedulables.*;

/**
 *
 * @author Marco Nanni
 */
public class BadThread13 extends PeriodicThread {

    private long badExcecutionTime;

    public BadThread13(String name, long excecutionTime, long badExcecutionTime , int numberOfIterations, int priority, RelativeTime period, RelativeTime startTime, RelativeTime deadline, AsyncEventHandler deadlineHandler) {
        super (name, excecutionTime, numberOfIterations, priority, period, startTime, deadline, deadlineHandler);
        this.badExcecutionTime = badExcecutionTime;
    }

    public BadThread13() {
        super ();
    }



@Override
    public void run() {
        BusyWait busyWait = BusyWait.getInstance();
        for (int k =0; k< this.getNumberOfIterations(); k++){
            this.getLog().writeStartJob();
            if (k==0)
                busyWait.doJobFor(this.getBadExcecutionTime());
            else
            busyWait.doJobFor(this.getExcecutionTime());
            this.getLog().writeEndJob();
            BadThread13.waitForNextPeriod();

        }
    }





    public long getBadExcecutionTime() {
        return badExcecutionTime;
    }

    public void setBadExcecutionTime(long badExcecutionTime) {
        this.badExcecutionTime = badExcecutionTime;
    }





}
