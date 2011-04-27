/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova3;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Launcher3  extends RealtimeThread{

    @Override
    public  void run (){
        // richiamo scheduler
        PriorityScheduler ps = PriorityScheduler.instance();
        // Creazione Thread 1
        System.out.println("Creazione Thread 1");
        WorkerThread3 th1= new WorkerThread3();
        th1.setExecutionTime(2000);
        th1.setName("Thread 1");
        th1.setPriority(ps.getMaxPriority()-10);
        // Creazione Thread2 
        System.out.println("Creazione Thread 2");
        WorkerThread3 th2= new WorkerThread3();
        th2.setExecutionTime(2000);
        th2.setName("Thread 2");
        th2.setPriority(ps.getMaxPriority()-15);
        // imposto lo zero time e lo do in pasto allo scheduler
        StringWriter sw = new StringWriter(5000);
        th1.setStringWriter(sw);
        th2.setStringWriter(sw);

        System.out.println("20 secondi alla partenza");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("imposto lo zero time");

        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();

        th1.setZeroTime(zeroTime);
        th2.setZeroTime(zeroTime);

        

        // dò i due thread in pasto allo scheduler
        System.out.println("dò i due thread in pasto allo scheduler");
        

        th2.start();
        th1.start();
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher3.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Stampa Risultati");
        System.out.println(sw);
        

    }

    public static void main (String [] args){
        Launcher3 me = new Launcher3();
        me.setPriority(PriorityScheduler.instance().getMaxPriority());
        me.start();

    }

}
