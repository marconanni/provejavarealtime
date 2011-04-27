/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.logging;

import java.util.Hashtable;

/**
 *
 * @author Marco Nanni
 * Classe che espone la correlazione tra codice e descrizione degli eveneti
 * che vengono trascritti nei log
 */
public class CodeDescription extends Hashtable<String,String> {

    public CodeDescription() {
        super();
        this.put("10", "creazione oggetto schedulabile");
        this.put("20", "inizio job");
        this.put("30", "termine job");
        this.put("40", "occupazione risorsa");
        this.put("50", "rilascio risorsa");
        this.put("60", "deadline non rispettata");

    }

    

}
