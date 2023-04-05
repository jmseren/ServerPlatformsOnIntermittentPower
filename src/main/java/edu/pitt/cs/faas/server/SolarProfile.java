package edu.pitt.cs.faas.server;

import java.util.*;
import java.io.*;

public class SolarProfile {

    private static final int PANEL_WATTAGE = 1;

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


    public String toString(){
        String output = "";
        int time = 0;

        for(Trace trace : traces){
            output += "Time: " + time + " Period: " + trace.period + " Power: " + trace.power + "\n";
            time += trace.period;
        }

        return output;
    }

    public float getPower(){
        if(traces.size() == 0){
            return 0;
        }

        Trace trace = traces.peek();

        if(time == trace.period){
            traces.remove();
            time = 0;
        }

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
