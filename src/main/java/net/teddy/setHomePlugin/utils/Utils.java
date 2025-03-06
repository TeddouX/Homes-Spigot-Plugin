package net.teddy.setHomePlugin.utils;

import com.jeff_media.morepersistentdatatypes.DataType;
import net.teddy.setHomePlugin.SetHomePlugin;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Utils {

    public static final NamespacedKey homesKey = new NamespacedKey(SetHomePlugin.getInstance(), "homes");

    public static HashMap<String, Location> getPlayerHomes(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();

        // Get all homes from the pdc
        return (HashMap<String, Location>) pdc.get(homesKey, DataType.asMap(PersistentDataType.STRING, DataType.LOCATION));
    }

    public static void setPlayerPdc(Player player, HashMap<String, Location> homes) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();

        pdc.set(homesKey, DataType.asMap(PersistentDataType.STRING, DataType.LOCATION), homes);
    }
}
