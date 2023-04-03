package edu.pitt.cs.faas.server;

import java.util.*;

import edu.pitt.cs.faas.battery.Battery;
import edu.pitt.cs.faas.workload.Workload;

public class DataCenter {
    private SolarProfile solarProfile;
    private int time;
    private Queue<Workload> queue = new LinkedList<Workload>();

    private final int MAX_CONCURRENT_WORKLOADS = 1;

    private Workload[] working = new Workload[MAX_CONCURRENT_WORKLOADS];
    private LinkedList<Workload> history = new LinkedList<Workload>();



    private Battery batt;
    


    public DataCenter(SolarProfile solarProfile) {
        this.solarProfile = solarProfile;
        this.time = 0;

        batt = new Battery(1000);
    }

    public int getPower() {
        return batt.getLevel();
    }

    public int invoke(Workload w){
        queue.add(w);
        return 0;
    }

    public int getTime(){
        return time;
    }

    public void timeStep(){
        time++;
        
    }


    public float getPowerDraw(){
        float power = 0;
        for(Workload w : working){
            if(w != null){
                power += w.getPower();
            }
        }
        return power;
    }
}