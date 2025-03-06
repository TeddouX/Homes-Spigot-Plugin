package net.teddy.setHomePlugin.commands;

import net.teddy.setHomePlugin.SetHomePlugin;
import net.teddy.setHomePlugin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SeeHomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player))
            return true;

        if (args.length != 0 && !player.hasPermission("sethome.see-other-players-homes"))
            // Player trying to get another person's homes, but doesn't have permission
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    SetHomePlugin.getInstance().getConfig().getString("see-homes-dont-have-permissions")));

        else if (args.length == 0) {
            // Player getting his own homes
            HashMap<String, Location> playerHomes = Utils.getPlayerHomes(player);

            if (playerHomes == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        SetHomePlugin.getInstance().getConfig().getString("home-command-no-homes")));

                return true;
            }

            int playerHomesAmount = playerHomes.size();
            if (playerHomesAmount == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        SetHomePlugin.getInstance().getConfig().getString("home-command-no-homes")));

                return true;
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        SetHomePlugin.getInstance().getConfig().getString("see-homes-homes-amount").formatted(playerHomesAmount)));

                String finalMessage = createHomesMessage(playerHomes);
                player.sendMessage(finalMessage);
            }

        } else {
            // Player that has permissions to get another player's homes
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);

            if (target == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        SetHomePlugin.getInstance().getConfig().getString("see-homes-no-players-named").formatted(targetName)));

                return true;
            }

            HashMap<String, Location> targetHomes = Utils.getPlayerHomes(target);

            if (targetHomes == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        SetHomePlugin.getInstance().getConfig().getString("see-homes-target-player-has-no-homes").formatted(targetName)));

                return true;
            }

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    SetHomePlugin.getInstance().getConfig().getString("see-homes-target-player-amount").formatted(targetName, targetHomes.size())));

            String finalMessage = createHomesMessage(targetHomes);
            player.sendMessage(finalMessage);
        }

        return true;
    }

    String createHomesMessage(HashMap<String, Location> homes) {
        StringBuilder sb = new StringBuilder();

        homes.forEach((home, location) -> {
            sb.append("\u2000- ") // So the trim doesn't delete the first whitespace
                    .append(ChatColor.AQUA).append(home)
                    .append(ChatColor.WHITE).append(": ")
                    .append(ChatColor.YELLOW)
                    .append(Math.round(location.getX())).append(" ")
                    .append(Math.round(location.getY())).append(" ")
                    .append(Math.round(location.getZ())).append(ChatColor.WHITE)
                    .append("\n");
        });

        return sb.toString().trim();
    }
}
