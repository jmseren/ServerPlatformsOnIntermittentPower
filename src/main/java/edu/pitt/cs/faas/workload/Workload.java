package edu.pitt.cs.faas.workload;

public class Workload {
    private static final float CPU_POWER = 6.5f; // Watts at 100% CPU utilization

    private int coldTime;
    private int time; // Time in seconds
    private float cpu; // CPU utilization




    public Workload(int time, int coldTime, float cpu, WorkloadTrace trace){
        this.time = time;
        this.coldTime = coldTime;
        this.cpu = cpu;
    }

    public boolean work(int time, boolean cold){
        return (cold ? coldTime : this.time) < time;
    }


    public int getTime(){
        return time;
    }

    public float getPower(){
        return CPU_POWER * cpu;
    }

    public int getHash() {
        return this.hashCode();
    }
}
