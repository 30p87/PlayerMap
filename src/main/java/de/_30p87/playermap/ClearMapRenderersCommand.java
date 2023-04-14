package de._30p87.playermap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class ClearMapRenderersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("clearMapRenderers")) {
            for (short i = 0; i < 32767; i++) {
                MapView mapView = getServer().getMap(i);
                if (mapView == null) continue;
                for (MapRenderer mapRenderer : mapView.getRenderers()) {
                    mapView.removeRenderer(mapRenderer);
                }
            }
        }
        return true;
    }
}
