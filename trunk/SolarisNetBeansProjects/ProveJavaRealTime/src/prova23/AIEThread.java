/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova23;

import javax.realtime.AsynchronouslyInterruptedException;
import javax.realtime.RealtimeThread;

/**
 *
 * @author root
 */
public class AIEThread extends RealtimeThread {
    private AsynchronouslyInterruptedException aie;
    private OggettoInterrompibile oggettoInterrompibile;

    public AIEThread(AsynchronouslyInterruptedException aie, OggettoInterrompibile oggettoInterrompibile) {
        this.aie = aie;
        this.oggettoInterrompibile = oggettoInterrompibile;
    }

    @Override
    public void run() {
        aie.doInterruptible(oggettoInterrompibile);
    }



}
