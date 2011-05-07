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
 * @author Marco: questa classemodella la risorsa utilizzata
 * da p1 e da p3. è l'ultima ad essere acceduta nell'accesso
 * annidato di p3
 */
public class R1 extends ExclusiveResource {



    public R1(String resourceName) {
        super(resourceName);
    }

    public R1(ResourceLog log, String resourceName) {
        super (log, resourceName);
    }

    @Override
    /**
     * sia p3 che p1 usano la risorsa per una sola unità di tempo
     * quindi viene tutto facile
     */
    public synchronized void useResource(Thread userThread) {
        this.getLog().writeStartUseResource();
        BusyWait.getInstance().doJobFor(200);
        this.getLog().writeFinishUseResource();
    }




}
