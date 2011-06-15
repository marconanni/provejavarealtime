/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

/**
 *
 * @author Marco Nanni
 * è il thread che implementa la politica stop: quando un thread viola la deadline
 * la sua esecuzione viene interrotta. Deve essere associato ad un
 * InterrumpiblePeriodicThread
 */
public class StopPolicyHandler extends DeadlineMissedHandler {

    public StopPolicyHandler() {
        super();
    }

    public StopPolicyHandler(InterrumpiblePeriodicThread controlledThread, int priority, String name) {
        super(controlledThread, priority, name);
    }




    @Override
    /**
     * interrompe l'esecuzione del job delthread controllato invocando su di
     * lui il metodo interrupt.
     */
    public void handleAsyncEvent() {
        this.getLog().writeDeadlineMissed(this.getControlledThread().getName());
        this.getControlledThread().interrupt();
        this.getControlledThread().schedulePeriodic();

    }

    @Override
    public InterrumpiblePeriodicThread getControlledThread() {
        return (InterrumpiblePeriodicThread) super.getControlledThread();
    }

  
    public void setControlledThread(InterrumpiblePeriodicThread controlledThread) {
        super.setControlledThread(controlledThread);
    }



    



}
