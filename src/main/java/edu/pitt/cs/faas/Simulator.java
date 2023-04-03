package edu.pitt.cs.faas;

import java.util.*;
import java.io.*;

import edu.pitt.cs.faas.loadbalancer.LeanLoadBalancer;
import edu.pitt.cs.faas.loadbalancer.LoadBalancer;
import edu.pitt.cs.faas.server.DataCenter;
import edu.pitt.cs.faas.server.SolarProfile;

public class Simulator {
    private final int NUM_NODES = 9;

    private ArrayList<DataCenter> nodes = new ArrayList<DataCenter>();

    private int time = 0;

    public Simulator(LoadBalancer lb){
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


    }

    public static void main(String[] args){
        Simulator sim = new Simulator(new LeanLoadBalancer());
        sim.run();
    }


    public void run(){
    }
}
