/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova09;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Launcher9 {

    public static void main(String [] args){

        System.out.println("Prova9: inizializzazione");
        StringWriter stringWriter= new StringWriter();
        DeadlineHandler9 handler = new DeadlineHandler9();
        handler.setSchedulingParameters(new PriorityParameters(PriorityScheduler.instance().getMaxPriority()-1));
        handler.setStringWriter(stringWriter);
        WorkerThread9 thread1 = new WorkerThread9();
        thread1.setStringWriter(stringWriter);
        thread1.setIterations(1);
        thread1.setName("Thread1");
        thread1.setPriority((PriorityScheduler.instance().getMaxPriority())-10);
        PeriodicParameters sp = new PeriodicParameters(null, new RelativeTime(3000,0), new RelativeTime(2000,0), null, handler, null);
        thread1.setReleaseParameters(sp);
        handler.setControlledThread(thread1);
        thread1.setZeroTime(Clock.getRealtimeClock().getTime());
        handler.setZeroTime(Clock.getRealtimeClock().getTime());
        System.out.println("Prova9: lancio");
        thread1.start();
        
        try {
            thread1.join(10000);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher9.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Prova9: stampa risultati");
         System.out.println(stringWriter);
    }

}
