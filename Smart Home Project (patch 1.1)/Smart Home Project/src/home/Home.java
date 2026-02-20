package home;

import java.util.ArrayList;
import java.util.List;

public class Home {
    private final String name;
    private final List<Room> rooms = new ArrayList<>();

    public Home(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room findRoomByName(String roomName) {
        for (Room r : rooms) {
            if (r.getName().equalsIgnoreCase(roomName)) {
                return r;
            }
        }
        return null;
    }
}
