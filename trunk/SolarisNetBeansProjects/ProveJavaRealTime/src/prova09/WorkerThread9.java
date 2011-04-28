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
public class WorkerThread9 extends RealtimeThread{

    private  int iterations = 1;

    private AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
    private boolean endwork;

    public boolean isEndwork() {
        return endwork;
    }

    public void setEndwork(boolean endwork) {
        this.endwork = endwork;
    }
   

    public  int getIterations() {
        return iterations;
    }

    public  void setIterations(int iterations) {
        this.iterations = iterations;
    }

   
    private StringWriter stringWriter ;

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

    @Override
    public void run() {

        for (int k=0; k<iterations; k++){
            this.output("Sono il thread "+this.getName()+ " inizio l'iterazione "+k+" all'istante"+this.getCurrentTime());
            this.doJobFor();
            
            
            this.output("Sono il thread "+this.getName()+ " finisco l'esecuzione dell'iterazione "+k+" all'istante"+this.getCurrentTime());
            waitForNextPeriod();

        }

    }

    protected void doJobFor(){
        int k=4;
        while (!endwork){
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
