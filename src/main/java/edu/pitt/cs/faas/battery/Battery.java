package edu.pitt.cs.faas.battery;

public class Battery {
    private float batteryCapacity;
    private float batteryLevel;


    public Battery(int batteryCapacity){
        this.batteryCapacity = batteryCapacity;
        this.batteryLevel = batteryCapacity;
    }


    public float getCapacity(){
        return batteryCapacity;
    }

    public float getLevel(){
        return batteryLevel;
    }

    public void charge(float amt) throws BatteryEmptyException {
        if(amt < 0){
            discharge(-amt);
            return;
        }
        batteryLevel += amt;
        if(batteryLevel > batteryCapacity){
            batteryLevel = batteryCapacity;
        }
    }

    public void discharge(float amt) throws BatteryEmptyException {
        batteryLevel -= amt;
        if(batteryLevel < 0) {
            throw new BatteryEmptyException();
        }
    }
}
