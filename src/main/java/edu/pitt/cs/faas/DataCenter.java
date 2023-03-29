package edu.pitt.cs.faas;

public class DataCenter implements Runnable {
    private SolarProfile solarProfile;
    private int power;
    private int time;
    private ArrayList<Workload> queue = new ArrayList<Workload>();

    private final int MAX_CONCURRENT_WORKLOADS = 1;

    private Workload[] working = new Workload[MAX_CONCURRENT_WORKLOADS];
    private Workload[] history = new Workload[MAX_CONCURRENT_WORKLOADS];
    


    public DataCenter(SolarProfile solarProfile) {
        this.solarProfile = solarProfile;
        this.power = 100;
        this.time = 0;
    }

    public int getPower() {
        return power;
    }

    public int invoke(Workload w){
        queue.add(w);
        return 0;
    }

    public synchronized int getTime(){
        return time;
    }

    public synchronized void timeStep(){
        time++;
    }


    public void run() {

    }
}