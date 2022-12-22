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

    protected abstract void generateBossRoom(DiscreteCoordinates bossCoords);
    protected abstract void setUpConnector(MapState[][] roomsPlacement, ICRogueRoom room);
    abstract void generateRoom(DiscreteCoordinates coords, int type);
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

    void generateFixedMap(int width, int height) {
        roomMap = new ICRogueRoom[width][height];
    }

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

    private boolean freeSlots(DiscreteCoordinates coords) {
        if ((coords.x > 0 && coords.x < width) && (coords.y > 0 && coords.y < height)) {
            return mapStates[coords.x][coords.y] == MapState.NULL;
        }
        return false;
    }

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

    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyID) {
        final ICRogueRoom room = roomMap[coords.x][coords.y];
        room.lockConnector(connector.getIndex(), keyID);
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
