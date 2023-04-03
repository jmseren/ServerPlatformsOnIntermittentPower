package edu.pitt.cs.faas.loadbalancer;

import java.util.ArrayList;

import edu.pitt.cs.faas.server.DataCenter;
import edu.pitt.cs.faas.workload.Workload;

public interface LoadBalancer {
    public void invoke(ArrayList<DataCenter> nodes, Workload w);
}
