package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.*;

public abstract class Level implements Logic {
    private final int width;
    private final int height;
    private ICRogueRoom[][] roomMap;
    private MapState[][] mapStates;
    private DiscreteCoordinates bossCoords;
    private String startingRoomName;
    private final DiscreteCoordinates startPosition;

    /**
     * Default Level constructor.
     * Generate the level's map.
     *
     * @param randomMap         (boolean): Determines whether to generate a random or fixed map
     * @param roomsDistribution (int[]): Defines the how many rooms of a certain type have to be created. Not null
     * @param startPosition     (DiscreteCoordinates): Defines the player starting position. Not null
     * @param width             (int): Defines the width of the map. Greater than 0
     * @param height            (int): Defines the height of the map. Greater than 0
     */
    public Level(boolean randomMap, int[] roomsDistribution, DiscreteCoordinates startPosition, int width, int height) {
        this.width = width;
        this.height = height;
        this.startPosition = startPosition;

        if (randomMap) {
            MapState[][] mapStates = generateRandomRoomPlacement(roomsDistribution);
            generateRandomMap(mapStates, roomsDistribution);
        } else {
            generateFixedMap(width, height);
            this.bossCoords = new DiscreteCoordinates(0,0);
        }
    }

    /**
     * Generates a room.
     *
     * @param coords (DiscreteCoordinates): Defines the coordinates of the room. Not null
     * @param type   (int): Defines the type of the room to generate. Non-negative
     */
    abstract void generateRoom(DiscreteCoordinates coords, int type);

    /**
     * Generates the boss room.
     *
     * @param bossCoords (DiscreteCoordinates): Defines the coordinates of the boss room. Not null
     */
    protected abstract void generateBossRoom(DiscreteCoordinates bossCoords);

    /** Enumeration of the rooms' states */
    protected enum MapState {
        NULL, // Empty space
        PLACED, // The room has been placed but not yet explored by the room placement algorithm
        EXPLORED, // The room has been placed and explored by the algorithm
        BOSS_ROOM, // The room is a boss room
        CREATED; // The room has been instantiated in the room map

        @Override
        public String toString() {
            return Integer.toString(ordinal());
        }
    }

    /**
     * Generates a beforehand-generated map.
     *
     * @param width  (int): Defines the width of the map. Greater than 0
     * @param height (int): Defines the height of the map. Greater than 0
     */
    void generateFixedMap(int width, int height) {
        roomMap = new ICRogueRoom[width][height];
    }

    /**
     * Generates a random map and defines the states of the rooms inside it.
     *
     * @param roomsDistribution (int[]): Defines the how many rooms of a certain type have to be created. Not null
     * @return (MapState[][]): Map with defined states of rooms
     */
    public MapState[][] generateRandomRoomPlacement(int[] roomsDistribution) {
        int nbRooms = 0;

        roomMap = new ICRogueRoom[width][height];
        mapStates = new MapState[width][height];

        for (int j : roomsDistribution) {
            nbRooms += j;
        }

        int roomsToPlace = nbRooms;

        for (MapState[] mapState : mapStates) {
            Arrays.fill(mapState, MapState.NULL);
        }
        mapStates[width/2][height/2] = MapState.PLACED;

        while (roomsToPlace > 0) {
            DiscreteCoordinates randomChosenRoom = mapPlaced();
            List<DiscreteCoordinates> freeSlots = new ArrayList<>();
            for (Orientation orientation : Orientation.values()) {
                DiscreteCoordinates coords = randomChosenRoom.jump(orientation.toVector());
                if (freeSlots(coords)) {
                    freeSlots.add(coords);
                }
            }
            mapStates[randomChosenRoom.x][randomChosenRoom.y] = MapState.EXPLORED;

            if (freeSlots.size() != 0) {

                int toPlace = RandomHelper.roomGenerator.nextInt(1, 1+Math.min(freeSlots.size(), roomsToPlace));
                List<Integer> indexes = new ArrayList<>();

                for (int i = 0; i < freeSlots.size(); i++) {
                    indexes.add(i);
                }

                List<Integer> roomIndexes = RandomHelper.chooseKInList(toPlace, indexes);

                for (Integer index : roomIndexes) {
                    mapStates[freeSlots.get(index).x][freeSlots.get(index).y] = MapState.PLACED;
                }
                printMap(mapStates);

                roomsToPlace -= toPlace;
            }

        }
        bossCoords = mapPlaced();
        mapStates[bossCoords.x][bossCoords.y] = MapState.BOSS_ROOM;

        return mapStates;
    }

    /**
     * Gives the coordinates of where to place a new room.
     *
     * @param mapStates (MapState[][]): Map with defined states of rooms. Not null
     * @return (DiscreteCoordinates): Coordinates of where to place a new room
     */
    private DiscreteCoordinates choseRandomRoomToCreate(MapState[][] mapStates)  {
        List<DiscreteCoordinates> linearMap = new ArrayList<>();
        for (int i = 0; i < mapStates.length; ++i) {
            for (int j = 0; j < mapStates[i].length; ++j) {
                if (mapStates[i][j] == MapState.PLACED || mapStates[i][j] == MapState.EXPLORED) {
                    linearMap.add(new DiscreteCoordinates(i, j));
                }
            }
        }

        return linearMap.get(RandomHelper.roomGenerator.nextInt(linearMap.size()));
    }

    /**
     * Generates a random map.
     *
     * @param mapStates         (MapState[][]): Map with defined states of rooms. Not null
     * @param roomsDistribution (int[]): Defines the how many rooms of a certain type have to be created. Not null
     */
    public void generateRandomMap(MapState[][] mapStates, int[] roomsDistribution) {
        this.mapStates = mapStates;

        for (int type = 0; type < roomsDistribution.length; type++) {
            int k = roomsDistribution[type];
            for (int i = 0; i < k; i++) {
                DiscreteCoordinates coords = choseRandomRoomToCreate(mapStates);
                generateRoom(coords, type);
                mapStates[coords.x][coords.y] = MapState.CREATED;
            }
        }

        for (int i = 0; i < roomMap.length; i++) {
            for (int j = 0; j < roomMap[i].length; j++) {
                if (mapStates[i][j] == MapState.CREATED) {
                    setUpConnector(mapStates, roomMap[i][j]);
                }
            }
        }
        generateBossRoom(bossCoords);
        setUpConnector(mapStates, roomMap[bossCoords.x][bossCoords.y]);
    }

    /** @return (DiscreteCoordinates): coordinates of a random room chosen from the list of rooms to place */
    private DiscreteCoordinates mapPlaced() {
        List<DiscreteCoordinates> placedRoomList = new ArrayList<>();
        for(int i = 0; i < mapStates.length; ++i) {
            for(int j = 0; j < mapStates[i].length; ++j) {
                if (mapStates[i][j] == MapState.PLACED) {
                    placedRoomList.add(new DiscreteCoordinates(i, j));
                }
            }
        }

        return placedRoomList.get(RandomHelper.roomGenerator.nextInt(placedRoomList.size()));
    }

    /**
     * Check if there is a free slot at given coordinates.
     *
     * @param coords (DiscreteCoordinates): Coordinates of the slot where to check if it's possible to place a room. Not null
     * @return (boolean): true if the slot is free
     */
    private boolean freeSlots(DiscreteCoordinates coords) {
        if ((coords.x > 0 && coords.x < width) && (coords.y > 0 && coords.y < height)) {
            return mapStates[coords.x][coords.y] == MapState.NULL;
        }
        return false;
    }

    /**
     * Displays the random generated map in the terminal.
     *
     * @param map (MapState[][]): Map with defined states of rooms. Not null
     */
    private void printMap(MapState[][] map) {
        System.out.println("Generated map:");
        System.out.print(" | ");
        for (int j = 0; j < map[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        System.out.print("--|-");
        for (int j = 0; j < map[0].length; j++) {
            System.out.print("--");
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Locks a given connector.
     *
     * @param coords    (DiscreteCoordinates): Coordinate of the connector's destination room to lock. Not null
     * @param connector (ConnectorInRoom): Connector to lock. Not null
     * @param keyID     (int): Key identifier
     */
    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyID) {
        final ICRogueRoom room = roomMap[coords.x][coords.y];
        room.lockConnector(connector.getIndex(), keyID);
    }

    protected abstract void setUpConnector(MapState[][] roomsPlacement, ICRogueRoom room);

    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom roomArea) {
        roomMap[coords.x][coords.y] = roomArea;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector) {
        final ICRogueRoom room = roomMap[coords.x][coords.y];
        room.setConnectorDestination(connector.getIndex(), destination, connector.getDestination());
    }

    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector) {
       final ICRogueRoom room = roomMap[coords.x][coords.y];
        room.setConnectorDestination(connector.getIndex(), destination, connector.getDestination());
        room.closeConnector(connector.getIndex());
    }

    public ICRogueRoom[][] getRoomArea() {
        return roomMap;
    }

    public void setStartingRoomName(String startingRoomName) {
        this.startingRoomName = startingRoomName;
    }

    public String getStartingRoomName() {
        return startingRoomName;
    }

    protected void setStartingRoomName(DiscreteCoordinates coords) {
        startingRoomName = "icrogue/level0" + coords.x + coords.y;
    }

    @Override
    public boolean isOn() {
         return bossCoords != null && roomMap[bossCoords.x][bossCoords.y].isOn();
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }

    @Override
    public float getIntensity() {
        return 0;
    }
}
