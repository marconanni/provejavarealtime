/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova07;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Launcher7  extends RealtimeThread{

    @Override
    public  void run (){
        System.out.println("Prova 7: inizializzazione");
        StringWriter stringWriter = new StringWriter();
        Resource resource = new Resource();
        resource.setStringWriter(stringWriter);
        ResourceThread thread1 = new ResourceThread();
        thread1.setExecutionTime(2000);
        thread1.setResource(resource);
        thread1.setStringWriter(stringWriter);
        thread1.setName("Thread1");
        thread1.setPriority(PriorityScheduler.instance().getMaxPriority() -5);
        thread1.setReleaseParameters(new PeriodicParameters(new RelativeTime(1000,0), new RelativeTime(20000,0)));


        WorkerThread7 thread21 = new WorkerThread7();
        thread21.setExecutionTime(5000);
        thread21.setStringWriter(stringWriter);
        thread21.setName("Thread21");
        thread21.setPriority(PriorityScheduler.instance().getMaxPriority() -6);
        thread21.setReleaseParameters(new PeriodicParameters(new RelativeTime(1000,0), new RelativeTime(20000,0)));

        WorkerThread7 thread22 = new WorkerThread7();
        thread22.setExecutionTime(5000);
        thread22.setStringWriter(stringWriter);
        thread22.setName("Thread22");
        thread22.setPriority(PriorityScheduler.instance().getMaxPriority() -6);
        thread22.setReleaseParameters(new PeriodicParameters(new RelativeTime(1000,0), new RelativeTime(20000,0)));


        
        
        ResourceThread thread3 = new ResourceThread();
        thread3.setExecutionTime(2000);
        thread3.setResource(resource);
        thread3.setStringWriter(stringWriter);
        thread3.setName("Thread3");
        thread3.setPriority(PriorityScheduler.instance().getMaxPriority() -7);
        thread3.setReleaseParameters(new PeriodicParameters(new RelativeTime(0,0), new RelativeTime(20000,0)));

        thread1.setZeroTime(Clock.getRealtimeClock().getTime());
        thread21.setZeroTime(Clock.getRealtimeClock().getTime());
        thread22.setZeroTime(Clock.getRealtimeClock().getTime());
        thread3.setZeroTime(Clock.getRealtimeClock().getTime());
        System.out.println("Prova7: 20 secondi all'avvio");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher7.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        System.out.println("Prova7:avvio");
        thread1.start();
        thread21.start();
//        thread22.start();
        thread3.start();
        try {
            thread1.join();
            thread21.join();
//            thread22.join();
            thread3.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher7.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Prova7: stampa risultati");
        System.out.println(stringWriter);

        
        

    }

    public static void main (String [] args){
        Launcher7 me = new Launcher7();
        me.setPriority(PriorityScheduler.instance().getMaxPriority());
        me.start();

    }

}
