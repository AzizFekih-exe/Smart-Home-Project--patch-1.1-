package devices;

public class MotionSensor extends SmartDevice {
    private boolean motionDetected;

    public MotionSensor(String id, String name) {
        super(id, name);
        // motionDetected is false by default → NO MOTION initially
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public void detectMotion() {
        motionDetected = true;
    }

    public void clearMotion() {
        motionDetected = false;
    }

    @Override
    public String getStatus() {
        return motionDetected ? "MOTION" : "NO MOTION";
    }
}
