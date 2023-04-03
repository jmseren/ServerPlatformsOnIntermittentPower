package loadbalancer;

import java.util.ArrayList;

import edu.pitt.cs.faas.server.DataCenter;
import edu.pitt.cs.faas.workload.Workload;

public class LeanLoadBalancer implements LoadBalancer { 
    // The current implementation OpenWhisk uses to load balance workloads
    
    public void invoke(ArrayList<DataCenter> nodes, Workload w){
        int numNodes = nodes.size();
        nodes.get(w.getHash() % numNodes).invoke(w);
    }
    
}
