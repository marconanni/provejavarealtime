/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova11;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;
import realtimeLibrary.logging.*;
import realtimeLibrary.schedulables.*;
import realtimeLibrary.busyWait.BusyWait;

/**
 *
 * @author root
 */
public class Launcher11 extends RealtimeThread {


    public static void main (String [] args ){

        Launcher11 launcher = new Launcher11();
        launcher.setName("launcher");
        launcher.setPriority(PriorityScheduler.instance().getMaxPriority());
        launcher.start();


        
    }

    @Override
    public void run() {

        System.out.println("Launcher 11: confugurazione in corso");

        // creo il thread che deve occupare una cpu
        CpuDespotThread despotTrhead = new CpuDespotThread();
        despotTrhead.setName("despotThread");
        despotTrhead.setPriority(PriorityScheduler.instance().getNormPriority()+4);

        // creo il thread 1 che ha un tempo di esecuzione doppio al secondo ciclo
        BadThread11 th1 = new BadThread11();
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+2);
        th1.setName("Thread1");
        th1.setNumberOfIterations(2);
        th1.setBadExcecutionTime(900);
        th1.setExcecutionTime(400);
        th1.setReleaseParameters(new PeriodicParameters(new RelativeTime(1000, 0)));

        

        //  creo il thread 2: un normale thread che ha come tempo di esecuzione 4000  e lo stesso periodo di th1
        PeriodicThread th2 = new PeriodicThread();
        th2.setName("Thread2");
        th2.setNumberOfIterations(7);
        th2.setExcecutionTime(200);
       
        // imposto i parametri di release del secondo thread
        th2.setReleaseParameters(new PeriodicParameters(new RelativeTime(1000,0)));
        
        System.out.println("Launcher 11: confugurazione BusyWait");
        BusyWait busyWait = BusyWait.getInstance();
        busyWait.initialize(1000, 3000);

        System.out.println("Launcher 11: Lancio tra 10 secondi!");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher11.class.getName()).log(Level.SEVERE, null, ex);
        }
        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
        despotTrhead.start();
        th1.start();
        th2.start();
        try {
            th1.join(6000);
            th2.join(6000);
            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher11.class.getName()).log(Level.SEVERE, null, ex);
        }



        System.out.println("Launcher 11: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
        logs.add(th2.getLog());
       

        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);




    }





}
