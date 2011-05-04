/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova10;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;

import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.logging.Log;
import realtimeLibrary.logging.SchedulableLog;

/**
 *
 * @author Marco Nanni
 */
public class Launcher10 extends RealtimeThread {

    public static void main (String [] args){

        Launcher10 launcher10 = new Launcher10();
        launcher10.setName("Launcher");
        launcher10.setPriority(PriorityScheduler.instance().getMaxPriority());
//        System.out.println("20 secondi alla partenza");
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Launcher10.class.getName()).log(Level.SEVERE, null, ex);
//        }
        launcher10.start();

        



    }

    public void run (){
        WorkerThread10 thread = new WorkerThread10();
        thread.setIterations(5);
        thread.setWorkTime(1000);
        thread.setName("Thread1");
        thread.setLog(new SchedulableLog());
        thread.setReleaseParameters(new PeriodicParameters(new RelativeTime(2000, 0)));
        thread.setPriority(PriorityScheduler.instance().getNormPriority());
        System.out.println("Inizializzazione busy Wait");
        BusyWait.getInstance().initialize(1000,2000);
        System.out.println("Inizializzazione Terminata; il numero di iterazioni al millesimo è"+ BusyWait.getInstance().getCicliPerMillisecondo() );
        System.out.println("Partenza!");
        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
        thread.start();
        try {
            thread.join(80000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher10.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Stampa Risultati");
        Vector<Log> logs = new Vector<Log>();

        logs.add(thread.getLog());
        String result =realtimeLibrary.logging.Util.relativeMerge(logs,zeroTime);
        System.out.println(result);
    }

}
