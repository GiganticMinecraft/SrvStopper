package click.seichi.srvStopper;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public final class PluginConfig {

    private final boolean forceStop;
    private final List<String> requiredPlugins;

    private PluginConfig(boolean forceStop, List<String> requiredPlugins) {
        this.forceStop = forceStop;
        this.requiredPlugins = List.copyOf(requiredPlugins);
    }

    public static PluginConfig from(FileConfiguration config) {
        return new PluginConfig(
                config.getBoolean("force-stop", false),
                config.getStringList("required-plugins")
        );
    }

    public boolean isForceStop() {
        return forceStop;
    }

    public List<String> getRequiredPlugins() {
        return requiredPlugins;
    }

}
