package devices;

import interfaces.EnergyConsumer;

public class Thermostat extends SmartDevice implements EnergyConsumer {
    private double temperature; // °C

    public Thermostat(String id, String name, double temperature) {
        super(id, name);
        this.temperature = temperature;
    }

    @Override
    public void turnOn() {
        setOn(true);
    }

    @Override
    public void turnOff() {
        setOn(false);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    private boolean isHeating() {
        return temperature < 21.0;
    }

    @Override
    public double getCurrentPowerUsage() {
        // very rough model: heating uses more power
        return isHeating() ? 500.0 : 50.0;
    }

    @Override
    public String getStatus() {
        return (isOn() ? "ON" : "OFF") + " (temp=" + temperature + "°C)";
    }
}
