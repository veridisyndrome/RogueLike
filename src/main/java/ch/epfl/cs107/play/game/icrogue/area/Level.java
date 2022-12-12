package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Level {
    private int width;
    private int height;
    private ICRogueRoom[][] roomArea;
    private final DiscreteCoordinates destinationCoords;
    private DiscreteCoordinates bossCoords;
    private String startingRoomName;

    public Level(DiscreteCoordinates destinationCoords, int width, int height) {
        this.destinationCoords = destinationCoords;
        roomArea = new ICRogueRoom[width][height];
        this.width = width;
        this.height = height;
        this.bossCoords = new DiscreteCoordinates(0,0);
        generateFixedMap();
    }

    abstract void generateFixedMap();

    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom roomArea) {
        this.roomArea[coords.x][coords.y] = roomArea;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector) {
        final ICRogueRoom room = roomArea[coords.x][coords.y];
        room.setConnectorDestination(connector.getIndex(), destination, destinationCoords);
    }

    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector) {
        final ICRogueRoom room = roomArea[coords.x][coords.y];
        room.setConnectorDestination(connector.getIndex(), destination, destinationCoords);
        room.closeConnector(connector.getIndex());
    }

    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyID) {
        final ICRogueRoom room = roomArea[coords.x][coords.y];
        room.lockConnector(connector.getIndex(), keyID);
    }

    public ICRogueRoom[][] getRoomArea() {
        return roomArea;
    }

    public void setStartingRoomName(String startingRoomName) {
        this.startingRoomName = startingRoomName;
    }

    public String getStartingRoomName() {
        return startingRoomName;
    }

    protected String setStartingRoomName(DiscreteCoordinates coords) {
        return "icrogue/level0" + coords.x + coords.y;
    }
}
