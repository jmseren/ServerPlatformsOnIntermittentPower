package edu.pitt.cs.faas.loadbalancer;

import java.util.ArrayList;

import edu.pitt.cs.faas.server.DataCenter;
import edu.pitt.cs.faas.workload.Workload;

public interface Policy {
    // Decide if a workload should be added to the queue or executed immediately
    public boolean defer(Workload w, ArrayList<DataCenter> nodes);
}
