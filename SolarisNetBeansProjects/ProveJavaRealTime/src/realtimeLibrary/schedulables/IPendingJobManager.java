/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

import javax.realtime.RealtimeThread;

/**
 *
 * @author root
 */
public interface IPendingJobManager {

    public void doPendingJob(PeriodicThread managedThread);

}
