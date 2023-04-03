package edu.pitt.cs.faas;

import java.util.ArrayList;

public class Simulator {
    private final int NUM_NODES = 10;

    private ArrayList<DataCenter> nodes = new ArrayList<DataCenter>();

    private int time = 0;

    public Simulator(LoadBalancer lb){
        for(int i = 0; i < NUM_NODES; i++){
            nodes.add(new DataCenter(new SolarProfile("solar.csv")));
        }

        


    }

    public static void main(String[] args){
        Simulator sim = new Simulator(new LeanLoadBalancer());
        sim.run();
    }


    public void run(){
        SolarProfile solarProfile = new SolarProfile("solar.csv");
        System.out.println(solarProfile.toString());
    }
}
