/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova22;


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
public class Launcher22 extends RealtimeThread {



    public static void main (String [] args ){

        Launcher22 launcher = new Launcher22();
        launcher.setName("launcher");
        launcher.setPriority(PriorityScheduler.instance().getMaxPriority());
        launcher.start();


        
    }

    @Override
    public void run() {

        System.out.println("Laucher: confugurazione in corso");

        // creo il thread che deve occupare una cpu
        CpuDespotThread despotTrhead = new CpuDespotThread();
        despotTrhead.setName("despotThread");
        despotTrhead.setPriority(PriorityScheduler.instance().getNormPriority()+4);

        
        

       

        InterrumpiblePeriodicThread th1 =  new InterrumpiblePeriodicThread();
        RelativeTime period = new RelativeTime(100, 0);
        RelativeTime deadline = new RelativeTime(70, 0);
        PeriodicParameters periodicParameters = new PeriodicParameters(period);
        periodicParameters.setDeadline(deadline);
        th1.setReleaseParameters(periodicParameters);
        th1.setName("Thread1");
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+1);
        th1.setExcecutionTime(80);
        
        th1.setNumberOfIterations(7);


        StopPolicyHandler handler = new StopPolicyHandler(th1, PriorityScheduler.instance().getNormPriority()+4, "sleepingHandler");
       


        th1.setDeadlineMissedHandler(handler);

        


        Log launcherLog = new Log();

        



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
        despotTrhead.start();
       
        th1.start();
       
       
        try {
           
            
          
            
            th1.join(6000);
           
            
            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher22.class.getName()).log(Level.SEVERE, null, ex);
        }



        System.out.println("Laucher: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
        logs.add(handler.getLog());
        logs.add(launcherLog);
        
       
        
        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);




    }





}
