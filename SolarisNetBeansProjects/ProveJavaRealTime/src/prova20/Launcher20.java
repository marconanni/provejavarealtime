/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova20;


import prova19.*;
import prova17.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;
import realtimeLibrary.logging.*;
import realtimeLibrary.schedulables.*;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.scheduler.EDFScheduler;


/**
 *
 * @author root
 */
public class Launcher20 extends RealtimeThread {



    public static void main (String [] args ){

        Launcher20 launcher = new Launcher20();
        launcher.setName("launcher");
        launcher.setPriority(PriorityScheduler.instance().getMaxPriority());
        launcher.start();


        
    }

    @Override
    public void run() {

        System.out.println("Laucher: confugurazione in corso");



//        Scheduler.setDefaultScheduler(EDFScheduler.instance());

        // creo il thread che deve occupare una cpu
        CpuDespotTrhead despotTrhead = new CpuDespotTrhead();
        despotTrhead.setName("despotThread");
        despotTrhead.setPriority(PriorityScheduler.instance().getNormPriority()+7);

        // creo tre thread con periodo uguale, ma la stessa deadline e lo stesso tempo di esecuzione

        RelativeTime commonPeriod  = new RelativeTime(1000,0);
        long commonExcecutionTime =100;
        int commonNumberOfIterations = 10;

        
        // primo thread: deadline pari al periodo
            PeriodicThread th1 = new PeriodicThread();
        th1.setName("Thread1");
        th1.setExcecutionTime(commonExcecutionTime);
        th1.setNumberOfIterations(commonNumberOfIterations);
        th1.setReleaseParameters(new PeriodicParameters(commonPeriod));
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+1);

        // secondo thread: deadline a 70

        PeriodicThread th2 = new PeriodicThread();
        th2.setName("Thread2");
        th2.setExcecutionTime(commonExcecutionTime);
        th2.setNumberOfIterations(commonNumberOfIterations);
        th2.setReleaseParameters(new PeriodicParameters(commonPeriod));
//        th2.getReleaseParameters().setDeadline(new RelativeTime(70, 0));
         th2.setPriority(PriorityScheduler.instance().getNormPriority()+2);

        // terzo thread: deadline a 40

        PeriodicThread th3 = new PeriodicThread();
        th3.setName("Thread3");
        th3.setExcecutionTime(commonExcecutionTime);
        th3.setNumberOfIterations(commonNumberOfIterations);
        th3.setReleaseParameters(new PeriodicParameters(commonPeriod));
//        th3.getReleaseParameters().setDeadline(new RelativeTime(40, 0));
         th3.setPriority(PriorityScheduler.instance().getNormPriority()+3);

         System.out.println("Laucher: confugurazione BusyWait");
        BusyWait busyWait = BusyWait.getInstance();
        busyWait.initialize(1000, 3000);

        System.out.println("Lancio!");

        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
        despotTrhead.start();
        th1.start();
        th2.start();
        th3.start();
        try {
            th1.join(60000);
            th2.join(60000);
            th3.join(60000);
            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher20.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
        
        

       

        



        System.out.println("Laucher: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
      
        logs.add(th2.getLog());
        logs.add(th2.getLog());
        logs.add(th3.getLog());
       
        
        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);



        




    }





}
