package click.seichi.srvStopper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public final class SrvStopperCommand implements CommandExecutor, TabCompleter {

    private final SrvStopper plugin;

    public SrvStopperCommand(SrvStopper plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sendUsage(sender, label);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "version" -> {
                var desc = plugin.getDescription();
                sender.sendMessage(desc.getName() + " v" + desc.getVersion());
            }
            case "config" -> {
                if (args.length < 2) {
                    sendUsage(sender, label);
                    return true;
                }
                switch (args[1].toLowerCase()) {
                    case "show" -> {
                        var config = plugin.getPluginConfig();
                        boolean forceStop = config.isForceStop();
                        var requiredPlugins = config.getRequiredPlugins().stream().sorted().toList();

                        sender.sendMessage(ChatColor.GRAY + "強制停止: " + (forceStop ? ChatColor.GREEN + "有効" : ChatColor.RED + "無効"));
                        sender.sendMessage(ChatColor.GRAY + "必須プラグイン:");
                        if (requiredPlugins.isEmpty()) {
                            sender.sendMessage(ChatColor.GRAY + "  (なし)");
                        } else {
                            for (var name : requiredPlugins) {
                                sender.sendMessage(ChatColor.GRAY + "  - " + ChatColor.WHITE + name);
                            }
                        }
                    }
                    case "reload" -> {
                        plugin.reloadPluginConfig();
                        sender.sendMessage("設定を再読み込みしました。");
                    }
                    default -> sendUsage(sender, label);
                }
            }
            default -> sendUsage(sender, label);
        }

        return true;
    }

    private void sendUsage(CommandSender sender, String label) {
        sender.sendMessage("Usage: /" + label + " <version|config <show|reload>>");
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 1) {
            return Stream.of("version", "config")
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("config")) {
            return Stream.of("show", "reload")
                    .filter(s -> s.startsWith(args[1].toLowerCase()))
                    .toList();
        }

        return List.of();
    }
}
