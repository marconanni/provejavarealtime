/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova5;
import java.io.StringWriter;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class WorkerThread5 extends RealtimeThread{

    private  int iterations = 10;

     private AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
    private long shortExecutionTime =500;
    private long longExcecutionTime = 1500;

    public  int getIterations() {
        return iterations;
    }

    public  void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public long getLongExcecutionTime() {
        return longExcecutionTime;
    }

    public void setLongExcecutionTime(long longExcecutionTime) {
        this.longExcecutionTime = longExcecutionTime;
    }
    private StringWriter stringWriter ;

    public StringWriter getStringWriter() {
        return stringWriter;
    }

    public void setStringWriter(StringWriter stringWriter) {
        this.stringWriter = stringWriter;
    }


    public long getShortExecutionTime() {
        return shortExecutionTime;
    }

    public void setShortExecutionTime(long executionTime) {
        this.shortExecutionTime = executionTime;
    }

    public AbsoluteTime getZeroTime() {
        return zeroTime;
    }

    public void setZeroTime(AbsoluteTime zeroTime) {
        this.zeroTime = zeroTime;
    }

    @Override
    public void run() {

        for (int k=0; k<iterations; k++){
            this.output("Sono il thread "+this.getName()+ " inizio l'iterazione "+k+" all'istante"+this.getCurrentTime());
            if (k %2 ==0)
               this.doJobFor(this.shortExecutionTime);
            else
                this.doJobFor(this.getLongExcecutionTime());
            
            
            this.output("Sono il thread "+this.getName()+ " finisco l'esecuzione dell'iterazione "+k+" all'istante"+this.getCurrentTime());
            waitForNextPeriod();

        }

    }

    protected void doJobFor(long workTime){
        RelativeTime endTime = this.getCurrentTime().add(new RelativeTime(workTime, 0));
        int k=4;
        while (this.getCurrentTime().compareTo(endTime)<0){
            k++;
            k--;

        }
    }




    protected RelativeTime getCurrentTime(){
        return Clock.getRealtimeClock().getTime().subtract(this.getZeroTime());
    }

    protected void output(String message){
        if (stringWriter!=null)
            stringWriter.write("\n"+message);
         else
             System.out.println(message);
    }

}
