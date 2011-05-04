/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;
import javax.realtime.RealtimeThread;


/**
 *
 * @author Marco Nanni
 * Thread che ha il compito di occupare stablmente una cpu,
 * si può usare in ambienti multiprocessore per facilitare
 * lo sviluppo di applicazioni che consentono di verificare
 * i meccanismi di scheduling di JavaReal Time.
 * una volta lanciato esegue finchè la proprietà
 * continueExecution è settata a false.
 * Per funzionare correttamente deve avere un priorità
 * speriore a quella degli altri thread realtime,
 * ma inferiore a quella del thread chefa da inizializzatore/metascheduler
 */
public class CpuDespotTrhread  extends RealtimeThread{

    private boolean continueExcecution = true;

    @Override
    public void run() {
        int k = 7;
        while(this.isContinueExcecution()){
            k=k+40;
            k=k-40;

        }
    }





    public boolean isContinueExcecution() {
        return continueExcecution;
    }

    public void setContinueExcecution(boolean continueExcecution) {
        this.continueExcecution = continueExcecution;
    }






}
