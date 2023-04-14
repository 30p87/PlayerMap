package de._30p87.playermap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;

public class MapInitializeListener implements Listener {
    private final FileConfiguration config;

    public MapInitializeListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onMapInitialize(MapInitializeEvent mapInitializeEvent) {
        mapInitializeEvent.getMap().addRenderer(new PlayerMapRenderer(config));
    }
}
