package edu.pitt.cs.faas;

public class Workload {
    private int time; // Time in seconds for workload to complete
    private int power; // Power in watts for workload to complete

    public Workload(int time, int power){
        this.time = time;
        this.power = power;
    }

    public int getTime(){
        return time;
    }

    public int getPower(){
        return power;
    }
}
