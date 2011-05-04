/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;
import javax.realtime.AsyncEventHandler;
import javax.realtime.BoundAsyncEventHandler;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;
import realtimeLibrary.logging.SchedulableLog;


/**
 *
 * @author Marco Nanni: template per l'handler di deadline
 * relativo ad un thread periodico
 * nn fa niente solo scrive l'evento sul log e rischedula il thread controllato
 */
public class DeadlineMissHandler extends AsyncEventHandler  {

     private RealtimeThread controlledThread;
     private String name;
     private SchedulableLog log;

    public DeadlineMissHandler(RealtimeThread controlledThread, int priority, String name) {
        super();
        this.controlledThread = controlledThread;
        this.setSchedulingParameters(new PriorityParameters(priority));
        this.name= name;
        this.log = new SchedulableLog();
    }

    @Override
    public void handleAsyncEvent() {
        Thread.currentThread().setName(this.getName());
        log.writeDeadlineMissed(controlledThread.getName());
        controlledThread.schedulePeriodic();

    }

    public int getPriority(){
        return ((PriorityParameters) this.getSchedulingParameters()).getPriority();
    }

    public void setPriority (int priority){
        this.setSchedulingParameters( new PriorityParameters(priority));
    }









    public RealtimeThread getControlledThread() {
        return controlledThread;
    }

    public void setControlledThread(RealtimeThread controlledThread) {
        this.controlledThread = controlledThread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchedulableLog getLog() {
        return log;
    }

    public void setLog(SchedulableLog log) {
        this.log = log;
    }









}
