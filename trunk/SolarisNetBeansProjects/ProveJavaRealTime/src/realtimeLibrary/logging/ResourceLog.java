/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.logging;

/**
 *
 * @author Marco
 *
 * Classe che modella il log associato ad una risorsa ad accesso esclusivo
 * Fornisce i metodi per indicare l'inizio e la fine dell'uso della risorsa da
 * parte di un thread
 */
public class ResourceLog extends Log {

    private String resourceName;

    /**
     *
     * @param dimension la dimensione in byte del buffer del log
     */
    public ResourceLog(int dimension) {
        super(dimension);
    }

    /**
     * costruisce un resourceLog con un buffer di 5000 byte
     */
    public ResourceLog() {
        super();
    }

    /**
     *
     * @param dimension la dimensione in byte del buffer del log
     * @param resourceName il nome della risorsa associata al log
     */
    public ResourceLog(int dimension, String resourceName) {
        super(dimension);
        this.resourceName = resourceName;
    }

    /**
     *
     * @param resourceName il nome della risorsa associata al log
     */
     public ResourceLog( String resourceName) {

        this.resourceName = resourceName;
    }






    /**
     * metodo che permette di registrare l'inzio dell'uso della risorsa da parte del thread corrente
     *
     */

     /*
      * viene scritta una riga tipo
      * nomeThread on nomeRisorsa;40;timestamp
      */
    public void writeStartUseResource(){
            String threadName = Thread.currentThread().getName();
            this.writeEvent(threadName+" on "+this.getResourceName(), "40");
        
            
       
    }

    /**
     * metodo che permette di registrare la fine dell'uso della risorsa da parte del thread corrente
     */

    /*
      * viene scritta una riga tipo
      * nomeThread on nomeRisorsa;40;timestamp
      */
    public void writeFinishUseResource(){

            String threadName = Thread.currentThread().getName();
            this.writeEvent(threadName+" on "+this.getResourceName(), "50");
       
            
        
    }

    /**
     *
     * @return il nome della risorsa a cui è associato il log.
     * Il nome verrà visualizzato negli eventi registrati insieme
     * a quello del processo che sta impegnando la risorsa.
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     *
     * @param resourceName il nome della risorsa a cui è associato il log.
     * Il nome verrà visualizzato negli eventi registrati insieme
     * a quello del processo che sta impegnando la risorsa.
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

   
   

     




}
