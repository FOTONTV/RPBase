package ru.fotontv.rpbase.modules.jail;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Jail {
    private int numCamera = 0;
    private final String name;
    private final List<CameraJail> camers = new ArrayList<>();

    public Jail(String name) {
        this.name = name;
    }

    public boolean addCamera(int num, Location loc) {
        if (num > numCamera) {
            numCamera += num;
            int found = 0;
            for (CameraJail name : camers) {
                if (name.getId() == num)
                    found += 1;
            }
            if (found == 0) {
                CameraJail camera = new CameraJail(num, loc);
                camers.add(camera);
                return true;
            }
            return false;
        }
        return false;
    }

    public void setCamers(List<CameraJail> camers) {
        this.camers.addAll(camers);
    }

    public String getName() {
        return name;
    }

    public List<CameraJail> getCamers() {
        return camers;
    }

    public boolean removeCamera(int num) {
        for (CameraJail cameraJail : camers) {
            if (cameraJail.getId() == num) {
                camers.remove(cameraJail);
                numCamera -= 1;
                return true;
            }
        }
        return false;
    }

    public void removePlayer(int num, String nick) {
        for (CameraJail cameraJail : camers) {
            if (cameraJail.getId() == num) {
                cameraJail.removePlayer(nick);
            }
        }
    }
}
