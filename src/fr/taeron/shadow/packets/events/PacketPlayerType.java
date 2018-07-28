package fr.taeron.shadow.packets.events;

public enum PacketPlayerType
{
    POSLOOK("POSLOOK", 0), 
    POSITION("POSITION", 1), 
    LOOK("LOOK", 2), 
    FLYING("FLYING", 3);
    
    private PacketPlayerType(final String s, final int n) {
    }
}
