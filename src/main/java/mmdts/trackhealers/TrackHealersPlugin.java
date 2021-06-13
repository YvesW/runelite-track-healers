// Special thanks to Miracle Nuts, RoteRosen, and Schizo Girl for helping me debug this plugin.

package mmdts.trackhealers;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Player;
import net.runelite.api.NPC;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.ChatMessageType;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.kit.KitType;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

import static net.runelite.client.RuneLite.LOGS_DIR;

@PluginDescriptor(
        name = "Track Healers",
        description = "Just a quick plugin to track healers.",
        tags = {"minigame", "tick", "logger", "tracker", "healer"}
)

@Slf4j
public class TrackHealersPlugin extends Plugin
{
    private static final int BA_WAVE_NUM_INDEX = 2;
    @Inject
    private Client client;

    private String currentWave = "0";
    private long waveTickCount = 0;
    private boolean inWave = false;
    private char role = 0;

    private PrintWriter out;
    private BufferedWriter bw;
    private FileWriter fw;

    @Override
    protected void startUp() throws Exception
    {
        File logFile = new File(LOGS_DIR, "track_healers.log");
        fw = new FileWriter(logFile, true);
        bw = new BufferedWriter(fw);
        out = new PrintWriter(bw);
    }

    @Override
    protected void shutDown() throws Exception
    {
        shutDownActions();
        out.close();
        bw.close();
        fw.close();
    }

    private void shutDownActions() throws IOException
    {
        out.flush();
        bw.flush();
        fw.flush();
        currentWave = "0";
        waveTickCount = 0;
        inWave = false;
        role = 0;
    }


    @Subscribe
    public void onWidgetLoaded(WidgetLoaded event)
    {
        switch (event.getGroupId())
        {
            case WidgetID.BA_ATTACKER_GROUP_ID:
            {
                role = Role.ATTACKER;
                break;
            }
            case WidgetID.BA_DEFENDER_GROUP_ID:
            {
                role = Role.DEFENDER;
                break;
            }
            case WidgetID.BA_HEALER_GROUP_ID:
            {
                role = Role.HEALER;
                break;
            }
            case WidgetID.BA_COLLECTOR_GROUP_ID:
            {
                role = Role.COLLECTOR;
                break;
            }
        }
    }

    @Subscribe
    public void onGameTick(GameTick tick) throws IOException
    {
        if (!inWave || (role != Role.HEALER && role != Role.DEFENDER))
        {
            return;
        }

        if (!client.isInInstancedRegion())
        {
            shutDownActions();
            // log.debug("WAVE ENDED");
        }

        ++waveTickCount;
        StringBuilder str = new StringBuilder();

        str
                .append(currentWave)
                .append(",")
                .append(waveTickCount)
                .append("|")
        ;

        Player[] players = client.getCachedPlayers();
        for (Player player : players)
        {
            if (player == null)
            {
                continue;
            }

            WorldPoint loc = player.getWorldLocation();

            if (player.getWorldLocation() == null)
            {
                continue;
            }

            char playerRole = Role.getRoleFromJawId(player.getPlayerComposition().getEquipmentId(KitType.JAW));

            if (playerRole == 0)
            {
                // Something weird happened and we left the BA game without that being marked.
                shutDownActions();
                // log.debug("NON ROLED PLAYER SIGNALED WAVE END");
            }

            str
                    .append("|")
                    .append(playerRole)
                    .append(",")
                    .append(Long.toString(loc.getRegionX()))
                    .append(",")
                    .append(Long.toString(loc.getRegionY()))
            ;
        }

        str
                .append("|")
        ;

        NPC[] npcs = client.getCachedNPCs();
        for (NPC npc : npcs)
        {
            if (npc == null)
            {
                continue;
            }

            if (!Objects.equals(npc.getName(), "Penance Healer"))
            {
                continue;
            }

            if (npc.getWorldLocation() == null)
            {
                continue;
            }

            WorldPoint loc = npc.getWorldLocation();
            Actor interacting = npc.getInteracting();
            char firstText = '_';

            str
                    .append("|")
                    .append(npc.getIndex())
                    .append(",")
                    .append(Long.toString(loc.getRegionX()))
                    .append(",")
                    .append(Long.toString(loc.getRegionY()))
            ;

            if (interacting instanceof Player)
            {
                firstText = Role.getRoleFromJawId(
                        ((Player) interacting).getPlayerComposition().getEquipmentId(KitType.JAW)
                );
                loc = interacting.getWorldLocation();
            }

            if (interacting instanceof NPC)
            {
                firstText = 'r';
                loc = interacting.getWorldLocation();
            }

            str
                    .append(">")
                    .append(firstText)
            ;

            if (firstText != '_')
            {
                str
                        .append(",")
                        .append(Long.toString(loc.getRegionX()))
                        .append(",")
                        .append(Long.toString(loc.getRegionY()))
                ;

            } else
            {
                str
                        .append(",_,_")
                ;
            }
        }

        // log.debug(str.toString());

        out.println(str);
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (event.getType() == ChatMessageType.GAMEMESSAGE
                && event.getMessage().startsWith("---- Wave:"))
        {
            currentWave = event.getMessage().split(" ")[BA_WAVE_NUM_INDEX];
            inWave = true;
            waveTickCount = 0;

            // log.debug("WAVE " + currentWave + " STARTED");
        }
    }
}

