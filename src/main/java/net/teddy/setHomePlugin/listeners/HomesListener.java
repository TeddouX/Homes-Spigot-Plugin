package net.teddy.setHomePlugin.listeners;

import net.teddy.setHomePlugin.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class HomesListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        // Initialize home storage
        if (!player.hasPlayedBefore())
            Utils.setPlayerPdc(player, new HashMap<>());
    }
}
