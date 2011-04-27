/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova;

import java.util.StringTokenizer;

/**
 *
 * @author root
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String stringa = new String("10(1303396620724 ms, 3831 ns)\n 10(1303396620724 ms, 3831 ns)\n10(1303396620724 ms, 3831 ns)");

        StringTokenizer st1= new StringTokenizer(stringa, "\n");
        String [] righe =stringa.split("\n");

        int k =0;

        System.out.println("righe "+ righe[0]+"\n"+righe[1]+"\n"+righe[2]+"\n");
        // le mie righe sono nel formato 10(1303397184558 ms, 141674 ns) , devo ripulirle
        for (k=0;k<righe.length;k++){
            righe[k]= righe[k].trim();
            righe[k]=righe[k].replace(" ", "");
            righe[k]=righe[k].replace(")", "");
            righe[k]=righe[k].replace("ms", "");
            righe[k]=righe[k].replace("ns", "");
            righe[k]=righe[k].replace("(", ";");
            righe[k]=righe[k].replace(",", ";");
            // ora le righe sono nel formato 10;1303396620724;3831
            // le posso dividere nelle tre parti 
            System.out.println(righe[k]);
            String[] parti = righe[k].split(";");
           
            System.out.println("codice"+parti[0]);
            System.out.println("millis"+parti[1]);
            System.out.println("nanos"+parti[2]);

        }
       
    }

}
