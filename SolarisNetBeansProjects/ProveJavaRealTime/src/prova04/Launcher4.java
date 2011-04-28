/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova04;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Launcher4 {

    public static void main(String [] args){
        WorkerThread4 thread1 = new WorkerThread4();
        thread1.setExecutionTime(500);
        thread1.setName("Thread1");
        thread1.setPriority((PriorityScheduler.instance().getMaxPriority())-10);
        PeriodicParameters sp = new PeriodicParameters(new RelativeTime(1000,0));
        thread1.setReleaseParameters(sp);
        thread1.setZeroTime(Clock.getRealtimeClock().getTime());
        thread1.start();
    }

}
