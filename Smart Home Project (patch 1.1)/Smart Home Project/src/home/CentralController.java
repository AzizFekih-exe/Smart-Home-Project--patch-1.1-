package home;

import devices.Light;
import devices.MotionSensor;
import devices.SmartDevice;
import devices.Thermostat;
import interfaces.EnergyConsumer;

public class CentralController {
    private final Home home;

    public CentralController(Home home) {
        this.home = home;
    }

    // Show devices + energy if available
    public void listAllDevices() {
        System.out.println("\n--- Current Home Status ---");
        System.out.println("Home: " + home.getName());
        for (Room room : home.getRooms()) {
            System.out.println("Room: " + room.getName());
            if (room.getDevices().isEmpty()) {
                System.out.println("  (no devices)");
            }
            for (SmartDevice device : room.getDevices()) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("  [%-4s] %-15s | Status: %-15s",
                        device.getId(), device.getName(), device.getStatus()));

                if (device instanceof EnergyConsumer ec) {
                    sb.append(String.format(" | Power: %6.1f W", ec.getCurrentPowerUsage()));
                }
                System.out.println(sb.toString());
            }
        }
        System.out.println("---------------------------\n");
    }

    // Heating rule for a single room
    public void applyHeatingRuleToRoom(String roomName, double threshold) {
        Room room = home.findRoomByName(roomName);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }
        for (SmartDevice device : room.getDevices()) {
            if (device instanceof Thermostat thermostat) {
                if (thermostat.getTemperature() < threshold) {
                    thermostat.turnOn();
                    System.out.println("Rule: turning ON " + thermostat.getName()
                            + " in " + room.getName()
                            + " because temp is " + thermostat.getTemperature());
                } else {
                    thermostat.turnOff();
                    System.out.println("Rule: turning OFF " + thermostat.getName()
                            + " in " + room.getName()
                            + " because temp is " + thermostat.getTemperature());
                }
            }
        }
    }

    // Motion rule for a single room: lights ON if any sensor has motion, OFF
    // otherwise
    public void applyMotionRuleToRoom(String roomName) {
        Room room = home.findRoomByName(roomName);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        boolean motionInRoom = false;
        for (SmartDevice device : room.getDevices()) {
            if (device instanceof MotionSensor sensor && sensor.isMotionDetected()) {
                motionInRoom = true;
                break;
            }
        }

        if (motionInRoom) {
            for (SmartDevice device : room.getDevices()) {
                if (device instanceof Light light) {
                    light.turnOn();
                    System.out.println("Rule: turning ON " + light.getName()
                            + " because motion was detected in " + room.getName());
                }
            }
        } else {
            for (SmartDevice device : room.getDevices()) {
                if (device instanceof Light light) {
                    light.turnOff();
                    System.out.println("Rule: turning OFF " + light.getName()
                            + " because there is no motion in " + room.getName());
                }
            }
        }
    }
}
