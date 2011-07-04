/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

/**
 *
 * @author root
 */
public class SkipStopThresholdPolicyHandler extends ThresholdPolicyHandler {

    private int skipCount;

    public SkipStopThresholdPolicyHandler() {
        super();
        skipCount=0;
    }

    public SkipStopThresholdPolicyHandler(InterrumpiblePeriodicThread controlledThread, int priority, String name, int threshold) {
        super(controlledThread, priority, name, threshold);
        skipCount=0;
    }

    @Override
    public synchronized void handleAsyncEvent() {
        this.getLog().writeDeadlineMissed(this.getControlledThread().getName());
        this.getControlledThread().setPendingMode(true);
        this.incrementSkipCount();
        super.incrementDeadlineCount();
        if(super.getDeadlineCount()>= super.getThreshold()){
            super.setDeadlineCount(0);
            this.getControlledThread().interrupt();
        }
        this.getControlledThread().schedulePeriodic();
    }

    @Override
    public synchronized void doPendingJob(PeriodicThread managedThread) {
        this.getLog().writeSkippedJob(this.getControlledThread().getName());
        this.decrementSkipCount();
        if (this.getSkipCount()==0)
            this.getControlledThread().setPendingMode(false);
    }

    










    @Override
    public InterrumpiblePeriodicThread getControlledThread() {
        return (InterrumpiblePeriodicThread) super.getControlledThread();
    }


    public void setControlledThread(InterrumpiblePeriodicThread controlledThread) {
        super.setControlledThread(controlledThread);


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
