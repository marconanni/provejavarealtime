/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prova1;

import javax.realtime.*;

/**
 *
 * @author root
 */
public class WorkerThread extends RealtimeThread {
    private int executionTime;

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public void run() {
       AbsoluteTime startTime= new AbsoluteTime(Clock.getRealtimeClock().getTime());
       AbsoluteTime currentTime = new AbsoluteTime(startTime);
       AbsoluteTime endTime = startTime.add(new RelativeTime(executionTime,0));
       int k=3;
       System.out.println("worker thread, inizio l'esecuzione");
       while ((currentTime.compareTo(endTime))<0){
           k++;
           k--;
           currentTime.set(Clock.getRealtimeClock().getTime());
        }
       RelativeTime tempoEffettivo = currentTime.subtract(startTime);
       System.out.println("worker thread esecuzione terminata  effettivamente dopo " + tempoEffettivo);
       System.out.println("il valore teorico dovrebbe essere " + endTime.subtract(startTime));
       System.out.println("StartTime: "+ startTime);
       System.out.println("currentTime: "+ currentTime);
       System.out.println("endTime" + endTime);


    }

}
