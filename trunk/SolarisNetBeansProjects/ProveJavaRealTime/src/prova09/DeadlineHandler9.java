/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova09;
import java.io.StringWriter;
import javax.realtime.*;
/**
 *
 * @author root
 */
public class DeadlineHandler9 extends BoundAsyncEventHandler {

    private  int missedDeadlineCount =0;
    private AbsoluteTime zeroTime ;
    private StringWriter stringWriter;
    private WorkerThread9 controlledThread;

    public WorkerThread9 getControlledThread() {
        return controlledThread;
    }

    public void setControlledThread( WorkerThread9 controlledThread) {
        this.controlledThread = controlledThread;
    }

    public int getMissedDeadlineCount() {
        return missedDeadlineCount;
    }

    public void setMissedDeadlineCount(int missedDeadlineCount) {
        this.missedDeadlineCount = missedDeadlineCount;
    }

    public StringWriter getStringWriter() {
        return stringWriter;
    }

    public void setStringWriter(StringWriter stringWriter) {
        this.stringWriter = stringWriter;
    }

    public AbsoluteTime getZeroTime() {
        return zeroTime;
    }

    public void setZeroTime(AbsoluteTime zeroTime) {
        this.zeroTime = zeroTime;
    }

    protected RelativeTime getCurrentTime(){
        return Clock.getRealtimeClock().getTime().subtract(this.getZeroTime());
    }

    @Override
    public void handleAsyncEvent() {
        this.missedDeadlineCount++;
        this.output("Handler chiamato all'istante "+this.getCurrentTime()+" sono intervenuto già già "+this.getMissedDeadlineCount()+" volte");
       if(this.controlledThread !=null)
           this.getControlledThread().setEndwork(true);
           this.getControlledThread().schedulePeriodic();

    }





    protected void output(String message){
        if (stringWriter!=null)
            stringWriter.write("\n"+message);
         else
             System.out.println(message);
    }

}
