/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.logging;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.*;

/**
 *
 * @author Marco Nanni
 */
public class LibraryTestLauncher {

    public static void main (String [] args){
        // test per i log OK
//        testLogs();
        // testa il metodo SimpleMerge OK
//        testUtilSimpleMerge();
        // testa il metodo relativemerge
//        testUtilRelativeMerge();
        

    }

    /**
     * metodo che testa le funzionalità delle classi Log, ResourceLog
     * Schedulablelog e LogLine
     */
    private static void testLogs() {
        Log log = new Log();
        try {
            Thread.currentThread().setName("Thread1");
            System.out.println("provaLog");
            log.writeEvent("10");
            log.writeEvent("20");
            log.writeEvent("20");
            System.out.println(log);
            System.out.println(log.getLines());
            System.out.println("provaSchedulableLog");
            SchedulableLog schedulableLog = new SchedulableLog();
            schedulableLog.writeCreation();
            schedulableLog.writeStartJob();
            schedulableLog.writeEndJob();
            System.out.println(schedulableLog);
            System.out.println(schedulableLog.getLines());
            System.out.println("provaResourceLog");
            ResourceLog resourceLog = new ResourceLog();
            resourceLog.writeStartUseResource();
            resourceLog.writeFinishUseResource();
            System.out.println(resourceLog);
            System.out.println(resourceLog.getLines());
        } catch (Exception ex) {
            Logger.getLogger(LibraryTestLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void testUtilSimpleMerge(){

        Log log1 = new Log();
        Log log2 = new Log();
        Log log3 = new Log();

        log1.writeEvent("Thread1", "20", new AbsoluteTime(2000,0));
        log1.writeEvent("Thread1", "20", new AbsoluteTime(4000,0));
        log1.writeEvent("Thread1", "20", new AbsoluteTime(8000,0));

        log2.writeEvent("Thread2", "30", new AbsoluteTime(1000,0));
        log2.writeEvent("Thread2", "30", new AbsoluteTime(3000,0));
        log2.writeEvent("Thread2", "30", new AbsoluteTime(5000,0));

        log3.writeEvent("Thread3", "40", new AbsoluteTime(6000,0));
        log3.writeEvent("Thread3", "40", new AbsoluteTime(7000,0));
        log3.writeEvent("Thread3", "40", new AbsoluteTime(9000,0));

        Vector<Log> v = new Vector<Log>();
        v.add(log1);
        v.add(log2);
        v.add(log3);

        String result =Util.simpleMerge(v);
        System.out.println(result);

    }

    private static void testUtilRelativeMerge(){

        Log log1 = new Log();
        Log log2 = new Log();
        Log log3 = new Log();
        AbsoluteTime zerotime = Clock.getRealtimeClock().getTime();

        log1.writeEvent("Thread1", "20", Clock.getRealtimeClock().getTime().add(2000, 0));
        log1.writeEvent("Thread1", "20", Clock.getRealtimeClock().getTime().add(4000, 0));
        log1.writeEvent("Thread1", "20", Clock.getRealtimeClock().getTime().add(8000, 0));

        log2.writeEvent("Thread2", "30",Clock.getRealtimeClock().getTime().add(1000, 0));
        log2.writeEvent("Thread2", "30", Clock.getRealtimeClock().getTime().add(3000, 0));
        log2.writeEvent("Thread2", "30", Clock.getRealtimeClock().getTime().add(5000, 0));

        log3.writeEvent("Thread3", "40", Clock.getRealtimeClock().getTime().add(6000, 0));
        log3.writeEvent("Thread3", "40", Clock.getRealtimeClock().getTime().add(7000, 0));
        log3.writeEvent("Thread3", "50", Clock.getRealtimeClock().getTime().add(9000, 0));

        Vector<Log> v = new Vector<Log>();
        v.add(log1);
        v.add(log2);
        v.add(log3);

        String result =Util.relativeMerge(v, zerotime);
        System.out.println(result);

    }

}
