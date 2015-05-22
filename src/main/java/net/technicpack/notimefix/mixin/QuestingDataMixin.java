package net.technicpack.notimefix.mixin;

import hardcorequesting.QuestingData;
import hardcorequesting.Team;

public class QuestingDataMixin {



    public static void fixPlayerData(QuestingData player) {
        Team team = player.getTeam();

        for (Object teammateObj : team.getPlayers()) {
            Team.PlayerEntry teammate = (Team.PlayerEntry)teammateObj;
            String name = teammate.getName();
            if (QuestingData.data.containsKey(name)) {
                QuestingData teammatePlayer = (QuestingData)QuestingData.data.get(name);
                if (teammatePlayer.getTeam().getId() != team.getId())
                    QuestingData.data.remove(name);
            }
        }
    }
}
