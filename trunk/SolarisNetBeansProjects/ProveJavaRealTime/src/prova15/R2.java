/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova15;

import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.logging.ResourceLog;
import realtimeLibrary.resources.ExclusiveResource;

/**
 *
 * @author Marco Nanni
 * classe che rapresenta la risorsa r2, acceduta d p2 e da p3
 * è la seconda ad essere acceduta nellìesecuzione innestata dip3
 */
public class R2  extends ExclusiveResource{
    
    private R1 r1;

    public R2(String resourceName) {
        super (resourceName);
    }

    public R2(ResourceLog log, String resourceName) {
        super(log, resourceName);
    }

    @Override
    /**
     * il thread r3 usa la risorsa per 100 ms, poi accede a r1
     * poi riusa la risorsa per altri 100 ms.
     * tutti gli altri thread usano la risorsa per 100 ms e basta
     */
    public synchronized void useResource(Thread userThread) {
        this.getLog().writeStartUseResource();
       if (userThread.getName().equals("Thread3")){
           BusyWait.getInstance().doJobFor(200);
           r1.useResource(userThread);
           BusyWait.getInstance().doJobFor(200);
       }
         else
             BusyWait.getInstance().doJobFor(200);


        this.getLog().writeFinishUseResource();

    }



    public R1 getR1() {
        return r1;
    }

    public void setR1(R1 r1) {
        this.r1 = r1;
    }





}
