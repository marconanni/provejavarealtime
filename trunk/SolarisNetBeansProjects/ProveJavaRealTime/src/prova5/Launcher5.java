/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova5;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Launcher5 {

    public static void main(String [] args){

        System.out.println("Prova5: inizializzazione");
        StringWriter stringWriter= new StringWriter();
        DeadlineHandler handler = new DeadlineHandler();
        handler.setSchedulingParameters(new PriorityParameters(PriorityScheduler.instance().getMaxPriority()-1));
        handler.setStringWriter(stringWriter);
        WorkerThread5 thread1 = new WorkerThread5();
        thread1.setStringWriter(stringWriter);
        thread1.setShortExecutionTime(500);
        thread1.setLongExcecutionTime(2000);
        thread1.setName("Thread1");
        thread1.setPriority((PriorityScheduler.instance().getMaxPriority())-10);
        PeriodicParameters sp = new PeriodicParameters(null, new RelativeTime(1000,0), null, new RelativeTime(700,0), null, handler);
        sp.setDeadlineMissHandler(handler);
        thread1.setReleaseParameters(sp);
        handler.setControlledThread(thread1);
        thread1.setZeroTime(Clock.getRealtimeClock().getTime());
        handler.setZeroTime(Clock.getRealtimeClock().getTime());
        System.out.println("Prova5: lancio");
        thread1.start();
        
        try {
            thread1.join();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher5.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Prova5: stampa risultati");
         System.out.println(stringWriter);
    }

}
