/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova17;


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
public class Launcher17 extends RealtimeThread {



    public static void main (String [] args ){

        Launcher17 launcher = new Launcher17();
        launcher.setName("launcher");
        launcher.setPriority(PriorityScheduler.instance().getMaxPriority());
        launcher.start();


        
    }

    @Override
    public void run() {

        System.out.println("Laucher: confugurazione in corso");

//         creo il thread che deve occupare una cpu
        CpuDespotTrhead despotTrhead = new CpuDespotTrhead();
        despotTrhead.setName("despotThread");
        despotTrhead.setPriority(PriorityScheduler.instance().getNormPriority()+4);

        
        

        BadThread th1 =  new BadThread();
        RelativeTime period = new RelativeTime(100, 0);
        th1.setReleaseParameters(new PeriodicParameters(period));
        th1.setName("Thread1");
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+2);
        th1.setExcecutionTime(10);
        th1.setBadExcecutionTime(250);
        th1.setBadIteration(1);
        th1.setNumberOfIterations(6);


        SleepingHandler handler = new SleepingHandler(th1, PriorityScheduler.instance().getNormPriority()+4, "sleepingHandler",0);
        handler.setSleepingTime(0);

        
        th1.setDeadlineMissedHandler(handler);
        /*
         * creo un altro thread con un'altro sleeping handler, voglio vedere se
         * il thread che chiama gli handler è lo stesso o se ce ne è uno associato
         * ad ogni thread real time.
         */

        



        System.out.println("Laucher: confugurazione BusyWait");
        BusyWait busyWait = BusyWait.getInstance();
        busyWait.initialize(1000, 3000);

       
        
        

//        System.out.println("Laucher: Lancio tra 10 secondi!");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Launcher17.class.getName()).log(Level.SEVERE, null, ex);
//        }
        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
//        despotTrhead.start();
       
        th1.start();
       
        try {
            
            
            th1.join(6000);
            
//            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher17.class.getName()).log(Level.SEVERE, null, ex);
        }



        System.out.println("Laucher: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
        logs.add(handler.getLog());
        
        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);



        




    }





}
