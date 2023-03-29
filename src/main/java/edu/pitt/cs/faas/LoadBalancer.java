package edu.pitt.cs.faas;

public interface LoadBalancer {
    public void invoke(ArrayList<DataCenter> nodes, Workload w);
}
