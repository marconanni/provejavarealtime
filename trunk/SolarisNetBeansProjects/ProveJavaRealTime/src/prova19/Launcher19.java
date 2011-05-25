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
        th1.setReleaseParameters(new PeriodicParameters(period));
        th1.setName("Thread1");
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+2);
        th1.setExcecutionTime(50);
        th1.setBadExcecutionTime(50);
        th1.setBadIteration(1);
        th1.setNumberOfIterations(7);


        SleepingHandler handler = new SleepingHandler(th1, PriorityScheduler.instance().getNormPriority()+4, "sleepingHandler",0);
        handler.setSleepingTime(0);


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
            sleep(160);
            RelativeTime newDeadLine= new RelativeTime(40,0);
            PeriodicParameters newParameters = new PeriodicParameters(null, period, null, newDeadLine, null, handler);
            th1.setReleaseParameters(newParameters);
            launcherLog.writeGenericMessage("changedReleaseParameters");
            
            th1.join(6000);
            
            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher19.class.getName()).log(Level.SEVERE, null, ex);
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
