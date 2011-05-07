/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.resources;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.logging.ResourceLog;

/**
 *
 * @author Marco Nanni
 * classe che modella la generica risorsa ad accesso esclusivo.
 * esibisce una serie di metodi astratti che devono essere implementari
 * per mostrare i comportamenti specifici per ogni evento
 */
public abstract class ExclusiveResource {

    private ResourceLog log;
    private String resourceName;

    public ExclusiveResource(ResourceLog log, String resourceName) {
        this.log = log;
        this.log.setResourceName(resourceName);
        this.resourceName = resourceName;
    }

    public ExclusiveResource(String resourceName) {
        this.resourceName = resourceName;
        this.log = new ResourceLog(resourceName);
    }
    
    /**
     * Metodo che modella l'uso della risorsa per un determinato periodo di tempo.
     * non fa altro che registrare l'uso della risorsa nel log ed eseguire una busyWait
     * lunga excecutionTime
     *
     * @param excetutionTime la durata dell'uso della risorsa
     */
    public synchronized  void useResouceFor(long excetutionTime){
        log.writeStartUseResource();
        BusyWait.getInstance().doJobFor(excetutionTime);
        log.writeFinishUseResource();
    }
    
    /**
     * Metodo che permette di modellare l'uso della risorsa differenziando
     * l'esecuzione in base al thread.
     * E' DA SOVRASCRIVERE! : avrei voluto farlo astratto, ma il
     * sistema non mi permette di fare un metodo astratto e synchronized
     * @param userThread il thread che sta accedendo alla risorsa
     */
    public synchronized  void useResource(Thread userThread){
        ;
    }

    public ResourceLog getLog() {
        return log;
    }

    public void setLog(ResourceLog log) {
        this.log = log;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }








}
