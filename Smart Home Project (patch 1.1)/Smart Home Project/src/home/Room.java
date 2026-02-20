package home;

import devices.SmartDevice;
import exceptions.DeviceNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String name;
    private final List<SmartDevice> devices = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addDevice(SmartDevice device) {
        devices.add(device);
    }

    public void removeDeviceById(String id) {
        devices.removeIf(d -> d.getId().equals(id));
    }

    public SmartDevice findDeviceById(String id) {
        for (SmartDevice d : devices) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    public SmartDevice findDeviceByIdOrThrow(String id) throws DeviceNotFoundException {
        SmartDevice d = findDeviceById(id);
        if (d == null) {
            throw new DeviceNotFoundException(
                    "Device with id '" + id + "' not found in room " + name);
        }
        return d;
    }

    public List<SmartDevice> getDevices() {
        return devices;
    }
}
