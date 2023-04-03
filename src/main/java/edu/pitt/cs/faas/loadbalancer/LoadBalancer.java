package edu.pitt.cs.faas;

import java.util.ArrayList;

public interface LoadBalancer {
    public void invoke(ArrayList<DataCenter> nodes, Workload w);
}
