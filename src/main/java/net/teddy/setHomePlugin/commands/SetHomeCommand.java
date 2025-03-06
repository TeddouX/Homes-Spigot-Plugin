package net.teddy.setHomePlugin.commands;

import com.jeff_media.morepersistentdatatypes.DataType;
import net.teddy.setHomePlugin.SetHomePlugin;
import net.teddy.setHomePlugin.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class SetHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player))
            return true;

        // If the player entered an invalid amount of arguments
        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    SetHomePlugin.getInstance().getConfig().getString("sethome-command-invalid-arguments")));

            return true;
        }

        Location playerLocation = player.getLocation();

        StringBuilder sb = new StringBuilder();
        for (String s : args)
            sb.append(s).append(" ");

        String homeName = sb.toString().stripTrailing();

        PersistentDataContainer pdc = player.getPersistentDataContainer();
        HashMap<String, Location> playerHomes = new HashMap<>();

        // If the pdc already has a homes Hashmap retreive it
        if (pdc.has(Utils.homesKey))
            playerHomes = (HashMap<String, Location>) pdc.get(Utils.homesKey, DataType.asMap(PersistentDataType.STRING, DataType.LOCATION));

        // Add the home to the pdc
        playerHomes.put(homeName, playerLocation);
        Utils.setPlayerPdc(player, playerHomes);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                SetHomePlugin.getInstance().getConfig().getString("sethome-command-home-set").formatted(homeName)));

        return true;
    }
}
