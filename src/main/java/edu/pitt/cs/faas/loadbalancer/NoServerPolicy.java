package edu.pitt.cs.faas.loadbalancer;

import java.util.ArrayList;

import edu.pitt.cs.faas.server.DataCenter;
import edu.pitt.cs.faas.workload.Workload;

public class NoServerPolicy implements Policy {
    public boolean defer(Workload w, ArrayList<DataCenter> nodes){
        return nodes.isEmpty();
    }
}
