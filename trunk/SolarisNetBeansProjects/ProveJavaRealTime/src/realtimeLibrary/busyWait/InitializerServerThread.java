/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.busyWait;

import javax.realtime.RealtimeThread;

/**
 *
 * @author Marco Nanni
 *
 * Classe sfruttata durante l'inizializzazione,
 * non fa altro che eseguire un ciclo  e contare il numero di iterazioni
 * fincè non se ne forza la terminazione dall'esterno.
 */
public class InitializerServerThread extends RealtimeThread{

    private long iterationCount=0;
    private boolean continueExcecution = true;

    /**
     *
     * @return il numero di iterazioni eseguite
     */
    public long getIterationCount() {
        return iterationCount;
    }

    /**
     * corpo dell'esecuzione della classe: a parte del codice superfluo,
     * magari da migliorare introducendo operazioni più pesanti, incrementa
     * ad ogni ciclo il numero di iterazioni eseguite
     */
    @Override
    public void run() {
        this.setName("initializerServerThread");
        this.iterationCount=0;
        int k =3;
        while(continueExcecution){
            k++;
            k--;
            iterationCount++;
        }
    }

    public boolean isContinueExcecution() {
        return continueExcecution;
    }

    public void setContinueExcecution(boolean continueExcecution) {
        this.continueExcecution = continueExcecution;
    }

    



}
