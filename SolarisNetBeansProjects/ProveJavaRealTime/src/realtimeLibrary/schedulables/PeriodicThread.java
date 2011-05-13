/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

import javax.realtime.AsyncEventHandler;
import javax.realtime.PeriodicParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import realtimeLibrary.busyWait.BusyWait;
import realtimeLibrary.logging.SchedulableLog;

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

    @Override
    /*
     * corpo dell'esecuzione del thread, per ogni ciclo di esecuzione (job)
     * se il valore di skipNumber è pari a zero (quindi non ci sono release pendenti
     * con politica skip) scrivo l'inizio e la fine del job sul log ed eseguo una
     * busy wait di durata pari ad excecutionTime.
     * Se il parametro skipNumber è maggiore di zero, lo decremento e non faccio altro.
     */
    public void run() {

        BusyWait busyWait = BusyWait.getInstance();


    for (int i =0; i< this.getNumberOfIterations(); i++){
       

        if (this.getSkipNumber()==0){
            this.getLog().writeStartJob();
           this.doJob();


           this.getLog().writeEndJob();

        }// fine if skipnumber=0
        else{
            this.decrementSkipNumber();
            this.getLog().writeSkippedJob();

           }
        
        super.waitForNextPeriod();

    }// fine ciclo



       

    }

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
    public int getSkipNumber() {
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



    

}
