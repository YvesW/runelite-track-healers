### mmdts's Healer Tracking Plugin


Saves healer information to `%user_profile%/.runelite/logs/track_healers.log` or `~/.runelite/logs/track_healers.log`.

You can then use the log file to understand healers better (or send it to me on discord: mmdts#9857).

To parse the file, use the following pseudocode:

```
for line in file:
    for (wave_info, players, npcs) in line.split("||"):
        (wave_number, tick) = wave_info.split(",")
        # Do stuff with the wave info.
        for player in players.split("|"):
            (role, x, y) =  player.split(",")
            # Do stuff with the player data.
        for npc_and_target in npcs.split("|"):
            (npc, target) = npc_and_target.split(">")
            (client_index, x, y) = npc.split(",")
            (role, x, y) =  target.split(",")
            # Do stuff with the npc data and the target data.
            # Runner has role "r", and all of role, x, and y are set to "_" if no target is present.
```

