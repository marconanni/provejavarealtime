/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova23;


import prova22.*;
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
public class Launcher23 extends RealtimeThread {



    public static void main (String [] args ){

        Launcher23 launcher = new Launcher23();
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

        
        

       

        OggettoInterrompibile oggettoInterrompibile = new OggettoInterrompibile();
        
       oggettoInterrompibile.setExcecutionTime(500);

       AsynchronouslyInterruptedException aie = new AsynchronouslyInterruptedException();
       AIEThread aiethread = new AIEThread(aie, oggettoInterrompibile);
       aiethread.setName("AIEThread");



        Log launcherLog = new Log();

        



        System.out.println("Laucher: confugurazione BusyWait");
        BusyWait busyWait = BusyWait.getInstance();
        busyWait.initialize(1000, 3000);

       
        
        

        AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
        despotTrhead.start();
       
        aiethread.start();
       
       
        try {
           sleep(200);
           launcherLog.writeGenericMessage("chiamata interrupt");
           aie.fire();
      
            
          
            
           
           
            
            despotTrhead.setContinueExcecution(false);
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher23.class.getName()).log(Level.SEVERE, null, ex);
        }



        System.out.println("Laucher: Stampa Risultati");

        Vector <Log> logs = new Vector<Log>();
        logs.add(oggettoInterrompibile.getLog());
        logs.add(launcherLog);
        
       
        
        String result =Util.relativeMerge(logs, zeroTime);
        System.out.println(result);




    }





}
