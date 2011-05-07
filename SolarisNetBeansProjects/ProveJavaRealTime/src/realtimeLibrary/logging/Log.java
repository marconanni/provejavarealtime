/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.logging;

import java.io.StringWriter;
import java.util.List;
import java.util.Vector;
import javax.realtime.*;



/**
 *
 * @author Marco Nanni
 *
 *
 */
public class Log {

    protected static CodeDescription codeDescription = new CodeDescription();

    /**
     * 
     * @return la mappa che collega i codici degli eventi ad una loro descrizione
     */
    public static CodeDescription getCodeDescription() {
        return codeDescription;
    }

    protected StringWriter stringWriter ;

    protected StringWriter getStringWriter() {
        return stringWriter;
    }

    public Log() {

        stringWriter = new StringWriter(5000);
    }
    public Log(int dimension) {

        stringWriter = new StringWriter(dimension);
    }

   /**
    * metodo che scrive nello string writer una stringa del tipo:
    * nome del thread che sta chiamando il metodo;codiceEvento;millisecondiAttuali;nanosecondiAttuali
    * ad esempio: Thread1;20;234580485;234
    * @param eventCode: il codice dell'evento da scrivere nel log
    * @throws Exception
    */
    public void writeEvent(String eventCode) {
       
        StringWriter sw = this.getStringWriter();
        sw.write(Thread.currentThread().getName()+";");
        sw.write(eventCode+";");
        sw.write(Clock.getRealtimeClock().getTime().getMilliseconds()+";");
        sw.write(Clock.getRealtimeClock().getTime().getNanoseconds()+"\n");
        

    }

    /**
    * metodo che scrive nello string writer una stringa del tipo:
    * nome del thread ;codiceEvento;millisecondiAttuali;nanosecondiAttuali
    * ad esempio: Thread1;20;234580485;234
     * @param threadName : il nome del thread che verrà visualizzato come autore dell'evento
    * @param eventCode: il codice dell'evento da scrivere nel log
    * @throws Exception
    */
    public void writeEvent(String threadName,String eventCode) {
        
        StringWriter sw = this.getStringWriter();
        sw.write(threadName+";");
        sw.write(eventCode+";");
        sw.write(Clock.getRealtimeClock().getTime().getMilliseconds()+";");
        sw.write(Clock.getRealtimeClock().getTime().getNanoseconds()+"\n");


    }
    
    /**
    * metodo che scrive nello string writer una stringa del tipo:
    * nome del thread ;codiceEvento;millisecondi;nanosecondi
    * ad esempio: Thread1;20;234580485;234
     * @param threadName : il nome del thread che verrà visualizzato come autore dell'evento
    * @param eventCode: il codice dell'evento da scrivere nel log
     * @param  eventTime: il tempo al quale si vuole che l'evento venga registrato
    * @throws Exception
    */
    public void writeEvent(String threadName,String eventCode,AbsoluteTime eventTime) {
        
        StringWriter sw = this.getStringWriter();
        sw.write(threadName+";");
        sw.write(eventCode+";");
        sw.write(eventTime.getMilliseconds()+";");
        sw.write(eventTime.getNanoseconds()+"\n");


    }






    @Override
    public String toString() {
        return this.getStringWriter().toString();
    }

    /**
     *
     * @return un vettore di LogLine, con un elemento per ogni evento registrato
     * qualora nel log non ci sono registrati eventi viene restituito
     * un vector vuoto
     */
    public List<LogLine> getLines(){

        Vector<LogLine> result = new Vector<LogLine>();
        // se il buffer è vuoto restituisco un vector vuoto
        if (this.getStringWriter().toString().equals(""))
            return result;
        String [] lines = (this.getStringWriter().toString()).split("\n");
        
        for(int k =0; k<lines.length;k++){

            // le mie lines sono nel formato Thread1;10;1303397184558;141674
            
            // le posso dividere nelle quattro parti
            String [] parts = lines[k].split(";");
            String threadName = parts[0];
            String code = parts[1];
            
            AbsoluteTime eventTime = new AbsoluteTime(Long.parseLong(parts[2]), Integer.parseInt(parts[3]));
            result.add(new LogLine(code, eventTime, threadName));


        }

        return result;


    }




}
