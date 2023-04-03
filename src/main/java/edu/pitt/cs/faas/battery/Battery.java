package edu.pitt.cs.faas.battery;

public class Battery {
    private int batteryCapacity;
    private int batteryLevel;


    public Battery(int batteryCapacity){
        this.batteryCapacity = batteryCapacity;
        this.batteryLevel = batteryCapacity;
    }


    public int getCapacity(){
        return batteryCapacity;
    }

    public int getLevel(){
        return batteryLevel;
    }

    public void charge(int amt){
        batteryLevel += amt;
        if(batteryLevel > batteryCapacity) batteryLevel = batteryCapacity;
    }

    public void discharge(int amt) throws BatteryEmptyException {
        batteryLevel -= amt;
        if(batteryLevel < 0) {
            throw new BatteryEmptyException();
        }
    }
}
