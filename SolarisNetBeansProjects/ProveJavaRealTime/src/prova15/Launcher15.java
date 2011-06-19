/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova15;

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
public class Launcher15 extends RealtimeThread {


    public static void main (String [] args ){

        Launcher15 launcher = new Launcher15();
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

        // creo le risorse
        R1 r1 = new R1("r1");
        R2 r2 = new R2("R2");
        r2.setR1(r1);
        

        // creo il thread 1: quello più prioritario che parte dopo 500 ms

        ResourceThread15 th1 = new ResourceThread15();
        th1.setPriority(PriorityScheduler.instance().getNormPriority()+3);
        th1.setName("Thread1");
        th1.setNumberOfIterations(1);
        th1.setResource(r1);
        th1.setReleaseParameters(new PeriodicParameters(new RelativeTime(500, 0), new RelativeTime(6000, 0)));
        

        

        //  creo il thread 2: quello a priorità 2 che però parte dopo 200 ms
        ResourceThread15 th2 = new ResourceThread15();
        th2.setPriority(PriorityScheduler.instance().getNormPriority()+2);
        th2.setName("Thread2");
        th2.setNumberOfIterations(1);
        th2.setResource(r2);
        th2.setReleaseParameters(new PeriodicParameters(new RelativeTime(200, 0), new RelativeTime(6000, 0)));

         //  creo il thread 3: quello a priorità minima che  parte subito
        ResourceThread15 th3 = new ResourceThread15();
        th3.setPriority(PriorityScheduler.instance().getNormPriority()+1);
        th3.setName("Thread3");
        th3.setNumberOfIterations(1);
        th3.setResource(r2);
        th3.setReleaseParameters(new PeriodicParameters( new RelativeTime(6000, 0)));



        System.out.println("Laucher: confugurazione BusyWait");
        BusyWait busyWait = BusyWait.getInstance();
        busyWait.initialize(1000, 3000);

        System.out.println("Laucher: Lancio tra 10 secondi!");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher15.class.getName()).log(Level.SEVERE, null, ex);
        }
        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
        despotTrhead.start();
        th3.start();
        th2.start();
        th1.start();
        try {
            th1.join(6000);
            th2.join(6000);
            th3.join(6000);
            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher15.class.getName()).log(Level.SEVERE, null, ex);
        }



        System.out.println("Laucher: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(th1.getLog());
        logs.add(th2.getLog());
        logs.add(th3.getLog());
        logs.add(r1.getLog());
        logs.add(r2.getLog());

        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);




    }





}
