package mmdts.trackhealers;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.ItemID;

public final class Role {
    public static final char ATTACKER = 'a';
    public static final char COLLECTOR = 'c';
    public static final char DEFENDER = 'd';
    public static final char HEALER = 'h';
    private static final ImmutableSet<Integer> DEFENDER_ICONS = ImmutableSet.of(
            ItemID.DEFENDER_ICON,
            ItemID.DEFENDER_ICON_22340,
            ItemID.DEFENDER_ICON_22341,
            ItemID.DEFENDER_ICON_22342,
            ItemID.DEFENDER_ICON_22343,
            ItemID.DEFENDER_ICON_22344,
            ItemID.DEFENDER_ICON_22345,
            ItemID.DEFENDER_ICON_23466,
            ItemID.DEFENDER_ICON_23467,
            ItemID.DEFENDER_ICON_23468,
            ItemID.DEFENDER_ICON_23469,
            ItemID.DEFENDER_ICON_23470,
            ItemID.DEFENDER_ICON_22725,
            ItemID.DEFENDER_ICON_22726,
            ItemID.DEFENDER_ICON_22727,
            ItemID.DEFENDER_ICON_22728
    );
    private static final ImmutableSet<Integer> HEALER_ICONS = ImmutableSet.of(
            ItemID.HEALER_ICON,
            ItemID.HEALER_ICON_10567,
            ItemID.HEALER_ICON_20802,
            ItemID.HEALER_ICON_22308,
            ItemID.HEALER_ICON_22309,
            ItemID.HEALER_ICON_22310,
            ItemID.HEALER_ICON_22311,
            ItemID.HEALER_ICON_23478,
            ItemID.HEALER_ICON_23479,
            ItemID.HEALER_ICON_23480,
            ItemID.HEALER_ICON_23481,
            ItemID.HEALER_ICON_23482,
            ItemID.HEALER_ICON_23483,
            ItemID.HEALER_ICON_23484,
            ItemID.HEALER_ICON_23485,
            ItemID.HEALER_ICON_23486
    );
    private static final ImmutableSet<Integer> ATTACKER_ICONS = ImmutableSet.of(
            ItemID.ATTACKER_ICON,
            ItemID.ATTACKER_ICON_23460,
            ItemID.ATTACKER_ICON_23461,
            ItemID.ATTACKER_ICON_23462,
            ItemID.ATTACKER_ICON_23463,
            ItemID.ATTACKER_ICON_23464,
            ItemID.ATTACKER_ICON_23465,
            ItemID.ATTACKER_ICON_22346,
            ItemID.ATTACKER_ICON_22347,
            ItemID.ATTACKER_ICON_22348,
            ItemID.ATTACKER_ICON_22349,
            ItemID.ATTACKER_ICON_22721,
            ItemID.ATTACKER_ICON_22722,
            ItemID.ATTACKER_ICON_22723,
            ItemID.ATTACKER_ICON_22729,
            ItemID.ATTACKER_ICON_22730
    );
    private static final ImmutableSet<Integer> COLLECTOR_ICONS = ImmutableSet.of(
            ItemID.COLLECTOR_ICON,
            ItemID.COLLECTOR_ICON_23471,
            ItemID.COLLECTOR_ICON_23472,
            ItemID.COLLECTOR_ICON_23473,
            ItemID.COLLECTOR_ICON_23474,
            ItemID.COLLECTOR_ICON_23475,
            ItemID.COLLECTOR_ICON_23476,
            ItemID.COLLECTOR_ICON_23477,
            ItemID.COLLECTOR_ICON_22312,
            ItemID.COLLECTOR_ICON_22313,
            ItemID.COLLECTOR_ICON_22314,
            ItemID.COLLECTOR_ICON_22315,
            ItemID.COLLECTOR_ICON_22337,
            ItemID.COLLECTOR_ICON_22338,
            ItemID.COLLECTOR_ICON_22339,
            ItemID.COLLECTOR_ICON_22724
    );

    protected static char getRoleFromJawId(int jawId)
    {
        if (HEALER_ICONS.contains(jawId))
        {
            return HEALER;
        }
        if (DEFENDER_ICONS.contains(jawId))
        {
            return DEFENDER;
        }
        if (ATTACKER_ICONS.contains(jawId))
        {
            return ATTACKER;
        }
        if (COLLECTOR_ICONS.contains(jawId))
        {
            return COLLECTOR;
        }

        return 0;
    }

}
