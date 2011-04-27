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

    public ResourceLog(int dimension) {
        super(dimension);
    }

    public ResourceLog() {
        super();
    }




    /**
     * metodo che permette di registrare l'inzio dell'uso della risorsa da parte del thread corrente
     */
    public void writeStartUseResource(){
        
            this.writeEvent("40");
       
    }

    /**
     * metodo che permette di registrare la fine dell'uso della risorsa da parte del thread corrente
     */
    public void writeFinishUseResource(){
       
            this.writeEvent("50");
        
    }

   
   

     




}
