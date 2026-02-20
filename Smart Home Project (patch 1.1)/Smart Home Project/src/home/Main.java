package home;

import devices.Light;
import devices.MotionSensor;
import devices.Thermostat;
import exceptions.DeviceNotFoundException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Home home = new Home("My Smart Home");
        CentralController controller = new CentralController(home);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Smart Home Menu ===");
            System.out.println("1. Add room");
            System.out.println("2. Add device to room");
            System.out.println("3. List all devices");
            System.out.println("4. Apply heating rule (per room)");
            System.out.println("5. Apply motion rule (per room)");
            System.out.println("6. Control a device");
            System.out.println("0. Exit");

            int choice = readInt(scanner, "Choice: ");

            switch (choice) {
                case 1 -> addRoom(home, scanner);
                case 2 -> addDeviceToRoom(home, scanner);
                case 3 -> controller.listAllDevices();
                case 4 -> {
                    System.out.print("Apply heating rule to which room? ");
                    String roomName = scanner.nextLine();
                    controller.applyHeatingRuleToRoom(roomName, 20.0);
                }
                case 5 -> {
                    System.out.print("Apply motion rule to which room? ");
                    String roomName = scanner.nextLine();
                    controller.applyMotionRuleToRoom(roomName);
                }
                case 6 -> controlDevice(home, scanner);
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // read an int safely (handles empty / invalid input)
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Please enter a number.");
                continue;
            }
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }
    //handling range of int for brightness
    private static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value < min || value > max) {
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } else {
                return value;
            }
        }
    }
    //handling range of temperature
    private static double readDoubleInRange(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Please enter a number.");
                continue;
            }
            try {
                double value = Double.parseDouble(line);
                if (value < min || value > max) {
                    System.out.println("Please enter a value between " + min + " and " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }
    //user must input an id and a name for the device
    private static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("This field cannot be empty. Please try again.");
            } else {
                return line;
            }
        }
    }



    private static void addRoom(Home home, Scanner scanner) {
        String name = readNonEmptyString(scanner, "Enter room name: ");
        Room room = new Room(name);
        home.addRoom(room);
        System.out.println("Room added: " + name);
    }

    private static void addDeviceToRoom(Home home, Scanner scanner) {
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine();
        Room room = home.findRoomByName(roomName);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.println("Choose device type:");
        System.out.println("1. Light");
        System.out.println("2. Thermostat");
        System.out.println("3. Motion sensor");
        int type = readIntInRange(scanner, "Type: ",1 , 3);

        String id = readNonEmptyString(scanner, "Enter device id: ");
        String name = readNonEmptyString(scanner, "Enter device name: ");


        switch (type) {
            case 1 -> {
                int brightness = readIntInRange(scanner, "Brightness (0-100): ", 0, 100);
                Light light = new Light(id, name, brightness);
                room.addDevice(light);
            }
            case 2 -> {
                System.out.print("Initial temperature: ");
                double temp = readDoubleInRange(scanner, "Initial temperature (-10 to 50): ", -10.0, 50.0);
                Thermostat thermostat = new Thermostat(id, name, temp);
                room.addDevice(thermostat);
            }
            case 3 -> {
                // start with NO MOTION
                MotionSensor sensor = new MotionSensor(id, name);
                room.addDevice(sensor);
            }
            default -> System.out.println("Unknown device type.");
        }
    }

    private static void controlDevice(Home home, Scanner scanner) {
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine();
        Room room = home.findRoomByName(roomName);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.print("Enter device id: ");
        String id = scanner.nextLine();

        try {
            var device = room.findDeviceByIdOrThrow(id);

            System.out.println("Selected: " + device.getName() + " ("
                    + device.getClass().getSimpleName() + ")");

            if (device instanceof Light light) {
                System.out.println("1. Turn ON");
                System.out.println("2. Turn OFF");
                System.out.println("3. Set brightness");
                int c = readInt(scanner, "Choice: ");
                switch (c) {
                    case 1 -> light.turnOn();
                    case 2 -> light.turnOff();
                    case 3 -> {
                        int b = readIntInRange(scanner, "New brightness (0-100): ", 0, 100);
                        light.setBrightness(b);
                    }
                    default -> {
                        System.out.println("Invalid choice for light.");
                        return;
                    }
                }
            } else if (device instanceof Thermostat thermostat) {
                System.out.println("1. Turn ON");
                System.out.println("2. Turn OFF");
                System.out.println("3. Set temperature");
                int c = readInt(scanner, "Choice: ");
                switch (c) {
                    case 1 -> thermostat.turnOn();
                    case 2 -> thermostat.turnOff();
                    case 3 -> {
                        System.out.print("New temperature: ");
                        double t = readDoubleInRange(scanner, "New temperature (-10 to 50): ", -10.0, 50.0);
                        thermostat.setTemperature(t);
                        if (t >= 25.0) {
                            thermostat.turnOff();
                        }
                    }
                    default -> {
                        System.out.println("Invalid choice for thermostat.");
                        return;
                    }
                }
            } else if (device instanceof MotionSensor sensor) {
                System.out.println("1. Detect motion");
                System.out.println("2. Clear motion");
                int c = readInt(scanner, "Choice: ");
                switch (c) {
                    case 1 -> sensor.detectMotion();
                    case 2 -> sensor.clearMotion();
                    default -> {
                        System.out.println("Invalid choice for motion sensor.");
                        return;
                    }
                }
            }

            System.out.println("Action applied.");

        } catch (DeviceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
