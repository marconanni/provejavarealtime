/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova18;

import javax.realtime.MemoryParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.ProcessingGroupParameters;
import javax.realtime.ReleaseParameters;
import javax.realtime.Schedulable;
import javax.realtime.SchedulingParameters;
import realtimeLibrary.logging.Log;

/**
 *
 * @author Marco Nanni
 *
 * classe che estende il priorityScheduler. non fa nient'altro se non scrivere
 * sul Log un messaggio relativo al metodo chiamato e chiamare la classe madre.
 * può essere una buona idea mettere dei breakpoint nei vari metodi per vedere
 * quando vengono chiamati.
 */
public class DummyPriorityScheduler extends PriorityScheduler {
    private Log log;

    public DummyPriorityScheduler() {
        super();
        this.log = new Log();
    }



    @Override
    protected synchronized boolean addToFeasibility(Schedulable schedulable) {
        log.writeGenericMessage("addToFeasibility");
        return super.addToFeasibility(schedulable);
    }

    @Override
    public void fireSchedulable(Schedulable schedulable) {
        log.writeGenericMessage("fireSchedulable");
        super.fireSchedulable(schedulable);
    }

    @Override
    public int getMaxPriority() {
        log.writeGenericMessage("getMaxPriority");
        return super.getMaxPriority();
    }

    @Override
    public int getMinPriority() {
        log.writeGenericMessage("getMinPriority");
        return super.getMinPriority();
    }

    @Override
    public int getNormPriority() {
        log.writeGenericMessage("getNormPriority");
        return super.getNormPriority();
    }

    @Override
    public String getPolicyName() {
        log.writeGenericMessage("getPolicyName");
        return super.getPolicyName();
    }

    @Override
    public boolean isFeasible() {
        log.writeGenericMessage("isFeasible");
        return super.isFeasible();
    }

    @Override
    protected boolean removeFromFeasibility(Schedulable schedulable) {
        log.writeGenericMessage("removeFromFeasibility");
        return super.removeFromFeasibility(schedulable);
    }

    @Override
    public boolean setIfFeasible(Schedulable schedulable, ReleaseParameters release, MemoryParameters memory) {
        log.writeGenericMessage("setIfFeasible");
        return super.setIfFeasible(schedulable, release, memory);
    }

    @Override
    public boolean setIfFeasible(Schedulable schedulable, ReleaseParameters release, MemoryParameters memory, ProcessingGroupParameters group) {
        log.writeGenericMessage("setIfFeasible");
        return super.setIfFeasible(schedulable, release, memory, group);
    }

    @Override
    public boolean setIfFeasible(Schedulable schedulable, SchedulingParameters sched, ReleaseParameters release, MemoryParameters memory, ProcessingGroupParameters group) {
        log.writeGenericMessage("setIfFeasible");
        
        return super.setIfFeasible(schedulable, sched, release, memory, group);
    }






    

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }





}
