package devices;

import interfaces.Controllable;

public abstract class SmartDevice implements Controllable {
    private final String id;
    private final String name;
    private boolean on;

    protected SmartDevice(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected void setOn(boolean on) {
        this.on = on;
    }

    public boolean isOn() {
        return on;
    }

    // default status based only on on/off
    public String getStatus() {
        return on ? "ON" : "OFF";
    }

    @Override
    public void turnOn() {
        this.on = true;
    }

    @Override
    public void turnOff() {
        this.on = false;
    }
}
