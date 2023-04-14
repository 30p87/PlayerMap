package de._30p87.playermap;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.map.MapView;

import static org.bukkit.Bukkit.*;

public class TimerListener implements Listener {
    public static int ticks;
    private static int lastTicked;
    private final FileConfiguration config;

    public TimerListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onTick(ServerTickEndEvent event) {
        ticks++;
        if ((ticks - lastTicked) < config.getInt("check-existing-interval")*20) return;
        lastTicked = ticks;

        for (short i = 0; i < 32767; i++) {
            MapView mapView = getServer().getMap(i);
            if (mapView == null) break;
            mapView.addRenderer(new PlayerMapRenderer(config));
        }
    }
}
