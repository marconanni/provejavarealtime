/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.busyWait;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.PriorityScheduler;

/**
 *
 * @author Marco Classe che realizza la funzionalità di busy wait,
 * è un singleton. Per poter usare il metodo workFor usare bisogna  che questa
 * sia inizializzata. Per farlo si deve invocare il metodo initialize
 */
public class BusyWait {
    
    private boolean initialized = false;
    private BusyWait instance = null;
    private float cicliPerMillisecondo =0;

    /**
     * Metodo che consente di calibrare la busy wait sulle capacità computazionali
     * della macchina sulla quale si sta eseguendo
     * @param initializationTime la durata della fase di calibrazione in millisecondi;
     * una maggiore durata di questa fase porta a calibrazioni più accurate
     */
    public void initialize(long initializationTime){
        InitializerMasterThread initializerThread = new InitializerMasterThread(initializationTime);
        initializerThread.setPriority(PriorityScheduler.instance().getMaxPriority());
        initializerThread.start();
        try {
            initializerThread.join(initializationTime * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(BusyWait.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCicliPerMillisecondo(initializerThread.getIterationCount()/initializationTime);
        this.setInitialized(true);
    }

    /**
     * Metodo che consente di calibrare la busy wait sulle capacità computazionali
     * della macchina sulla quale si sta eseguendo, questa fase dura cira cinque secondi
     * una maggiore durata di questa fase porta a calibrazioni più accurate
     */
    public void initialize(){
        this.initialize(5000);
    }

    /**
     * Metodo che consente di eseguire una busy wait per il tempo desiderato.
     * attenzione: se la classe non è stata precedentemente inizializzata
     * con il metodo initialize esegue una inizializzazione rapida di un secondo
     * @param milliseconds
     */
    public void doJobFor(long milliseconds){

        // se la classe non è inizializzata  eseguo una inizializzazione rapida
        //di un secondo, accorcia di conseguenza il tempo di esecuzione della busy wait
        if (!this.isInitialized()){
            System.err.println("Busy wait non ancora calibrata:eseguo una inizializzazione rapida: attenere 1 secondo ");
            this.initialize(1000);
            milliseconds = milliseconds-1000;
        }
       // i millisecondi potrebbero essere negativi: ad esempio, se l'utente ha richiesto una wait di
        //  mezzo secondo, ma non aveva inizializzato la classe ho già effettuto una fase di calibrazione
        // di un secondo e quindi è giusto che non venga bloccato ulteriormente.
        // Faccio notare come la variabile milliseconds in questo caso valga -500
        if(milliseconds >0){
        // così tronco la parte decimale
            long numberOfIteration = (long) ((this.getCicliPerMillisecondo() * milliseconds));
            int k =3;
            for (int i =0; i<numberOfIteration; i++){
                k++;
                k--;
            }
        }

    }


    
   /**
    *
    * @return true se la classe è già stato
    * chiamato il metodo initialize e la classe può quindi operare
    * regolarmente, false altrimenti
    */
    public boolean isInitialized() {
        return initialized;
    }

     /**
    * Metodo che viene eseguito al termine del metodo initialize per
      * formalizzzare che l'inizializzazione è stata completata
    * @param initialized  impostare a true dopo aver fatto
      * l'inizialiazione della classe
    */
    protected void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
    

    /**
     * 
     * @return un'istanza della classe BusyWait
     */
    public BusyWait getInstance() {
        if (instance == null)
            this.setInstance( new BusyWait());
        return instance;
    }

    public void setInstance(BusyWait instance) {
        this.instance = instance;
    }

    /**
     *
     * @return il numero dei cicli "perditempo"
     * che vengono eseguiti in media in un millisecondo
     */
    protected float getCicliPerMillisecondo() {
        return cicliPerMillisecondo;
    }


    /**
     *
     * @param cicliPerMillisecondo il numero dei cicli "perditempo"
     * che vengono eseguiti in media in un millisecondo
     */
    protected void setCicliPerMillisecondo(float cicliPerMillisecondo) {
        this.cicliPerMillisecondo = cicliPerMillisecondo;
    }
    
    
    
    

}
