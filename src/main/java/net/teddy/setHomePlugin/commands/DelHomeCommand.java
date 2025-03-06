package net.teddy.setHomePlugin.commands;

import net.teddy.setHomePlugin.SetHomePlugin;
import net.teddy.setHomePlugin.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class DelHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player))
            return true;

        // If the player entered an invalid amount of arguments
        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    SetHomePlugin.getInstance().getConfig().getString("delhome-command-invalid-arguments")));

            return true;
        }

        HashMap<String, Location> playerHomes = Utils.getPlayerHomes(player);
        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s).append(" ");

        String homeName = sb.toString().stripTrailing();

        if (playerHomes == null)
            return true;

        if (playerHomes.remove(homeName) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    SetHomePlugin.getInstance().getConfig().getString("home-command-no-home-named").formatted(homeName)));

            return true;
        }
        else
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    SetHomePlugin.getInstance().getConfig().getString("delhome-command-success").formatted(homeName)));

        Utils.setPlayerPdc(player, playerHomes);

        return true;
    }
}
