/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova6;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Launcher6 {

    public static void main(String [] args){

        System.out.println("Prova5: inizializzazione");
        StringWriter stringWriter= new StringWriter();
        DeadlineHandler handler = new DeadlineHandler();
        handler.setSchedulingParameters(new PriorityParameters(PriorityScheduler.instance().getMaxPriority()-1));
        handler.setStringWriter(stringWriter);
        WorkerThread6 thread1 = new WorkerThread6();
        thread1.setStringWriter(stringWriter);
        thread1.setShortExecutionTime(500);
        thread1.setLongExcecutionTime(2200);
        thread1.setIterations(3);
        thread1.setName("Thread1");
        thread1.setPriority((PriorityScheduler.instance().getMaxPriority())-10);
        PeriodicParameters sp = new PeriodicParameters(null, new RelativeTime(1000,0), null, null, null, handler);
        thread1.setReleaseParameters(sp);
        handler.setControlledThread(thread1);
        thread1.setZeroTime(Clock.getRealtimeClock().getTime());
        handler.setZeroTime(Clock.getRealtimeClock().getTime());
        System.out.println("Prova5: lancio");
        thread1.start();
        
        try {
            thread1.join();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher6.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Prova5: stampa risultati");
         System.out.println(stringWriter);
    }

}
