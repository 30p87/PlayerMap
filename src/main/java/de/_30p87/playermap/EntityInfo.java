package de._30p87.playermap;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;

public class EntityInfo {
    public Location location;
    public Chunk chunk;
    public String caption;
    public MapCursor.Type mapCursorsType;
    public EntityInfo(Entity entity, FileConfiguration config) {
        location = entity.getLocation();
        chunk = location.getChunk();
        caption = entity instanceof Player && config.getBoolean("display-player-names") ||
                !(entity instanceof Player) && config.getBoolean("display-entity-names") ?
                    entity.getName() :
                    !(entity instanceof Player) && config.getBoolean("display-entity-types") ?
                            entity.getType().toString() :
                            null;
        mapCursorsType = entity instanceof Player ? MapCursor.Type.GREEN_POINTER :
                entity instanceof Monster ?
                        MapCursor.Type.RED_MARKER :
                        MapCursor.Type.WHITE_CROSS;
    }
}
