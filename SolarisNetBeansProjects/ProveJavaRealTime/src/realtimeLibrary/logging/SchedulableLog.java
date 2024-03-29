/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.logging;

/**
 *
 * @author Marco
 * classe che rappresenta il log collegato a tutte le classi di oggetti schedulabili
 */
public class SchedulableLog extends Log{

    public SchedulableLog(int dimension) {
        super(dimension);
    }

    public SchedulableLog() {
        super();
    }



    /**
     * scrive sul log l'evento realtivo alla creazione dell'oggetto schedulabile
     * 
     */
    public void writeJobCreation() {
       
            super.writeEvent("10");
       
    }

    /**
     * scrive sul log l'evento realtivo all'inizio dell'esecuzione
     * del job dell'oggetto schedulabile
     *
     */
     public void writeStartJob() {
      
            super.writeEvent("20");
       
    }
     /**
     * scrive sul log l'evento realtivo alla fine
      * dell'esecuzione del job dell'oggetto schedulabile
     *
     */
     public void writeEndJob(){
      
            super.writeEvent("30");
       
    }

     /**
     * scrive sul log l'evento realtivo alla violazione dela deadline da parte di un thread
     *
     */
     public void writeDeadlineMissed(String misserName){

            super.writeEvent(misserName,"60");

    }

     /**
      * scrive sul log l'evento relativo al fato che un job
      * che di default verrebbe immediatamente accodato per recuperare
      * da una missed deadline diun job precedente,
      * viene saltato perchè perchè vi usa la politica skip
      */
     public void writeSkippedJob(){
         super.writeEvent("70");
     }

     /**
      * scrive sul log l'evento relativo al fato che un job
      * che di default verrebbe immediatamente accodato per recuperare
      * da una missed deadline diun job precedente,
      * viene saltato perchè perchè vi usa la politica skip
      * @param threadName il nome del thread che ha skippato il job
      */
     public void writeSkippedJob (String threadName){
         super.writeEvent(threadName, "70");
     }

     /**
      * scrive sul log l'evento relativo al fatto che l'esecuzione
      * del job che ha superato la deadline è stata interrotta in
      * accordo alla politica stop.
      * @param threadName il nome del thread il cui job è stato interrotto.
      */
     public void writeInterruptedJob(String threadName){
         super.writeEvent(threadName, "80");
     }

     /**
      * scrive sul log l'evento relativo al fatto che si sta
      * iniziando un job di recupero
      * @param threadName il nome del thread che sta eseguendo il
      * job di recupero
      */
     public void writeStartPendingJob(String threadName){
         super.writeEvent(threadName, "90");

     }
     /**
      * scrive sul log l'evento relativo al fatto che si sta
      * iniziando un job di recupero
      */
     public void writeStartPendingJob(){
         super.writeEvent( "90");

     }

     /**
      * scrive sul log l'evento relativo al fatto che si sta
      * finendo l'esecuzione di un job di recupero
      * @param threadName il nome del thread che sta eseguendo il
      * job di recupero
      */
     public void  writeEndPendingJob (String threadName){
         super.writeEvent(threadName, "100");
     }

     /**
      * scrive sul log l'evento relativo al fatto che si sta
      * finendo l'esecuzione di un job di recupero
      */
     public void  writeEndPendingJob (){
         super.writeEvent( "100");
     }


}
