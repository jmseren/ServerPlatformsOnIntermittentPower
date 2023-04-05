package edu.pitt.cs.faas.loadbalancer;

import java.util.ArrayList;

import edu.pitt.cs.faas.server.DataCenter;
import edu.pitt.cs.faas.workload.Workload;

public class LeanLoadBalancer implements LoadBalancer { 
    // The current implementation OpenWhisk uses to load balance workloads
    
    public DataCenter balance(ArrayList<DataCenter> nodes, Workload w){
        int numNodes = nodes.size();
        return nodes.get(w.getHash() % numNodes);
    }
    
}
