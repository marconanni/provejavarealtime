/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova19;


import prova17.*;
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
public class Launcher19 extends RealtimeThread {



    public static void main (String [] args ){

        Launcher19 launcher = new Launcher19();
        launcher.setName("launcher");
        launcher.setPriority(PriorityScheduler.instance().getMaxPriority());
        launcher.start();


        
    }

    @Override
    public void run() {

        System.out.println("Laucher: confugurazione in corso");

        // creo il thread che deve occupare una cpu
        CpuDespotTrhead despotTrhead = new CpuDespotTrhead();
        despotTrhead.setName("despotThread");
        despotTrhead.setPriority(PriorityScheduler.instance().getNormPriority()+4);

        
        

       

        BadThread th1 =  new BadThread();
        RelativeTime period = new RelativeTime(100, 0);
        RelativeTime deadline= new RelativeTime(70,0);
        PeriodicParameters periodicParameters = new PeriodicParameters(period);

        th1.setReleaseParameters(periodicParameters);
        th1.setName("Thread1");
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+1);
        th1.setExcecutionTime(40);
        th1.setBadExcecutionTime(40);
        th1.setBadIteration(1);
        th1.setNumberOfIterations(7);


        SleepingHandler handler = new SleepingHandler(th1, PriorityScheduler.instance().getNormPriority()+4, "sleepingHandler",0);
        handler.setSleepingTime(0);


        th1.setDeadlineMissedHandler(handler);

        PeriodicThread th2 = new PeriodicThread();
        th2.setReleaseParameters(periodicParameters);
        th2.setName("Thread2");
        th2.setPriority(th1.getPriority()+1);
        th2.setExcecutionTime(40);
        th2.setNumberOfIterations(7);


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
        th2.start();
       
        try {
            sleep(120);
//            PriorityParameters newSP = new PriorityParameters( th2.getPriority()+1);
//            th1.setSchedulingParameters(newSP);

            th2.setPriority(th1.getPriority()-1);
            
            launcherLog.writeGenericMessage("changedSchedulingParameters");
            
            th1.join(6000);
            th2.join(60000);
            
            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher19.class.getName()).log(Level.SEVERE, null, ex);
        }



        System.out.println("Laucher: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
        logs.add(handler.getLog());
        logs.add(launcherLog);
        logs.add(th2.getLog());
       
        
        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);



        




    }





}
