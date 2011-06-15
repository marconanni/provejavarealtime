/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova16;


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
public class Launcher16 extends RealtimeThread {


    public static void main (String [] args ){

        Launcher16 launcher = new Launcher16();
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

        
        

        // creo il thread 1:un thread che al primo ciclo ha un'esecuzione di
        // due cicli e mezzo. Implemento la politica di skip affidandoli
        // skipPolicyHandler
        // il thread ha periodo 100 ms ed esecuzione normale di 20

        BadThread th1 =  new BadThread();
        th1.setReleaseParameters(new PeriodicParameters(new RelativeTime(100, 0)));
        th1.setName("Thread1");
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+1);
        th1.setExcecutionTime(20);
        th1.setBadExcecutionTime(250);
        th1.setBadIteration(1);
        th1.setNumberOfIterations(7);

        SkipPolicyHandler handler = new SkipPolicyHandler(th1, PriorityScheduler.instance().getNormPriority()+2, "SkipHandler");
        th1.setDeadlineMissedHandler(handler);


        

       



        System.out.println("Laucher: confugurazione BusyWait");
        BusyWait busyWait = BusyWait.getInstance();
        busyWait.initialize(1000, 3000);

//        System.out.println("Laucher: Lancio tra 10 secondi!");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Launcher16.class.getName()).log(Level.SEVERE, null, ex);
//        }
        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
        despotTrhead.start();
       
        th1.start();
        try {
            th1.join(60000);
            
            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher16.class.getName()).log(Level.SEVERE, null, ex);
        }



        System.out.println("Laucher: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
        logs.add(handler.getLog());
        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);




    }





}
