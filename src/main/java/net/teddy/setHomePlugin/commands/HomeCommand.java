package net.teddy.setHomePlugin.commands;

import net.teddy.setHomePlugin.SetHomePlugin;
import net.teddy.setHomePlugin.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player))
            return true;

        // If the player entered an invalid amount of arguments
        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    SetHomePlugin.getInstance().getConfig().getString("home-command-invalid-arguments")));

            return true;
        }

        // Get all homes from the pdc
        HashMap<String, Location> playerHomes = Utils.getPlayerHomes(player);

        StringBuilder sb = new StringBuilder();
        for (String s : args) sb.append(s).append(" ");

        String homeName = sb.toString().stripTrailing();

        // If the player has no home named like given
        if (playerHomes.get(homeName) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    SetHomePlugin.getInstance().getConfig().getString("home-command-no-home-named").formatted(homeName)));

            return true;
        }

        // Get the location of the home
        Location homeLocation = playerHomes.get(homeName);

        // Teleport player while keeping its orientation
        player.teleport(new Location(
                homeLocation.getWorld(),
                homeLocation.getX(),
                homeLocation.getY(),
                homeLocation.getZ(),
                player.getLocation().getYaw(),
                player.getLocation().getPitch()));

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                SetHomePlugin.getInstance().getConfig().getString("home-command-success").formatted(homeName)));

        return true;
    }
}
