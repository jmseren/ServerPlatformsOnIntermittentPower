package edu.pitt.cs.faas;

public class LeanLoadBalancer { 
    // The current implementation OpenWhisk uses to load balance workloads
    
    public void invoke(ArrayList<DataCenter> nodes, Workload w){
        int numNodes = nodes.size();
        ndoes.get(w.hashCode() % numNodes).invoke(w);
    }
    
}
