/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova07;

import java.io.StringWriter;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class WorkerThread7 extends RealtimeThread{

    

    private AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
    private long executionTime =500;
   

  

    
    private StringWriter stringWriter ;

    public StringWriter getStringWriter() {
        return stringWriter;
    }

    public void setStringWriter(StringWriter stringWriter) {
        this.stringWriter = stringWriter;
    }


    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public AbsoluteTime getZeroTime() {
        return zeroTime;
    }

    public void setZeroTime(AbsoluteTime zeroTime) {
        this.zeroTime = zeroTime;
    }

    @Override
    public void run() {

        
            this.output("Sono il thread "+this.getName()+ " inizio l'iterazione all'istante"+this.getCurrentTime());
            this.doJobFor(this.executionTime);           
            this.output("Sono il thread "+this.getName()+ " finisco l'esecuzione  all'istante"+this.getCurrentTime());
            

        

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
