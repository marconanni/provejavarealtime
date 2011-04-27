/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova7;
import java.io.StringWriter;
import javax.realtime.*;

/**
 *
 * @author root
 */
public class Resource {
    private StringWriter stringWriter;

    public StringWriter getStringWriter() {
        return stringWriter;
    }

    public void setStringWriter(StringWriter stringWriter) {
        this.stringWriter = stringWriter;
    }

    public synchronized void useResourceFor(long millis){
        RealtimeThread currentThread = RealtimeThread.currentRealtimeThread();
        this.output("Sono il thread "+currentThread.getName()+" sono entrato in possesso della risorsa, la mia priorità attuale è "+currentThread.getPriority());
        this.doJobFor(millis);
        this.output("Sono il thread "+currentThread.getName()+" Sto per uscire dalla risorsa, La mia priorità attuale è" + RealtimeThread.currentRealtimeThread().getPriority());
    }


    protected void doJobFor(long millis){
        AbsoluteTime startTime = Clock.getRealtimeClock().getTime();
        RelativeTime currentTime = new RelativeTime(0,0);
        RelativeTime endTime = new RelativeTime(millis,0);
        int k=3;
        while (currentTime.compareTo(endTime)<0){
            k++;
            k--;
            currentTime.set(Clock.getRealtimeClock().getTime().subtract(startTime));
        }
    }

    protected void output(String message){
        if (stringWriter!=null)
            stringWriter.write("\n"+message);
         else
             System.out.println(message);
    }
        

}
