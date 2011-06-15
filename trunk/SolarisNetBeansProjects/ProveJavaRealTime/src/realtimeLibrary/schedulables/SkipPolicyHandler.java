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
public class SkipPolicyHandler extends DeadlineMissedHandler  {

    private int skipCount=0;
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
        this.skipCount++;
        super.getControlledThread().setPendingMode(true);
        this.getControlledThread().schedulePeriodic();

    }

    @Override
    public void doPendingJob(PeriodicThread managedThread) {
       this.decrementSkipCount();
       this.getLog().writeSkippedJob(this.getControlledThread().getName());
       if(this.getSkipCount()==0)
           this.getControlledThread().setPendingMode(false);

    }



    

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    public void incrementSkipCount(){
        this.skipCount++;
    }

    public void decrementSkipCount(){
        this.skipCount--;
    }







}
