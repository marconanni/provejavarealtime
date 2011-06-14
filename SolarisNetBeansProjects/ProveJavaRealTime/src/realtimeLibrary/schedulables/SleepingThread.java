/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class SleepingThread extends PeriodicThread{

    @Override
    protected void doJob() {
        try {
            sleep(super.getExcecutionTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(SleepingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
