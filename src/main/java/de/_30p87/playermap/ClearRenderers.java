package de._30p87.playermap;

import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import static org.bukkit.Bukkit.getServer;

public class ClearRenderers {
    public void clearRenderers() {
        for (short i = 0; i < 32767; i++) {
            MapView mapView = getServer().getMap(i);
            if (mapView == null) continue;
            for (MapRenderer mapRenderer : mapView.getRenderers()) {
                if (mapRenderer.toString().contains("PlayerMapRenderer")) mapView.removeRenderer(mapRenderer);
            }
        }
    }
}
