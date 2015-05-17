package net.technicpack.notimefix.io;

public class PlayerEntry {
    private String name;
    private boolean inTeam;
    private boolean owner;

    public PlayerEntry(String name, boolean inTeam, boolean owner) {
        this.name = name;
        this.inTeam = inTeam;
        this.owner = owner;
    }

    public String getName() { return name; }
    public boolean isInTeam() { return inTeam; }
    public boolean isOwner() { return owner; }
}
