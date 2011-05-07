/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova15;

import javax.realtime.AsyncEventHandler;
import javax.realtime.RelativeTime;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.resources.ExclusiveResource;
import realtimeLibrary.schedulables.PeriodicThread;


/**
 *
 * @author Marco Nanni
 *
 * modella una classe che esegue la risorsa in maniera analoga
 * all'esempio della slide 15 del blocco sui protocolli di acccesso
 * a risorse condivise
 */
public class ResourceThread15 extends PeriodicThread{

    private ExclusiveResource resource;

    public ResourceThread15(String name, long excecutionTime, int numberOfIterations, int priority, RelativeTime period, RelativeTime startTime, RelativeTime deadline, AsyncEventHandler deadlineHandler, ExclusiveResource resource) {
        super(name,excecutionTime, numberOfIterations, priority, period, startTime, deadline, deadlineHandler);
        this.resource = resource;
    }

    public ResourceThread15() {
    }

    @Override
    /**
     * secondo l'esempio, esegue per una unità di tempo,
     * occupa la risorsa,
     * poi conclude l'esecuzione con un'altra unità di esecuzione
     * ho fatto in modo che il processo facesse lo stesso, con come
     * unità di tempo 100 ms
     */
    protected void doJob() {
        BusyWait busyWait = BusyWait.getInstance();
        busyWait.doJobFor(100);
        resource.useResource(this);
        busyWait.doJobFor(100);
    }

    



    public ExclusiveResource getResource() {
        return resource;
    }

    public void setResource(ExclusiveResource resource) {
        this.resource = resource;
    }




}
