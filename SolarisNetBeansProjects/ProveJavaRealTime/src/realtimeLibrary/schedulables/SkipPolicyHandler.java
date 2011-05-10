/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;


/**
 *
 * @author Marco Nanni
 * Handler di deadline che fa si che, ogni volta che il thread associato
 * (che deve essere un PeriodicThread) sfora la deadline questo aumenta il
 * suo skipNumber in modo da realizzare la politica di skip
 */
public class SkipPolicyHandler extends DeadlineMissedHandler {

    public SkipPolicyHandler() {
    }

    /**
     * 
     * @param controlledThread il periodicThread monitorato dall'handler
     * @param priority la priorità dell'handler
     * @param name il nome dell'handler
     */
    public SkipPolicyHandler(PeriodicThread controlledThread, int priority, String name) {
        super(controlledThread, priority, name);
    }



    @Override
    /**
     * metodo che viene invocato ad ogni deadline missed
     * scrive l'evento sul proprio log,
     * aumenta il parametro skipNumber del thread  controllato
     * e lo rischedula, in modo da avere un comportamente di tipo skip
      */
    public void handleAsyncEvent() {
        Thread.currentThread().setName(this.getName());

        super.getLog().writeDeadlineMissed(super.getControlledThread().getName());
        ((PeriodicThread)super.getControlledThread()).incrementSkipNumber();
        this.getControlledThread().schedulePeriodic();

    }





}
