package edu.pitt.cs.faas.loadbalancer;

import java.util.ArrayList;

import edu.pitt.cs.faas.server.DataCenter;
import edu.pitt.cs.faas.workload.Workload;

public class GreedyBalancer implements LoadBalancer {
    
    public DataCenter balance(ArrayList<DataCenter> nodes, Workload w){
        // Choose the data center with the highest energy consumption
        DataCenter max = nodes.get(0);
        for(DataCenter dc : nodes){
            if(dc.getPower() > max.getPower()){
                max = dc;
            }
        }
        return max;
    }


}
