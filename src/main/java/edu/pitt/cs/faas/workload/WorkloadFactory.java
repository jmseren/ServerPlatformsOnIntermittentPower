package edu.pitt.cs.faas.workload;

import java.util.Scanner;

public class WorkloadFactory {
    private static Scanner source;

    public static void setWorkloadSource(Scanner source){
        WorkloadFactory.source = source;
    }

    public static Workload getWorkload(){
        if(source == null){
            throw new RuntimeException("Workload source not set");
        }
        WorkloadTrace trace = new WorkloadTrace(source.nextLine());
        return new Workload(1, 5, 1f, trace);
    }
}
