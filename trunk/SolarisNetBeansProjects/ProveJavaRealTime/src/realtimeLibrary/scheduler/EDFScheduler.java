/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.scheduler;

import java.util.PriorityQueue;
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

    private int handlerPriority; // è la priorità riservata agli handler: è la più alta del sistema
    private int releasePriority; // è la priorità di un job appena fa una release, è minore a handlerPriority
    private int excecutingPriority; // è la priorità del job in esecuzione, è minore rispetto a quella di release.
    private int readyPriority; // è la priorità dei processi  pronti per l'esecuzione. E' la più bassa del sistema

    private RealtimeThread excecutingThread;  // è il riferimento al thread correntemente in esecuzione
                                               // può essere null se il sistema è idle.
    private PriorityQueue readyQueue; // la coda dei processi pronti, può essere vuota nel caso di
                                    // sistema idle, oppure se c'è solo un thread in esecuzione.

    

}
