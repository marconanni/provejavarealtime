/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova23;

import javax.realtime.AsynchronouslyInterruptedException;
import javax.realtime.Interruptible;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.logging.Log;

/**
 *
 * @author root
 */
public class OggettoInterrompibile implements Interruptible {

    private Log log;
    private long excecutionTime;

    public OggettoInterrompibile() {
        this.log= new Log();
    }



    public void run(AsynchronouslyInterruptedException exception) throws AsynchronouslyInterruptedException {
       log.writeGenericMessage("Ogg.Interromp. inizio esecuzione normale");
       log.writeGenericMessage(Thread.currentThread().getName());
       BusyWait.getInstance().doInterrumpibleJobFor(excecutionTime);
       log.writeGenericMessage("Ogg.Interromp. fine esecuzione normale");
    }


    public void interruptAction(AsynchronouslyInterruptedException exception) {
       log.writeGenericMessage("Ogg.Interromp. all' interno di interruptAction");
        log.writeGenericMessage(Thread.currentThread().getName());
    }

    public long getExcecutionTime() {
        return excecutionTime;
    }

    public void setExcecutionTime(long excecutionTime) {
        this.excecutionTime = excecutionTime;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }



}
