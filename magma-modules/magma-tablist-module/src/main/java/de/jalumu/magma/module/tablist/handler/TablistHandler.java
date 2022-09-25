package de.jalumu.magma.module.tablist.handler;

import de.jalumu.magma.module.tablist.MagmaTablistModule;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class TablistHandler {

    private static HashMap<String, String> map = new HashMap<>();

    public static void init(MagmaTablistModule module, Player player) {


        LuckPerms api = LuckPermsProvider.get();


    }

    public static void updateTablist() {
        LuckPerms api = LuckPermsProvider.get();
        
        List<Player> sortedPlayers = new ArrayList<>();

        Bukkit.getOnlinePlayers().stream()
                .sorted(Comparator.comparingInt(value -> api.getGroupManager().getGroup(api.getPlayerAdapter(Player.class).getUser(value).getPrimaryGroup()).getWeight().getAsInt() * -1))
                .forEach(sortedPlayers::add);

        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard board = player.getScoreboard();
            int i = 0;
            for (Player sorted : sortedPlayers) {
                Team team = board.getTeam(i + sorted.getName());
                if (team == null) {
                    team = board.registerNewTeam(i + sorted.getName());
                }
                team.prefix(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(api.getPlayerAdapter(Player.class).getUser(sorted).getCachedData().getMetaData().getPrefix()) + " <dark_gray>| <reset>"));
                team.addPlayer(sorted);
                System.out.println(team.getPrefix());
                i++;
            }
        }

    }

}