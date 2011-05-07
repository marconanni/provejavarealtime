
package realtimeLibrary.logging;

import java.util.*;
import javax.realtime.*;


/**
 *
 * @author Marco Nanni
 * classe che contiene una serie di funzionalità per estrarre informazione
 * da un'insieme di log, aggregandoli ed elaborandoli
 */
public class Util {



    /**
     * restituisce la stampa di degli eventi presenti nei vari log ordinati
     * cronologicamente
     * @param logList la lista contentente i log da unire
     * @return una stringa che contiene tutti gli eventi presenti nei vari
     * log ordinati cronologicamente,
     * ad esempio:
     * Thread3;40;occupazione risorsa;(1303895591750 ms, 455084 ns)
     *  Thread1;20;inizio job;(1303895593750 ms, 514267 ns)
     *  Thread3;50;rilascio risorsa;(1303895595750 ms, 86330 ns)
     */
    public static String simpleMerge(List<Log> logList){
        
        //creo una treemap, ossia una mappa ordinata, cos posso inserire brutalmente
        // tutti gli eventi e ritrovarmeli già ordinati.
        TreeMap <AbsoluteTime,LogLine> map = new TreeMap<AbsoluteTime, LogLine>();
        //estraggo tutti gli eventi di ogni log e li inserisco nella mappa.
        for(int k=0 ; k< logList.size(); k++){
            List<LogLine> currentLogLines = logList.get(k).getLines();
            for(int j=0; j<currentLogLines.size(); j++)
                map.put(currentLogLines.get(j).getTime(), currentLogLines.get(j));            
        }
        // ora stampo tutte le linee di log nella stringa
        String result = "";
        AbsoluteTime firstKey;
        LogLine firstLine;
        while(!map.isEmpty()){
            firstKey= map.firstKey();
            firstLine = map.get(firstKey);
            result = result + firstLine.toString() + "\n";
            map.remove(firstKey);
        }       

        return result;
    }

    /**
     * restituisce la stampa degli eventi presenti nei vari log ordinati cornologicamente.
     * vengono mostrate le differenze relative di tempo rispetto al tempo zero
     * @param logList la lista contentente i log da unire
     * @param zeroTime un absolute time che esprime il tempo zero per tutti i log
     * @return una stringa che contiene tutti gli eventi presenti nei vari
     * log ordinati cronologicamente,
     * ad esempio:
     * Thread3;40;occupazione risorsa;(7000 ms, 406314 ns)
     * Thread1;20;inizio job;(8000 ms, 264337 ns)
     * Thread3;50;rilascio risorsa;(9000 ms, 433888 ns)
     */
    public static String relativeMerge(List<Log> logList, AbsoluteTime zeroTime){
         //creo una treemap, ossia una mappa ordinata, cos posso inserire brutalmente
        // tutti gli eventi e ritrovarmeli già ordinati.
        TreeMap <RelativeTime,LogLine> map = new TreeMap<RelativeTime, LogLine>();
        //estraggo tutti gli eventi di ogni log e li inserisco nella mappa.
        for(int k=0 ; k< logList.size(); k++){
            
            if (!logList.get(k).getLines().isEmpty()){ // il log potrebbe anche non avere alcun evento registrayo
                List<LogLine> currentLogLines = logList.get(k).getLines();
                for(int j=0; j<currentLogLines.size(); j++)
                    map.put(currentLogLines.get(j).getTime().subtract(zeroTime), currentLogLines.get(j));
            }
         }
        // ora stampo tutte le linee di log nella stringa
        String result = "";
        RelativeTime firstKey;
        LogLine firstLine;
        while(!map.isEmpty()){
            firstKey= map.firstKey();
            firstLine = map.get(firstKey);
            result = result + firstLine.getThreadName()+";"+firstLine.getCode()+";"+ firstLine.getDescription() + ";"+firstKey + "\n";
            map.remove(firstKey);
        }

        return result;
    }




}
