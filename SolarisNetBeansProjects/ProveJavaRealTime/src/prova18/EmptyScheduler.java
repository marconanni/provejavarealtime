/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova18;

import javax.realtime.MemoryParameters;
import javax.realtime.ProcessingGroupParameters;
import javax.realtime.ReleaseParameters;
import javax.realtime.Schedulable;
import javax.realtime.Scheduler;
import javax.realtime.SchedulingParameters;

/**
 *
 * @author root
 */
public class EmptyScheduler extends Scheduler {

    @Override
    protected boolean addToFeasibility(Schedulable schedulable) {
      int k=0;
       k++;
        return true;
    }

    @Override
    public void fireSchedulable(Schedulable schedulable) {
        int k=0;
       k++;
    }

    @Override
    public String getPolicyName() {
      int k=0;
       k++;
       return "";
    }

    @Override
    public boolean isFeasible() {
         int k=0;
       k++;
       return true;
    }

    @Override
    protected boolean removeFromFeasibility(Schedulable schedulable) {
        int k=0;
       k++;
       return true;
    }

    @Override
    public boolean setIfFeasible(Schedulable schedulable, ReleaseParameters release, MemoryParameters memory) {
         int k=0;
       k++;
       return true;
    }

    @Override
    public boolean setIfFeasible(Schedulable schedulable, ReleaseParameters release, MemoryParameters memory, ProcessingGroupParameters group) {
         int k=0;
       k++;
       return true;
    }

    @Override
    public boolean setIfFeasible(Schedulable schedulable, SchedulingParameters scheduling, ReleaseParameters release, MemoryParameters memory, ProcessingGroupParameters group) {
       int k=0;
       k++;
       return true;
    }



}
