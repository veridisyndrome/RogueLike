package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level {
    private int width;
    private int height;
    private ICRogueRoom[][] roomArea = new ICRogueRoom[width][height];
    private DiscreteCoordinates startCoords;
    private DiscreteCoordinates bossCoords;
    private String startingRoomName;

    public Level(DiscreteCoordinates startCoords, int width, int height) {
        this.startCoords = startCoords;
        this.width = width;
        this.height = height;

        generateFixedMap();
    }

    void generateFixedMap() {

    }

    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom roomArea) {
        this.roomArea[coords.x][coords.y] = roomArea;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector) {
        final ICRogueRoom room = roomArea[coords.x][coords.y];
        room
    }

    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector) {

    }

    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyID) {

    }

    protected void setStartingRoom(DiscreteCoordinates coords, String startingRoomName) {
        this.startingRoomName = startingRoomName;
    }
}
