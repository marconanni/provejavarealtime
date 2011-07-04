/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

/**
 *
 * @author Marco Nanni
 * classe che modella un  modella un gestore che, finchè un job non sfora
 * consecutivamente la deadline più di un certo valore adotta la politica asap,
 * schedulando i job successivi immediatamente dopo la fine del primo.
 * Tuttavia, qualora il job sfori almeno tante deadline quante la soglia,
 * allora si decide di interromperne l'esecuzione.
 */
public class ASAPStopThresholdPolicyHandler extends ThresholdPolicyHandler{

    private ASAPPolicyHandler asapPolicyHandler;
    private StopPolicyHandler stopPolicyHandler;
    private int skipCount;

    public ASAPStopThresholdPolicyHandler() {
        super();
        asapPolicyHandler = new ASAPPolicyHandler();
        stopPolicyHandler = new StopPolicyHandler();
    }

    public ASAPStopThresholdPolicyHandler(InterrumpiblePeriodicThread controlledThread, int priority, String name, int threshold) {
    super(controlledThread, priority, name, threshold);
    asapPolicyHandler = new ASAPPolicyHandler(controlledThread, priority, name);
    stopPolicyHandler = new StopPolicyHandler(controlledThread, priority, name);
    this.getControlledThread().setPendingJobManager(this);
    }

     @Override
    public synchronized void  handleAsyncEvent() {
        this.getLog().writeDeadlineMissed(this.getControlledThread().getName());
         this.getControlledThread().setPendingMode(true);
         this.incrementDeadlineCount();
         if(this.getDeadlineCount()<this.getThreshold()){
             asapPolicyHandler.handleAsyncEvent();
         }
     else{// ho raggiunto il limite: devo stoppare il thread
             this.setSkipCount(this.getDeadlineCount()-1);
             this.setDeadlineCount(0);
             this.getAsapPolicyHandler().setPendingReleases(0);
             this.getStopPolicyHandler().handleAsyncEvent();

     }

    }


    @Override
    public synchronized void doPendingJob(PeriodicThread managedThread) {
       if (this.getSkipCount()==0){
           this.getLog().writeStartPendingJob();
           this.asapPolicyHandler.doPendingJob(this.getControlledThread());
           this.getLog().writeEndPendingJob();
       }
     else{
           this.getLog().writeSkippedJob();
           this.decrementSkipCount();
           if (this.getSkipCount()==0)
               this.getControlledThread().setPendingMode(false);

     }
           
    }








    @Override
    public InterrumpiblePeriodicThread getControlledThread() {
        return (InterrumpiblePeriodicThread) super.getControlledThread();
    }

  
    public void setControlledThread(InterrumpiblePeriodicThread controlledThread) {
        super.setControlledThread(controlledThread);
        this.getAsapPolicyHandler().setControlledThread(controlledThread);
        this.getStopPolicyHandler().setControlledThread(controlledThread);
        controlledThread.setPendingJobManager(this);

    }








    


    protected ASAPPolicyHandler getAsapPolicyHandler() {
        return asapPolicyHandler;
    }

    protected void setAsapPolicyHandler(ASAPPolicyHandler asapPolicyHandler) {
        this.asapPolicyHandler = asapPolicyHandler;
    }

    protected StopPolicyHandler getStopPolicyHandler() {
        return stopPolicyHandler;
    }

    protected void setStopPolicyHandler(StopPolicyHandler stopPolicyHandler) {
        this.stopPolicyHandler = stopPolicyHandler;
    }

    protected int getSkipCount() {
        return skipCount;
    }

    protected void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    protected void incrementSkipCount(){
        this.skipCount++;
    }

    protected void decrementSkipCount(){
        this.skipCount--;
    }








}
