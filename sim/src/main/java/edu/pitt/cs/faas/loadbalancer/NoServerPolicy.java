package edu.pitt.cs.faas.loadbalancer;

import java.util.ArrayList;

import edu.pitt.cs.faas.server.DataCenter;
import edu.pitt.cs.faas.workload.Workload;

public class NoServerPolicy implements Policy {
    public boolean defer(Workload w, ArrayList<DataCenter> nodes){
        // A simple policy that ensures workloads do not get executed if there are no servers available
        return nodes.isEmpty();
    }
}
