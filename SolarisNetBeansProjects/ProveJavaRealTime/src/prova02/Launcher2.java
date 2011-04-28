/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova02;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.AbsoluteTime;
import javax.realtime.PriorityScheduler;
import javax.realtime.RelativeTime;

/**
 *
 * @author root
 */
public class Launcher2 {

    
    
    public static void main(String []args){
//       int millisecondi = 10000;
//       RelativeTime relTime= new RelativeTime(millisecondi, 0);
//       System.out.println("La stama di un relative time creato con " +millisecondi+" millisecondi è :" +relTime);
       
//       System.out.println("Differenza tra absolute time");
//       AbsoluteTime abs1= new AbsoluteTime(new Date (System.currentTimeMillis()));
//       AbsoluteTime abs2= new AbsoluteTime(new Date (System.currentTimeMillis()+3000));
//       RelativeTime diff = abs2.subtract(abs1);
//       System.out.println("La differenza tra gli absoluteTime dobrevve essere 3000, in realtà è " +diff);

//        System.out.println("Differenza tra absolute time ottenuti tramite sleep");
//       AbsoluteTime abs1= new AbsoluteTime(new Date (System.currentTimeMillis()));
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Launcher2.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       AbsoluteTime abs2= new AbsoluteTime(new Date (System.currentTimeMillis()));
//       RelativeTime diff = abs2.subtract(abs1);
//       System.out.println("La differenza tra gli absoluteTime dobrevve essere 3000, in realtà è " +diff);

       System.out.println ("Stampa delle priorità dello scheduler");

       PriorityScheduler ps = PriorityScheduler.instance();
       System.out.println("La priorità massima è "+ps.getMaxPriority());
       System.out.println("La priorità minima è "+ ps.getMinPriority());
       



    }
}
