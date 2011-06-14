/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.realtime.AsyncEventHandler;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import javax.realtime.Scheduler;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.logging.SchedulableLog;
import realtimeLibrary.scheduler.EDFScheduler;
import realtimeLibrary.scheduler.EDFSchedulingParameters;

/**
 *
 * @author Marco Nanni
 * classe che modella un generico thread periodico,
 * giusto un abbozzo
 */
public class PeriodicThread extends RealtimeThread {

    private long excecutionTime; // il tempo di esecuzione effettivo di ogni job
    private SchedulableLog log; // il log del thread
    private int numberOfIterations;// il numero di cicli che il thread deve eseguire
    private int skipNumber =0; // valore utilizzato in caso di politica SKIP di gestione dei sovraccarichi: indica quanti cicli bisogna saltare: viene settato dal deadlineMissedHandler
    private int currentIteration; // valore che indica quale è l'iterazione corrente del thread (quale job sta eseguendo)

    public PeriodicThread(String name,long excecutionTime, int numberOfIterations, int priority, RelativeTime period,RelativeTime startTime, RelativeTime deadline, AsyncEventHandler deadlineHandler) {

        super();
        this.excecutionTime = excecutionTime;
        this.numberOfIterations = numberOfIterations;
        super.setPriority(priority);
        this.setReleaseParameters(new PeriodicParameters(startTime, period, null, deadline, null, deadlineHandler) );
        log = new SchedulableLog();
        this.setName(name);
    }

    public PeriodicThread (){
        super();
        log = new SchedulableLog();

    }

    /**
     * Corpo centrale dell'esecuzione del thread.
     * dal momento che l'esecuzione cambia in base allo scheduler
     * si dirige la chiamata ai vari metodi privati: uno diverso
     * per ogni scheuduler supportato
     */
    @Override
    public void run(){
        try{
        Scheduler currentScheduler = super.getScheduler();
        if(currentScheduler instanceof EDFScheduler)
            this.run((EDFScheduler)currentScheduler);
        else if (currentScheduler instanceof PriorityScheduler)
            this.run((PriorityScheduler)currentScheduler);
        else
            this.run((Scheduler)currentScheduler);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * qualora lo scheduler non sia supportato il sistema chiama questo metodo,
     * dal momento che la classe scheduler è alla base della categoria.
     * A questo punto occorre lanciare un'eccezione per indicare che
     * qualcosa è andato storto
     * @param scheduler
     * @throws Exception
     */
    protected void run(Scheduler scheduler) throws Exception{
        throw new Exception("Unsupported scheduler: "+scheduler);
    }

   
    /*
     * corpo dell'esecuzione del thread nel caso lo scheduler sia PriorityScheduler,
     * quello di default. Per ogni ciclo di esecuzione (job)
     * se il valore di skipNumber è pari a zero (quindi non ci sono release pendenti
     * con politica skip) scrivo l'inizio e la fine del job sul log ed eseguo il metodo
     * doJob che contiene la businness logic dell'esecuzione del thread
     * Se il parametro skipNumber è maggiore di zero, devo eseguire una skip,
     * scrivo sul log che ho fatto la skip di un jobe decremento di uno il valore
     * skipNumber.
     */
    protected void run(PriorityScheduler priorityScheduler) {

       
        this.setCurrentIteration(0);


    for (int i =0; i< this.getNumberOfIterations(); i++){
        this.setCurrentIteration(i+1);//il contatore del for parte da zero
       

        if (this.getPendingReleases()==0){
            this.getLog().writeStartJob();
           this.doJob();


           this.getLog().writeEndJob();

        }// fine if skipnumber=0
        else{
           
            this.getLog().writeSkippedJob();
             this.decrementSkipNumber();

           }
        
        super.waitForNextPeriod();

    }// fine ciclo       

    }
    /**
     *
     * corpo dell'esecuzione del thread nel caso lo scheduler sia basato
     * sulla politica EDF. Per ogni ciclo di esecuzione (job)
     * se il valore di skipNumber è pari a zero (quindi non ci sono release pendenti
     * con politica skip) scrivo sul log la creazione del job, quindi chiamo il
     * metodo onJobRelease dello scheduler (che può essere bloccante nel caso
     * ci siano job con una deadline più imminente).Passata questa chiamata
     * viene registrato sul log che il job è entrato in esecuzione, viene
     * chiamato il metodo doJob che contiene la businnessLogic del thread. Alla
     * fine dell' esecuzione, oltre alla scrittura dell'evento sul log, viene eseguito
     * il metodo onJobEnd dello scheduler.
     *
     * Se il parametro skipNumber è maggiore di zero, devo eseguire una skip,
     * scrivo sul log che ho fatto la skip di un jobe decremento di uno il valore
     * skipNumber.
     *
     * NOTA: in questa versione, se è necessario eseguire una skip del job,
     * non vengono chiamati i due metodi dello scheduler, che porterebbero magari
     * ad accodare inutilmente il job. si è scelto di provvedere in questa maniera
     * per aumentare l'efficienza del sistema, a scapito di togliere un po' di controllo
     * allo scheduler.
     *
     * 
     * @param edfScheduler lo scheduler che attualmente sta gestendo il thread
     */
    protected void run(EDFScheduler edfScheduler){

        this.setCurrentIteration(0);

        this.setSchedulingParameters(new EDFSchedulingParameters());
           this.setPriority(EDFScheduler.getReleasePriority());


    for (int i =0; i< this.getNumberOfIterations(); i++){
        this.setCurrentIteration(i+1);//il contatore del for parte da zero


        if (this.getPendingReleases()==0){
            this.getLog().writeJobCreation();
            edfScheduler.onJobRelease(this);
            this.getLog().writeStartJob();

           this.doJob();

           this.getLog().writeEndJob();
           
           edfScheduler.onJobEnd(this);


        }// fine if skipnumber=0
        else{

            this.getLog().writeSkippedJob();
             this.decrementSkipNumber();

           }

        super.waitForNextPeriod();

    }// fine ciclo

    }

    /**
     * questo metodo contiene la businnes logic da eseguire ad ogni iterazione
     * (job). In questo caso si esegue una busy la cui lunghezza
     * dipende dal parametro excecutionTime.
     */
    protected void doJob() {
        BusyWait.getInstance().doJobFor(this.getExcecutionTime());
    }

    public void setDeadlineMissedHandler(AsyncEventHandler missHandler){
        PeriodicParameters currentParameters = (PeriodicParameters)this.getReleaseParameters();
        currentParameters.setDeadlineMissHandler(missHandler);
    }



    public long getExcecutionTime() {
        return excecutionTime;
    }

    public void setExcecutionTime(long excecutionTime) {
        this.excecutionTime = excecutionTime;
    }

    public SchedulableLog getLog() {
        return log;
    }

    public void setLog(SchedulableLog log) {
        this.log = log;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    /**
     *
     * @return il numero di esecuzioni che devono essere saltate
     */
    public int getPendingReleases() {
        return skipNumber;
    }

    /**
     *
     * @param skipNumber il numero di esecuzioni che devono essere saltate
     */
    public void setSkipNumber(int skipNumber) {
        this.skipNumber = skipNumber;
    }

    /**
     * incrementa  il numero di esecuzioni che devono essere saltate,
     * questo metodo viene usato soprattutto da un handler di deadlinemissed
     * con politica skip
     */
    public void incrementSkipNumber() {
        this.skipNumber++;
    }
    /**
     * decrementa  il numero di esecuzioni che devono essere saltate,
     */
    public void decrementSkipNumber() {
        this.skipNumber--;
    }

    /**
     *
     * @return l'iterazione corrente del thread (quale job sta eseguendo)
     */
    public int getCurrentIteration() {
        return currentIteration;
    }

    /**
     *
     * @param currentIteration l'iterazione corrente del thread (quale job sta eseguendo)
     */
    protected void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }





    

}
