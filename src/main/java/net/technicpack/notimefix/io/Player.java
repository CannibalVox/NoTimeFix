package net.technicpack.notimefix.io;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int lives;
    private int ticks;
    private int hours;
    private int selectedQuest = -1;
    private int selectedTask = -1;
    private boolean hasPlayedLore;
    private boolean hasReceivedBook;
    private Team team;
    private List<Pair<Integer, Integer>> bagData = new ArrayList<Pair<Integer, Integer>>();
    private int[] deaths;

    public Player(DataReader reader, QuestingData data) {
        this.name = reader.readName();

        if (data.isHardcore()) {
            this.lives = reader.readLives();
        }

        if (data.isQuesting()) {
            this.ticks = reader.readTicks();
            this.hours = reader.readHours();
            if (reader.readBoolean()) {
                this.selectedQuest = reader.readQuests();
                this.selectedTask = reader.readTasks();
            }
            this.hasPlayedLore = reader.readBoolean();
            this.hasReceivedBook = reader.readBoolean();
            if (reader.readBoolean()) {
                this.team = new Team(-1, reader);
            } else {
                this.team = data.getTeam(reader.readTeams());
            }

            int groupCount = reader.readGroupCount();
            for (int i = 0; i < groupCount; i++) {
                int id = reader.readGroupCount();
                int limit = reader.readLimit();
                bagData.add(new ImmutablePair<Integer, Integer>(id, limit));
            }
        }

        deaths = new int[15];
        for (int i = 0; i < 15; i++) {
            deaths[i] = reader.readDeaths();
        }
    }

    public void write(DataWriter writer, QuestingData data) {
        writer.writeName(this.name);

        if (data.isHardcore()) {
            writer.writeLives(this.lives);
        }

        if (data.isQuesting()) {
            writer.writeTicks(this.ticks);
            writer.writeHours(this.hours);

            boolean selected = this.selectedQuest != -1 || this.selectedTask != -1;

            writer.writeBoolean(selected);
            if (selected) {
                writer.writeQuests(this.selectedQuest);
                writer.writeTasks(this.selectedTask);
            }

            writer.writeBoolean(this.hasPlayedLore);
            writer.writeBoolean(this.hasReceivedBook);

            if (this.team.getId() == -1) {
                writer.writeBoolean(true);
                this.team.write(writer);
            } else {
                writer.writeBoolean(false);
                writer.writeTeams(this.team.getId());
            }

            writer.writeGroupCount(bagData.size());
            for (int i = 0; i < bagData.size(); i++) {
                writer.writeGroupCount(bagData.get(i).getKey());
                writer.writeLimit(bagData.get(i).getValue());
            }
        }

        for (int i = 0; i < 15; i++) {
            writer.writeDeaths(deaths[i]);
        }
    }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
    public String getName() { return name; }
}
