package devices;

import interfaces.EnergyConsumer;

public class Light extends SmartDevice implements EnergyConsumer {
    private int brightness; // 0–100

    public Light(String id, String name, int brightness) {
        super(id, name);
        this.brightness = brightness;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public double getCurrentPowerUsage() {
        // simple model: each percent = 0.5W
        return brightness * 0.5;
    }

    @Override
    public String getStatus() {
        return (isOn() ? "ON" : "OFF") + " (brightness=" + brightness + ")";
    }
}
