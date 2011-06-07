/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova;

import java.util.StringTokenizer;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        RealtimeThread th1 = new RealtimeThread();
        System.out.printl("partenza!");
       System.out.println(""+th1.getPriority());
        
        System.out.println(""+PriorityScheduler.instance().getNormPriority());

        AsyncEventHandler handler = new AsyncEventHandler();

        System.out.println(""+((PriorityParameters)handler.getSchedulingParameters()).getPriority());


     
      

       
    }

}
