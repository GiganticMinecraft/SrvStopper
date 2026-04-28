package click.seichi.srvStopper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class SrvStopper extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        // /reload コマンド実行時には検査しない
        if (event.getType() != ServerLoadEvent.LoadType.STARTUP) {
            return;
        }

        List<String> requiredPlugins = getConfig().getStringList("required-plugins");
        List<String> missingPlugins = requiredPlugins.stream()
                .filter(name -> {
                    var plugin = getServer().getPluginManager().getPlugin(name);
                    return plugin == null || !plugin.isEnabled();
                })
                .sorted()
                .toList();

        if (missingPlugins.isEmpty()) {
            getLogger().info("必須プラグインがすべて読み込まれています。");
        } else {
            getLogger().warning("以下の必須プラグインが読み込まれていません: " + String.join(", ", missingPlugins));
            if (getConfig().getBoolean("force-stop", false)) {
                getLogger().severe("サーバを停止します。");
                getServer().shutdown();
            }
        }
    }
}
