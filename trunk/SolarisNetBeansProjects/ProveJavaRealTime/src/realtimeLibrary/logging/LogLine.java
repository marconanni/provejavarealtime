/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.logging;

import javax.realtime.*;


/**
 *
 * @author Marco Nanni
 *
 * classe che rappresenta la linea di un file di log
 */

public class LogLine {

    protected String code;
    protected AbsoluteTime time;
    protected String threadName;

    public LogLine(String code, AbsoluteTime time, String threadName) {
        this.code = code;
        this.time = time;
        this.threadName = threadName;
    }


    




    /**
     * 
     * @return il codice dell'evento che ha generato la creazione della questa riga
     * di log
     */
    public String getCode() {
        return code;
    }

     protected void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return la descrizione dell'evento che ha generato la creazione
     * della questa riga di log
     */
     public String getDescription() {
        return Log.getCodeDescription().get(this.getCode());
    }

     

     /**
      *
      * @return il tempo di sistema in cui si è verificato l'evento
      */
    public AbsoluteTime getTime() {
        return time;
    }

    protected void setTime(AbsoluteTime time) {
        this.time = time;
    }

    /**
     *
     * @return il nome del thread che ha generato la linea di log: puo èssere la stringa vuota
     */
    public String getThreadName() {
        return threadName;
    }

    protected void setThreadName(String threadName) {
        this.threadName = threadName;
    }


    /**
     *
     * @return una striga del tipo Thread che ha generato l'vento;codiceEvento;descrizioneEvento;(tempo evento)
     * ad esempio Thread1;10;reazione oggetto schedulabile;(1303399792841 ms, 283054 ns)
     */
    @Override
    public String toString() {
        return (this.getThreadName()+";"+this.getCode()+";"+this.getDescription()+";"+this.getTime());
    }













}
