/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeLibrary.schedulables;

/**
 *
 * @author Marco Nanni
 * E' la classe base di tutti i gestori a soglia, nei quali
 * Quando un job sfora consecutivamente la deadline per un certo numero di volte
 * si cambia politica di gestione
 */
public class ThresholdPolicyHandler extends DeadlineMissedHandler{
    
    private int threshold;
    private int deadlineCount;

    public ThresholdPolicyHandler(PeriodicThread controlledThread, int priority, String name, int threshold) {
        super(controlledThread, priority, name);
        this.threshold = threshold;
        this.deadlineCount =0;
    }

    public ThresholdPolicyHandler() {
        super();
    }



    /**
     * 
     * @return un valore che indica dopo quante violazioni consecutive
     * di deadline si deve cambiare strategia.
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     *
     * @param threshold un valore che indica dopo quante violazioni consecutive
     * di deadline si deve cambiare strategia.
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    /**
     *
     * @return il contatore che tiene traccia di quante volte la deadline
     * è stata violata consecutivamente
     */
    public int getDeadlineCount() {
        return deadlineCount;
    }

    /**
     *
     * @param deadlineCount il contatore che tiene traccia di quante volte la deadline
     * è stata violata consecutivamente
     */
    public void setDeadlineCount(int deadlineCount) {
        this.deadlineCount = deadlineCount;
    }

    /**
     * incrementa il contatore che tiene traccia di quante volte la deadline
     * è stata violata consecutivamente
     */
    public void incrementDeadlineCount (){
        this.deadlineCount++;
    }

     /**
     * decrementa il contatore che tiene traccia di quante volte la deadline
     * è stata violata consecutivamente
     */
    public void decrementDeadlineCount (){
        this.deadlineCount++;
    }
    


    








}
