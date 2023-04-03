package edu.pitt.cs.faas.workload;

public class Workload {
    private static final float CPU_POWER = 6.5f; // Watts at 100% CPU utilization

    private int time; // Time in seconds for workload to complete
    private float cpu; // CPU utilization

    public Workload(int time, float cpu, WorkloadTrace trace){
        this.time = time;
        this.cpu = cpu;
    }

    public int getTime(){
        return time;
    }

    public float getPower(){
        return (int)((CPU_POWER * cpu) * time);
    }

    public int getHash() {
        return this.hashCode();
    }
}
