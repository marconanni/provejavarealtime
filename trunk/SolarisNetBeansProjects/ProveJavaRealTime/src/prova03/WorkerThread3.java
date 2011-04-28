/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova03;
import java.io.StringWriter;
import javax.realtime.*;
/**
 *
 * @author root
 */
public class WorkerThread3 extends RealtimeThread {

    private AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
    private long executionTime;
    private StringWriter stringWriter;

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

        RelativeTime startTime = Clock.getRealtimeClock().getTime().subtract(this.getZeroTime());
        this.output("Thread "+this.getName()+" inizio l'esecuzione all'istante "+ startTime );
        RelativeTime currentTime = new RelativeTime(startTime);
        RelativeTime endTime = startTime.add(this.getExecutionTime(), 0);
        int k =3;
        while (currentTime.compareTo(endTime)<0){
            k++;
            k--;
            currentTime.set(Clock.getRealtimeClock().getTime().subtract(this.getZeroTime()));
        }

       this.output("Thread "+this.getName()+" termino l'esecuzione all'istante "+ currentTime );



    }

    public void output(String message){
        if (stringWriter !=null)
            stringWriter.write("\n"+message);
        else System.out.println(message);
    }




}
