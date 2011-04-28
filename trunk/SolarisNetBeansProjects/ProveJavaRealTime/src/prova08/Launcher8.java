/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova08;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;
import realtimeLibrary.logging.*;

/**
 *
 * @author root
 */
public class Launcher8   extends RealtimeThread{

    public static void main (String[] args){
        Launcher8 launcher = new Launcher8();
        launcher.setName("Launcher");
        launcher.setPriority(PriorityScheduler.instance().getMaxPriority());
        launcher.start();
    }

    @Override
    public void run() {
        System.out.println("Launcher8 : configurazione");
        WorkerThread8  th1 = new WorkerThread8();
        OverrunHandler8 overrunHandler = new OverrunHandler8();
        overrunHandler.setSchedulingParameters(new PriorityParameters(PriorityScheduler.instance().getMaxPriority()-1));
        th1.setPriority(PriorityScheduler.instance().getNormPriority());
        th1.setReleaseParameters(new AperiodicParameters(new RelativeTime(2000, 0),new RelativeTime(3000, 0),overrunHandler,overrunHandler));
        
        overrunHandler.setThread(th1);
        th1.setName("thread1");
        
        System.out.println("Launcher8 : 20 secondi alla partenza");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher8.class.getName()).log(Level.SEVERE, null, ex);
        }
        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
        th1.start();
        try {
            th1.join(30000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher8.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Launcher8 : raccolta risultati");
        Vector<Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
        logs.add(overrunHandler.getLog());
        System.out.println(Util.relativeMerge(logs, zeroTime));


    }





}
