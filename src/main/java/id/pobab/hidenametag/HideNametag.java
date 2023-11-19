package id.pobab.hidenametag;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.Team.Visibility;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.List;

@Mod(HideNametag.MODID)
public class HideNametag {
    public static final String MODID = "hidenametag";
    private static final Logger LOGGER = LogUtils.getLogger();

    public HideNametag() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Scoreboard scoreboard = player.getServer().getScoreboard();
        if (scoreboard.getTeamNames().isEmpty()) {
            PlayerTeam playerTeam = scoreboard.addPlayerTeam("hide_nametag");
            playerTeam.setNameTagVisibility(Visibility.NEVER);
        }
        String teamName = scoreboard.getTeamNames().stream().findFirst().get();
        PlayerTeam team = scoreboard.getPlayerTeam(teamName);
        List<String> teamMember = team.getPlayers().stream().toList();
        if (teamMember.contains(player.getName().toString())) return;
        scoreboard.addPlayerToTeam(player.getName().toString(), team);
    }

}
