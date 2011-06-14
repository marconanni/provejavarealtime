/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.scheduler;

import java.util.PriorityQueue;
import java.util.TreeMap;
import javax.realtime.*;


/**
 *
 * @author Marco Nanni
 * classe che modella uno scheduler EDF.
 * Come la classe madre è un singleton
 * Lo scheduler è un oggeto passivo: sono i vari thread che, eseguendo
 * i suoi metodi si sincronizzano.
 * Dato che eredita da priorityScheduler, è sempre in esecuzione il thread 
 * a masimma priorità. Questo però è sempre il thread con la deadline più
 * imminente.
 * Lo scheduer è realizzato secondo la classica strategia secondo la quale i
 * processi pronti per l'esecuzione sono messi in una coda ordiantata in base
 * alla prossima deadline. Il primo processo della coda viene prelevato
 * portato a priorità maggiore, in modo che eserciti preemption su tutti gli altri.
 * Quando un  processo inizia un job ha priorità ancora maggiore, esegue un breve
 * preambolo poi viene inserito nella coda a bassa priorità in base alla sua deadline.
 * PriorityInherintance garantisce l'assenza di problemi di sincronizzazione:
 * se il processo in esecuzione rimane bloccato su una risorsa posseduta da
 * un thread in coda, quest'ultimo ottiene la priorità del thread in esecuzione,
 * può così finire di usare la risorsa, liberarla e lasciare strada al processo
 * sospeso.
 */
public class EDFScheduler extends PriorityScheduler {

    private static int handlerPriority= PriorityScheduler.instance().getNormPriority()+4;
    // è la priorità riservata agli handler: è la più alta del sistema
    private static int releasePriority= PriorityScheduler.instance().getNormPriority()+3;
    // è la priorità di un job appena fa una release, è minore a handlerPriority
    private static int excecutingPriority= PriorityScheduler.instance().getNormPriority()+2;
    // è la priorità del job in esecuzione, è minore rispetto a quella di release.
    private static int readyPriority= PriorityScheduler.instance().getNormPriority()+1;
    // è la priorità dei processi  pronti per l'esecuzione. E' la più bassa del sistema

    private static RealtimeThread excecutingThread=null;
    // è il riferimento al thread correntemente in esecuzione può essere null se il sistema è idle.
    private static TreeMap<AbsoluteTime,RealtimeThread> readyQueue = new TreeMap<AbsoluteTime,RealtimeThread>();


     // la coda dei processi pronti, può essere vuota nel caso di sistema idle, oppure se c'è solo un thread in esecuzione.

    public static EDFScheduler instance=null;

     public EDFScheduler() {
         PriorityScheduler.instance();
       
    }



    /**
     * metodo chiamato da ogni thread all'inizio del job:
     * provvede ad inserirlo nella coda dei processi pronti,
     * o a metterlo in esecuzione se è quello con deadline più imminente
     */

    /* calcola la deadline assoluta del thread ed imposta con questa i suoi EDFSchedulingParameters
     * se nessun thread è in esecuzione o se il thread in esecuzionh ha una deadline meno imminente,
     * allora metto direttamente in esecuzione il thread corrente rimettendo in coda quello attualmente
     * in esecuzione(naturalmente cambio le priorità di entrambi).
     * In caso contrario imposto la priorità del thread corrente e lo inserisco nella coda dei processi pronti.
     *
     */
    public static synchronized void onJobRelease(RealtimeThread currentThread){

        // calcolo prossima deadline
        RelativeTime relativeDeadline = currentThread.getReleaseParameters().getDeadline();
        AbsoluteTime currentThreadNextDeadline = Clock.getRealtimeClock().getTime().add(relativeDeadline);
        // imposto gli EDFParameters
        ((EDFSchedulingParameters)currentThread.getSchedulingParameters()).setNextDeadline(currentThreadNextDeadline);
        // se non c'è nessun altro thread in esecuzione metto il thread corrente in esecuzione
        if(excecutingThread==null){
            currentThread.setPriority(EDFScheduler.getExcecutingPriority());
            excecutingThread = currentThread;
        }
        else {
            AbsoluteTime excecutingThreadNextDeadline = ((EDFSchedulingParameters)excecutingThread.getSchedulingParameters()).getNextDeadline();
            // se la deadline del thread corrente è più immintente di quello in esecuzione
            // rimetto quest'ultimo nella coda e quello corrente in esecuzione.
            if(currentThreadNextDeadline.compareTo(excecutingThreadNextDeadline)<0){
                excecutingThread.setPriority(EDFScheduler.getReadyPriority());
                readyQueue.put(excecutingThreadNextDeadline, excecutingThread);
                currentThread.setPriority(EDFScheduler.getExcecutingPriority());
                excecutingThread = currentThread;
                
            }
                // in caso contrario metto il thread corrente nella coda
                else{
                    currentThread.setPriority(EDFScheduler.getReadyPriority());
                    readyQueue.put(currentThreadNextDeadline, currentThread);

                }
        }



        
    }


    /**
     * metodo che deve eseguire ogni thread al temine del job.
     * Mette in esecuzione il primo processo della coda, se questa non è vuota
     * imposta la priorità del thread che ha appena terminato al livello di release
     * in modo che al suo risveglio possa esercitare preemption su tutti gli altri,
     * ed eseguire tempestivamente il metodo onJobRelease
     * @param currentThread il thread che ha appena teminato l'esecuzione del suo job
     */
    public static synchronized void onJobEnd (RealtimeThread currentThread){

        currentThread.setPriority(EDFScheduler.getReleasePriority());
        if(!readyQueue.isEmpty()){
            excecutingThread=readyQueue.get(readyQueue.firstKey());
            readyQueue.remove(readyQueue.firstKey());
            excecutingThread.setPriority(EDFScheduler.getExcecutingPriority());
        }
        else
            excecutingThread=null;

    }


public static EDFScheduler instance(){
    if (instance == null )
        instance = new EDFScheduler();
    return instance;


}








    public static int getExcecutingPriority() {
        return excecutingPriority;
    }

    protected static void setExcecutingPriority(int excecutingPriority) {
        EDFScheduler.excecutingPriority = excecutingPriority;
    }

    protected static RealtimeThread getExcecutingThread() {
        return excecutingThread;
    }

    protected static void setExcecutingThread(RealtimeThread excecutingThread) {
        EDFScheduler.excecutingThread = excecutingThread;
    }

    public static int getHandlerPriority() {
        return handlerPriority;
    }

    protected static void setHandlerPriority(int handlerPriority) {
        EDFScheduler.handlerPriority = handlerPriority;
    }

    public static int getReadyPriority() {
        return readyPriority;
    }

    protected static void setReadyPriority(int readyPriority) {
        EDFScheduler.readyPriority = readyPriority;
    }

    protected static TreeMap<AbsoluteTime, RealtimeThread> getReadyQueue() {
        return readyQueue;
    }

    protected static void setReadyQueue(TreeMap<AbsoluteTime, RealtimeThread> readyQueue) {
        EDFScheduler.readyQueue = readyQueue;
    }

    public static int getReleasePriority() {
        return releasePriority;
    }

    protected static void setReleasePriority(int releasePriority) {
        EDFScheduler.releasePriority = releasePriority;
    }




   


     


     
}
