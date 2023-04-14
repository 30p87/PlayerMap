package de._30p87.playermap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PlayerMap extends JavaPlugin {
    public final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.addDefault("check-existing-interval", 15);
        config.addDefault("update-interval", 5);
        config.addDefault("display-friendly-entities", false);
        config.addDefault("display-monsters", false);
        config.addDefault("display-player-names", true);
        config.addDefault("display-entity-names", false);
        config.addDefault("display-entity-types", false);
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new TimerListener(config), this);
        getServer().getPluginManager().registerEvents(new MapInitializeListener(config), this);
        Objects.requireNonNull(getCommand("clearmaprenderers")).setExecutor(new ClearMapRenderersCommand());

        new ClearRenderers().clearRenderers();
    }
}
