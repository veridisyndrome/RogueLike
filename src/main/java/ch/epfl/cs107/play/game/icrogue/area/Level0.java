package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0 extends Level  {
    private static final int PART_1_KEY_ID = 3;
    private static final int BOSS_KEY_ID = 4;

    /** Default Level0 constructor */
    public Level0() {
        super(true, RoomType.getDistribution(), new DiscreteCoordinates(2, 2), 5, 5);
    }

    /** Enumeration of the rooms' types */
    public enum RoomType {
        TURRET_ROOM(4), // type and number of room STAFF_ROOM(1),
        STAFF_ROOM(1),
        BOSS_KEY(1),
        SPAWN(1),
        NORMAL(1),
        HEART_ROOM(3);

        private final int nbType;

        /**
         * Default RoomType constructor.
         *
         * @param nbType (int): defines the number of existing room types. Greater that 0
         */
        RoomType(int nbType) {
            this.nbType = nbType;
        }

        static int[] getDistribution() {
            int[] typeList = new int[RoomType.values().length];
            for (int i = 0; i < RoomType.values().length; ++i) {
                typeList[i] = RoomType.values()[i].nbType;
            }
            return typeList;
        }
    }

    void generateFixedMap() {
        generateMap2();
    }

    /** Generate a first beforehand-generated map.*/
    private void generateMap1() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0KeyRoom(room00, PART_1_KEY_ID));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
        lockRoomConnector(room00, Level0Room.Level0Connectors.E,  PART_1_KEY_ID);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
    }

    /** Generate a second beforehand-generated map.*/
    private void generateMap2() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0TurretRoom(room00));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1,0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
        setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connectors.E);

        lockRoomConnector(room10, Level0Room.Level0Connectors.W,  BOSS_KEY_ID);
        setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room20 = new DiscreteCoordinates(2,0);
        setRoom(room20, new Level0StaffRoom(room20));
        setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
        setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3,0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
        setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom (room11, new Level0Room(room11));
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);

        setStartingRoomName("icrogue/level030");
    }

    @Override
    void generateRoom(DiscreteCoordinates coords, int type) {
        RoomType roomType = RoomType.values()[type];
        ICRogueRoom room = switch(roomType) {
            case TURRET_ROOM -> new Level0TurretRoom(coords);
            case BOSS_KEY -> new Level0KeyRoom(coords, BOSS_KEY_ID);
            case SPAWN,NORMAL -> new Level0Room(coords);
            case STAFF_ROOM -> new Level0StaffRoom(coords);
            case HEART_ROOM -> new Level0HeartRoom(coords);
        };
        setRoom(coords, room);
        if (type == 3) {
            setStartingRoomName(coords);
        }
    }

    @Override
    protected void generateBossRoom(DiscreteCoordinates bossCoords) {
        setRoom(bossCoords, new Level0BossRoom(bossCoords));
    }

    @Override
    protected void setUpConnector(MapState[][] roomsPlacement, ICRogueRoom room) {
        DiscreteCoordinates coords = room.getCoords();
        for (Level0Room.Level0Connectors connectors : Level0Room.Level0Connectors.values()) {
            DiscreteCoordinates destinationCoords = coords.jump(connectors.getOrientation().toVector());
            if ((destinationCoords.x >= 0 && destinationCoords.x < roomsPlacement.length) && (destinationCoords.y >= 0 && destinationCoords.y < roomsPlacement[0].length)) {
                MapState destinationState = roomsPlacement[destinationCoords.x][destinationCoords.y];
                if (destinationState == MapState.BOSS_ROOM || destinationState == MapState.CREATED) {
                    setRoomConnector(coords, "icrogue/level0" + destinationCoords.x + destinationCoords.y, connectors);
                }
                if (destinationState == MapState.BOSS_ROOM) {
                    lockRoomConnector(coords, connectors, BOSS_KEY_ID);
                }
            }
        }
    }
}
