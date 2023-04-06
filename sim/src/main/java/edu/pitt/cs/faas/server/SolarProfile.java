package edu.pitt.cs.faas.server;

import java.util.*;
import java.io.*;

public class SolarProfile {

    // Watt-seconds required for a 6.5 W Pi to run for 24 hours
    // 561600 = 6.5 * 24 * 60 * 60
    // 
    private static final float PANEL_WATTAGE = 500f;

    private Queue<Trace> traces = new LinkedList<Trace>();

    private int time = 0;

    private int granularity = 10 * 60;

    private float averagePower;
    
    public SolarProfile(String fileName) {
        readFromCSV(fileName);
    }
    
    
    private void readFromCSV(String fileName) {
        try{
            ArrayList<String> traceLines = new ArrayList<String>();
            Scanner trace = new Scanner(new File(fileName));

            // Skip header
            trace.nextLine();

            // Skip the next 144 lines
            for(int i = 0; i < 144; i++){
                trace.nextLine();
            }


            // Now at Jan 1 2020 00:00:00

            while(trace.hasNext()){
                traceLines.add(trace.nextLine());
            }



            trace.close();

            for(String line : traceLines){
                String[] lineSplit = line.split(",");

                int period = 10;
                float power = Float.parseFloat(lineSplit[6]);

                averagePower += power;

                traces.add(new Trace(period, power));
            }

            averagePower /= traces.size();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }


    public float getPower(){
        if(traces.size() == 0){
            return 0;
        }

        Queue<Trace> temp = traces;

        Trace trace = traces.poll();

        time++;

        return (trace.power * PANEL_WATTAGE);
    }

    public int getGranularity(){
        return granularity;
    }

    public float getAveragePower(){
        return averagePower;
    }
}
