# Smart Home Project (Patch 1.1)

A Java-based simulation of a Smart Home management system. This project allows users to manage rooms and smart devices (Lights, Thermostats, Motion Sensors) while implementing automated rules based on environmental conditions.

## Features

- **Room Management**: Create and organize multiple rooms in your smart home.
- **Smart Devices**:
  - **Lights**: Can be toggled on/off and have adjustable brightness levels.
  - **Thermostats**: Monitor temperature and track power usage (heating simulation).
  - **Motion Sensors**: Detect and clear motion states to trigger automated actions.
- **Automation Rules**:
  - **Heating Rule**: Automatically turns thermostats ON if the temperature falls below a specified threshold.
  - **Motion Rule**: Automatically toggles lights based on motion detection within a room.
- **Energy Monitoring**: Real-time power usage tracking for energy-consuming devices.
- **Robust Input Handling**: Safe user input processing with range validation.

## Project Structure

```text
src/
├── devices/             # Device implementations (SmartDevice, Light, Thermostat, etc.)
├── exceptions/          # Custom exception classes
├── home/               # Core logic (Home, Room, CentralController, Main)
└── interfaces/          # Abstractions for device capabilities (Controllable, EnergyConsumer)
```

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 17 or higher.

### Compilation
From the `Smart Home Project` root directory:
```bash
javac -d out -sourcepath src src/home/Main.java
```

### Running
```bash
java -cp out home.Main
```

## Usage
1. **Add a Room**: Start by creating a room (e.g., "Living Room").
2. **Add Devices**: Add various devices to your rooms.
3. **Automate**: Run the heating or motion rules to see the system react to current conditions.
4. **Control**: Manually override device states via the control menu.
