package net.technicpack.notimefix.io;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
    private int id = -1;
    private String name;
    private int livesSetting;
    private int rewardSetting;
    private List<PlayerEntry> players = new ArrayList<PlayerEntry>();
    private List<Quest> quests = new ArrayList<Quest>();
    private List<Pair<Integer, Integer>> reputations = new ArrayList<Pair<Integer, Integer>>();

    public Team(int id, DataReader reader) {
        this.id = id;

        if (this.id != -1) {
            this.name = reader.readName();
            this.id = reader.readTeams();
            this.livesSetting = reader.readTeamLivesSetting();
            this.rewardSetting = reader.readTeamRewardSetting();

            int playerCount = reader.readPlayers();
            for (int i = 0; i < playerCount; i++) {
                String name = reader.readName();
                if (name == null) name = "Unknown";
                boolean inTeam = reader.readBoolean();
                boolean owner = reader.readBoolean();
                this.players.add(new PlayerEntry(name, inTeam, owner));
            }
        }

        int quests = reader.readQuests();
        for (int i = 0; i < quests; i++) {
            Quest quest = new Quest(reader);
            this.quests.add(quest);
        }

        int reputations = reader.readReputation();

        for (int i = 0; i < reputations; i++) {
            int repId = reader.readReputation();
            int repValue = reader.readReputationValue();
            this.reputations.add(new ImmutablePair<Integer, Integer>(repId, repValue));
        }
    }

    public void write(DataWriter writer) {
        if (this.id != -1) {
            writer.writeName(this.name);
            writer.writeTeams(this.id);
            writer.writeTeamLivesSetting(this.livesSetting);
            writer.writeTeamRewardSetting(this.rewardSetting);

            writer.writePlayers(this.players.size());
            for (int i = 0; i < players.size(); i++) {
                PlayerEntry player = players.get(i);

                writer.writeName(player.getName());
                writer.writeBoolean(player.isInTeam());
                writer.writeBoolean(player.isOwner());
            }
        }

        writer.writeQuests(quests.size());
        for (int i = 0; i < quests.size(); i++) {
            quests.get(i).write(writer);
        }

        writer.writeReputation(reputations.size());

        for (int i = 0; i < reputations.size(); i++) {
            writer.writeReputation(reputations.get(i).getKey());
            writer.writeReputationValue(reputations.get(i).getValue());
        }
    }

    public List<PlayerEntry> players() { return players; }
    public int getId() { return id; }
}
