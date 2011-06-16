/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.busyWait;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.AbsoluteTime;
import javax.realtime.AsynchronouslyInterruptedException;
import javax.realtime.Clock;
import javax.realtime.RealtimeThread;
import realtimeLibrary.schedulables.PeriodicThread;

/**
 *
 * @author Marco Classe che realizza la funzionalità di busy wait,
 * è un singleton. Per poter usare il metodo workFor usare bisogna  che questa
 * sia inizializzata. Per farlo si deve invocare il metodo initialize
 */
public class BusyWait {
    
    private static boolean initialized = false;
    private static BusyWait instance = null;
    private static float cicliPerMillisecondo =0;
    private static long defaultInitializationTime=1000;
    private static long defaultCalibrationTime = 2000;

    /**
     * Metodo che consente di settare la busy wait sulle capacità computazionali
     * della macchina sulla quale si sta eseguendo.
     * La sua esecuzione si compone di due fasi: una di inizializzazione e una di
     * calibrazione
     * @param initializationTime la durata della fase di inizializzazione
     * in millisecondi; una maggiore durata di questa fase porta a misure
     * più accurate. Per non perdere in precisione è consigliabile un valore di almeno
     * 100 ms
     * @param calibrationTime a durata della fase di calibrazioni
     * in millisecondi; una maggiore durata di questa fase porta a misure
     * più accurate. Per non perdere in precisione è consigliabile un valore di almeno
     * 500 ms
     */
    public void initialize(long initializationTime, long calibrationTime){
        InitializerMasterThread initializerThread = new InitializerMasterThread(initializationTime);
        initializerThread.setPriority(Thread.currentThread().getPriority()-1 );
        initializerThread.start();
        try {
            initializerThread.join(initializationTime * 2);
        } catch (InterruptedException ex) {
            Logger.getLogger(BusyWait.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCicliPerMillisecondo(initializerThread.getIterationCount()/initializationTime);
        this.setInitialized(true);
        this.calibration(calibrationTime);
        
    }

    /** Metodo che consente di settare la busy wait sulle capacità computazionali
     * della macchina sulla quale si sta eseguendo.
     * La sua esecuzione si compone di due fasi: una di inizializzazione e una di
     * calibrazione.
     *
     */
    public void initialize(){
        this.initialize(BusyWait.getDefaultInitializationTime(),BusyWait.getDefaultCalibrationTime());
    }

    /**
     * facendo delle prove si è visto che il valore dei cicli per millisecondo ottenuto
     * dalla sola inizializzazione si discosta significativamente da quelli della busy wait
     * con il risultato di tempi di esecuzione molto discordanti. Questo metodo permette di
     * riallineare le cose: esegue una busy wait della durata di calibratioTime, ne calcola
     * la durata effettiva e sistema di conseguenza i cicli per millisecondo.
     * @param calibrationTime la durata nominale della fase di calibrazione in millisecondi
     * per non perdere in precisione è consigliabile un balore di almeno 500 ms
     */
    protected void calibration(long calibrationTime){

        AbsoluteTime startTime = Clock.getRealtimeClock().getTime();
        this.doJobFor(calibrationTime);
        AbsoluteTime endTime = Clock.getRealtimeClock().getTime();
        long effectiveExcecutionTime = endTime.subtract(startTime).getMilliseconds();
        float vecchiCicliPerMillisecondo = this.getCicliPerMillisecondo();
        this.setCicliPerMillisecondo((vecchiCicliPerMillisecondo/effectiveExcecutionTime)*calibrationTime);

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
            System.err.println("Busy wait non ancora calibrata:eseguo una inizializzazione rapida");
            this.initialize(100,500);
            milliseconds = milliseconds-600;
        }
       // i millisecondi potrebbero essere negativi: ad esempio, se l'utente ha richiesto una wait di
        //  mezzo secondo, ma non aveva inizializzato la classe ho già effettuto una fase di calibrazione
        // di un secondo e quindi è giusto che non venga bloccato ulteriormente.
        // Faccio notare come la variabile milliseconds in questo caso valga -500
        if(milliseconds >0){
        // così tronco la parte decimale
            long numberOfIteration = (long) ((this.getCicliPerMillisecondo() * milliseconds));
            int k =3;
            for (long i =0; i<numberOfIteration; i++){
                k++;
                k--;
            }
        }

    }

    public void doInterrumpibleJobFor(long milliseconds) throws AsynchronouslyInterruptedException{

        

         // se la classe non è inizializzata  eseguo una inizializzazione rapida
        //di un secondo, accorcia di conseguenza il tempo di esecuzione della busy wait
        if (!this.isInitialized()){
            System.err.println("Busy wait non ancora calibrata:eseguo una inizializzazione rapida");
            this.initialize(100,500);
            milliseconds = milliseconds-600;
        }
       // i millisecondi potrebbero essere negativi: ad esempio, se l'utente ha richiesto una wait di
        //  mezzo secondo, ma non aveva inizializzato la classe ho già effettuto una fase di calibrazione
        // di un secondo e quindi è giusto che non venga bloccato ulteriormente.
        // Faccio notare come la variabile milliseconds in questo caso valga -500
        if(milliseconds >0){
        // così tronco la parte decimale
            long numberOfIteration = (long) ((this.getCicliPerMillisecondo() * milliseconds));
            int k =3;
            for (long i =0; i<numberOfIteration; i++){
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
        BusyWait.initialized = initialized;
    }
    

    /**
     * 
     * @return un'istanza della classe BusyWait
     */
    public static BusyWait getInstance() {
        if (instance == null)
            BusyWait.setInstance( new BusyWait());
        return instance;
    }

    protected static void setInstance(BusyWait instance) {
        BusyWait.instance = instance;
    }

    /**
     *
     * @return il numero dei cicli "perditempo"
     * che vengono eseguiti in media in un millisecondo
     */
    public float getCicliPerMillisecondo() {
        return cicliPerMillisecondo;
    }


    /**
     *
     * @param cicliPerMillisecondo il numero dei cicli "perditempo"
     * che vengono eseguiti in media in un millisecondo
     */
    protected void setCicliPerMillisecondo(float cicliPerMillisecondo) {
        BusyWait.cicliPerMillisecondo = cicliPerMillisecondo;
    }

    /**
     *
     * @return la durata di defautl della fase di calibrazione
     * eseguita nel metodo initialize in millisecondi
     */
    public static long getDefaultCalibrationTime() {
        return defaultCalibrationTime;
    }

    /**
     *
     * @param defaultCalibrationTime la durata di defautl della fase di calibrazione
     * eseguita nelmetodo initialize in millisecondi . per non perdere precisione
     * è consigliabile un  valore  almeno di 500 ms
     */
    public static void setDefaultCalibrationTime(long defaultCalibrationTime) {
        BusyWait.defaultCalibrationTime = defaultCalibrationTime;
    }

    /**
     *
     * @return la durata di defautl della fase di initializzazione
     * eseguita nelmetodo initialize in millisecondi .
     */
    public static long getDefaultInitializationTime() {
        return defaultInitializationTime;
    }

    /**
     *
     * @param defaultInitializationTime la durata di defautl della fase di inizializzazione
     * eseguita nelmetodo initialize in millisecondi . per non perdere precisione
     * è consigliabile un  valore  almeno di 100 ms
     */
    public static void setDefaultInitializationTime(long defaultInitializationTime) {
        BusyWait.defaultInitializationTime = defaultInitializationTime;
    }


    
    
    

}
