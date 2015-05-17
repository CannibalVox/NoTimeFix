package net.technicpack.notimefix.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestingData {
    private DataReader reader;
    private DataWriter writer;
    private List<Team> teams = new ArrayList<Team>();
    private HashMap<String, Team> playersToTeam = new HashMap<String, Team>();

    private boolean isHardcore;
    private boolean isQuesting;

    public QuestingData(DataReader reader, DataWriter writer) {
        this.reader = reader;
        this.writer = writer;

        writer.writeByte(reader.readByte());
        isHardcore = reader.readBoolean();
        writer.writeBoolean(isHardcore);
        isQuesting = reader.readBoolean();
        writer.writeBoolean(isQuesting);

        int teamCount = reader.readTeams();
        writer.writeTeams(teamCount);
        for (int i = 0; i < teamCount; i++) {
            Team team = new Team(teams.size(), reader);
            team.write(writer);
            teams.add(team);

            for (PlayerEntry entry : team.players()) {
                if (entry.isInTeam())
                    playersToTeam.put(entry.getName(), team);
            }
        }

        int playerCount = reader.readPlayers();
        writer.writePlayers(playerCount);
        for (int i = 0; i < playerCount; i++) {
            Player player = new Player(reader, this);
            if (player.getTeam().getId() == -1 && playersToTeam.containsKey(player.getName()))
                player.setTeam(playersToTeam.get(player.getName()));

            player.write(writer, this);
        }
    }

    public boolean isHardcore() { return isHardcore; }
    public boolean isQuesting() { return isQuesting; }
    public Team getTeam(int id) { return teams.get(id); }
}
