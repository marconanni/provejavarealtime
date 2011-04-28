/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova06;
import java.io.StringWriter;
import javax.realtime.*;
/**
 *
 * @author root
 */
public class DeadlineHandler extends BoundAsyncEventHandler {

    private  int missedDeadlineCount =0;
    private AbsoluteTime zeroTime ;
    private StringWriter stringWriter;
    private RealtimeThread controlledThread;

    public RealtimeThread getControlledThread() {
        return controlledThread;
    }

    public void setControlledThread(RealtimeThread controlledThread) {
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
        this.output("Deadline Sforata all'istante "+this.getCurrentTime()+" con questa ho sforato la deadline già "+this.getMissedDeadlineCount()+" volte");
       if(this.controlledThread !=null)
           this.getControlledThread().schedulePeriodic();

    }





    protected void output(String message){
        if (stringWriter!=null)
            stringWriter.write("\n"+message);
         else
             System.out.println(message);
    }

}
