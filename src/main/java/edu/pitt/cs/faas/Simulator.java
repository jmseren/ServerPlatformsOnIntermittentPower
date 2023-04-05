package edu.pitt.cs.faas;

import java.util.*;
import java.io.*;

import edu.pitt.cs.faas.loadbalancer.*;
import edu.pitt.cs.faas.server.*;
import edu.pitt.cs.faas.workload.*;

public class Simulator {
    private final int TIME_STEP = 1; // Time step in seconds
    private final int SIMULATION_TIME = 86400; // Simulation time in seconds 
    private final int NUM_WORKLOADS = 500;
    private final int NUM_NODES = 9;

    private ArrayList<DataCenter> nodes = new ArrayList<DataCenter>();
    private ArrayList<Workload> workloads = new ArrayList<Workload>();

    private int time = 0;

    private LoadBalancer lb;

    public Simulator(LoadBalancer lb){
        this.lb = lb;

        // Get list of solar profiles in data directory
        // Create a node for each solar profile
        File dataDir = new File("data/");
        File[] solarProfiles = dataDir.listFiles();
        for(int i = 0; i < NUM_NODES; i++){
            SolarProfile sp = new SolarProfile(solarProfiles[i].getAbsolutePath());
            nodes.add(new DataCenter(sp));
        }

        for(DataCenter dc : nodes){
            System.out.println(dc);
        }

        try {
            WorkloadFactory.setWorkloadSource(new Scanner(new File("workloads/invocations_per_function_md.anon.d01.csv")));
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        for(int i = 0; i < NUM_WORKLOADS; i++){
            workloads.add(WorkloadFactory.getWorkload());
        }

    }

    public static void main(String[] args){
        Simulator sim = new Simulator(new GreedyBalancer());
        sim.run();
    }

    private ArrayList<DataCenter> availableNodes(){
        // Return a list of nodes that are online
        ArrayList<DataCenter> availableNodes = new ArrayList<DataCenter>();
        for(DataCenter dc : nodes){
            if(dc.isOnline()){
                availableNodes.add(dc);
            }
        }
        return availableNodes;
    }

    public void run(){
        while(time < SIMULATION_TIME){
            for (Workload w : workloads) {
                if (!w.shouldExecute(time))
                    continue;

                DataCenter dc = lb.balance(availableNodes(), w);

                dc.invoke(w);

                System.out.println("Time: " + time + "\t " + w + "\t" + dc);

            }
            for(DataCenter dc : nodes){
                dc.tick();
            }
            time++;
        }
        
    }
}
