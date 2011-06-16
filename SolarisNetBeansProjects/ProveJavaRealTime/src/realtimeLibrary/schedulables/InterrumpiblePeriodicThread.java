/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

import javax.realtime.AsynchronouslyInterruptedException;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.schedulables.PeriodicThread;

/**
 *
 * @author Marco Nanni
 * classe che rappresenta un thread la cui esecuzione è interrompibile chiamando il metodo
 * interrupt mentre è in esecuzione.
 */
public class InterrumpiblePeriodicThread extends PeriodicThread{

    @Override
    /**
     * è il metodo che contiene la businness logic, che si riduce alla chiamata
     * di una busy wait interrompibile di durata pari a excecutionTime e raccoglie
     * eventualmente l'eccezione causata dalla chiamata di un metodo interrupt
     * scrivendo sul log che il thred è stato interrotto.
     */
    protected void doJob() {
        try {
           BusyWait.getInstance().doInterrumpibleJobFor(this.getExcecutionTime());
          
        } catch (AsynchronouslyInterruptedException ex) {
            ex.clear();
            this. getLog().writeInterruptedJob(this.getName());

        }

    }

    



}
