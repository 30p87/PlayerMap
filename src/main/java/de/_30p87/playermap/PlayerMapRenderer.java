package de._30p87.playermap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.map.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de._30p87.playermap.TimerListener.ticks;

public class PlayerMapRenderer extends MapRenderer {
    private static int lastTicked;
    private final FileConfiguration config;

    public PlayerMapRenderer(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void render(@NotNull MapView mapView, @NotNull MapCanvas canvas, @NotNull Player player) {
        if ((ticks - lastTicked) < config.getInt("update-interval")*20) return;
        lastTicked = ticks;

        if (mapView.getWorld() == null) return;

        List<EntityInfo> entityInfos = new ArrayList<>();
        for (Entity entity : mapView.getWorld().getEntities()) {
            if (!(entity instanceof Player)) {
                if (entity instanceof LivingEntity) {
                    if (entity instanceof Monster) {
                        if (!config.getBoolean("display-monsters")) continue;
                    } else {
                        if (!config.getBoolean("display-friendly-entities")) continue;
                    }
                } else continue;
            }
            entityInfos.add(new EntityInfo(entity, config));
        }
        if (entityInfos.isEmpty()) return;

        MapCursorCollection mapCursorCollection = new MapCursorCollection();
        double scale = Math.pow(2, mapView.getScale().ordinal());

        for (EntityInfo entityInfo : entityInfos) {
            if (!(entityInfo.location.x() > mapView.getCenterX() - scale * 64 &&
                    entityInfo.location.x() < mapView.getCenterX() + scale * 64 &&
                    entityInfo.location.z() > mapView.getCenterZ() - scale * 64 &&
                    entityInfo.location.z() < mapView.getCenterZ() + scale * 64)) continue;

            double dx = ((int)entityInfo.location.x() - mapView.getCenterX()) * 2;
            double dz = ((int)entityInfo.location.z() - mapView.getCenterZ()) * 2;

            if (dx < -128 || dx > 128 || dz < -128 || dz > 128) continue;

            mapCursorCollection.addCursor(new MapCursor((byte)dx, (byte)dz, byteFromDirection(entityInfo.location.getYaw()), entityInfo.mapCursorsType, true, entityInfo.caption));
        }

        canvas.setCursors(mapCursorCollection);
    }

    private float normalizeYaw(float yaw) {
        return yaw < -180 ?
                normalizeYaw(yaw + 360) :
                yaw > 180 ?
                        normalizeYaw(yaw - 360) :
                        yaw;
    }
    private byte byteFromDirection(float direction) {
        byte raw = (byte)(normalizeYaw(direction) / 180 * 8);
        return raw < 0 ? (byte)(raw + 15) : raw;
    }
}
