/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova01;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Launcher {

    public static void main (String[] args){
        try {
            PriorityScheduler scheduler = PriorityScheduler.instance();
            WorkerThread worker = new WorkerThread();
            worker.setPriority(scheduler.getMaxPriority() - 5);
            worker.setExecutionTime(1000);
            System.out.println("starting");
            Thread.sleep(15000);
            worker.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
