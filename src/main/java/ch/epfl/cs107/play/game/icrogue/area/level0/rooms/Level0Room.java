package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Specific area
 */
public class Level0Room extends ICRogueRoom {

    private final DiscreteCoordinates roomCoordinates;

    /**
     * Default Level0Room constructor.
     *
     * @param roomCoordinates (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
     */
    public Level0Room(DiscreteCoordinates roomCoordinates) {
        super(Level0Connectors.getAllConnectorsPosition(), Level0Connectors.getAllConnectorsOrientation(), "icrogue/Level0Room", roomCoordinates);
        this.roomCoordinates = roomCoordinates;
    }

    /** Enumeration of the connectors' orientations */
    public enum Level0Connectors implements ConnectorInRoom {
        W(new DiscreteCoordinates(0, 4), new DiscreteCoordinates(8, 5), Orientation.LEFT),
        S(new DiscreteCoordinates(4, 0), new DiscreteCoordinates(5, 8), Orientation.DOWN),
        E(new DiscreteCoordinates(9, 4), new DiscreteCoordinates(1, 5), Orientation.RIGHT),
        N(new DiscreteCoordinates(4, 9), new DiscreteCoordinates(5, 1), Orientation.UP);

        private final DiscreteCoordinates position;
        private final DiscreteCoordinates destination;
        private final Orientation orientation;
        public Orientation getOrientation() {
            return orientation;
        }

        /**
         * Default Level0Connectors constructor.
         *
         * @param position (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
         */
        Level0Connectors(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation) {
            this.position = position;
            this.destination = destination;
            this.orientation = orientation;
        }

        static List<Orientation> getAllConnectorsOrientation() {
            final List<Orientation> orientationList = new ArrayList<>();
            for (Level0Connectors value : values()) {
                orientationList.add(value.orientation);
            }
            return orientationList;
        }

        static List<DiscreteCoordinates> getAllConnectorsPosition() {
            final List<DiscreteCoordinates> positionList = new ArrayList<>();
            for (Level0Connectors value : values()) {
                positionList.add(value.position);
            }
            return positionList;
        }

        @Override
        public int getIndex() {
            return ordinal();
        }

        @Override
        public DiscreteCoordinates getDestination() {
            return destination;
        }

    }

    @Override
    public String getTitle() {
        return "icrogue/level0" + roomCoordinates.x + roomCoordinates.y;
    }

    @Override
    public boolean isOn() {
        return true;
    }

    @Override
    public boolean isOff() {
        return false;
    }

    @Override
    public float getIntensity() {
        return 0;
    }
}

