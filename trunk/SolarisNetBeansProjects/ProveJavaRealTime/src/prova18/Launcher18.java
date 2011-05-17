/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova18;


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
public class Launcher18 extends RealtimeThread {



    public static void main (String [] args ){

        Launcher18 launcher = new Launcher18();
        launcher.setName("launcher");
        launcher.setPriority(PriorityScheduler.instance().getMaxPriority());
        launcher.start();


        
    }

    @Override
    public void run() {

        System.out.println("Laucher: confugurazione in corso");
        DummyPriorityScheduler  dummyPriorityScheduler = new DummyPriorityScheduler();
        Scheduler.setDefaultScheduler(dummyPriorityScheduler);


//        // creo il thread che deve occupare una cpu
//        CpuDespotTrhead despotTrhead = new CpuDespotTrhead();
//        despotTrhead.setName("despotThread");
//        despotTrhead.setPriority(PriorityScheduler.instance().getNormPriority()+4);

        
        

       

        BadThread th1 =  new BadThread();
        RelativeTime period = new RelativeTime(100, 0);
        th1.setReleaseParameters(new PeriodicParameters(period));
        th1.setName("Thread1");
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+1);

        th1.setExcecutionTime(20);
        th1.setBadExcecutionTime(20);
        th1.setBadIteration(1);
        th1.setNumberOfIterations(6);

       
       
//        SleepingHandler18 handler = new SleepingHandler18();
//        handler.setControlledThread(th1);
//
//
//        handler.setSleepingTime(50);
//        th1.setDeadlineMissedHandler(handler);





        



        System.out.println("Laucher: confugurazione BusyWait");
        BusyWait busyWait = BusyWait.getInstance();
        busyWait.initialize(1000, 3000);

       
        
        


        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
//        despotTrhead.start();
       
        th1.start();
        
       
        try {
            
            
            th1.join(6000);
            
            
//            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher18.class.getName()).log(Level.SEVERE, null, ex);
        }



        System.out.println("Laucher: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
//        logs.add(handler.getLog());
  
        
        
        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);



        




    }





}
