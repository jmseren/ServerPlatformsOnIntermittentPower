package edu.pitt.cs.faas.server;

import java.util.*;

import edu.pitt.cs.faas.battery.*;
import edu.pitt.cs.faas.workload.Workload;

public class DataCenter {

    private boolean online = true;

    private final int MAX_HISTORY = 10;
    private final int MAX_CONCURRENT_WORKLOADS = 1;
    
    private SolarProfile solarProfile;
    private int time;
    private Queue<Workload> queue = new LinkedList<Workload>();
    private Workload[] working = new Workload[MAX_CONCURRENT_WORKLOADS];
    private int[] workingTime = new int[MAX_CONCURRENT_WORKLOADS];
    private boolean[] cold = new boolean[MAX_CONCURRENT_WORKLOADS];

    private float availablePower = 0; // Power in Watt-Sec available at every time step

    private static int count = 0;
    private int id = count++;

    private LinkedList<Workload> history = new LinkedList<Workload>();




    private Battery batt;
    


    public DataCenter(SolarProfile solarProfile) {
        this.solarProfile = solarProfile;
        this.time = 0;

        batt = new Battery(20000);
    }

    public float getPower() {
        return batt.getLevel();
    }

    public int invoke(Workload w){
        int slot = free();
        if(slot == -1){
            queue.add(w);
        }else{
            working[slot] = w;
        }

        return 0;
    }

    public int getTime(){
        return time;
    }


    private void evict(int i){
        if(working[i] == null) return;
        history.add(working[i]);
        working[i] = null;
        workingTime[i] = 0;
    }

    private boolean isCold(Workload w){
        return history.contains(w);
    }

    private void add(Workload w, int slot){
        // Add a workload to the current working set
        working[slot] = w;
        workingTime[slot] = 0;
        cold[slot] = isCold(w);

        // Update the history
        if(!isCold(w)){
            history.remove(w);
        }
        history.addFirst(w);
        if(history.size() > MAX_HISTORY){
            history.removeLast();
        }
        
        
    }

    public void tick(){
        time++;
        // Update the available power
        if(time % solarProfile.getGranularity() == 0){
            availablePower = solarProfile.getPower();
        }
        if(!online){
            try{
                batt.charge(availablePower);
                if(batt.getLevel() > batt.getCapacity() * .10){
                    online = true;
                    System.out.println("[BATTERY] Server " + id + " is now online");
                }     
            }catch(Exception e){
            }
            return;
        }

        // Update the battery
        float tempEnergy = availablePower;
        for(Workload w : working){
            if(w != null){
                tempEnergy -= w.getPower();
            }
        }
        
        try{
            batt.charge(tempEnergy);
        }catch(Exception e){
            System.out.println("[BATTERY] Server " + id + " is now offline");
            // Give all the workloads back to the controller
            online = false;
        }

        // Work on the workloads
        for(int i = 0; i < working.length; i++){
            if(working[i] == null) continue;
            workingTime[i]++;
            if(working[i].work(workingTime[i], cold[i])){
                System.out.println("[COMPLETED] " + working[i] + " on Server " + this.id);
                evict(i);
            }
        }

        // Add any waiting workloads
        do {
            int slot = free();
            if(slot == -1) break;
            if(queue.isEmpty()) break;
            add(queue.remove(), slot);
        } while(true);

        
    }


    public float getEnergy(){ 
        // A rough estimate of the energy in Watt-Sec that the data center is drawing
        float energy = 0;
        for(Workload w : working){
            if(w != null){
                energy += w.getPower();
            }
        }
        return energy;
    }

    private int free(){
    
        // Find the first free slot in the working array
        for(int i = 0; i < working.length; i++){
            if(working[i] == null){
                return i;
            }
        }
        return -1;
    }

    public boolean isOnline(){
        return online;
    }

    public String toString(){
        return "[DataCenter " + id + ", " + ((batt.getLevel() / batt.getCapacity()) * 100) + "%]";
    }
}