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
public class ResourceThread extends RealtimeThread {

    private AbsoluteTime zeroTime = Clock.getRealtimeClock().getTime();
    private long executionTime = 1000;
    private StringWriter stringWriter;
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

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

        this.output("Thread "+this.getName()+": inizio la mia esecuzione all'istante "+ this.getCurrentTime()+"la mia priorità è "+this.getPriority());
        resource.useResourceFor(executionTime);
        this.output("Thread "+this.getName()+": termino la mia esecuzione all'istante "+ this.getCurrentTime()+"la mia priorità è "+this.getPriority());

    }

    protected void output(String message){
        if (stringWriter!=null)
            stringWriter.write("\n"+message);
         else
             System.out.println(message);
    }

    protected RelativeTime getCurrentTime(){
        return Clock.getRealtimeClock().getTime().subtract(this.getZeroTime());
    }




}
