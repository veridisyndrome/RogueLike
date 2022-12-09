package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.List;

/**
 * Specific area
 */
public class Level0Room extends ICRogueRoom {

    public Level0Room(DiscreteCoordinates roomCoordinates) {
        super("icrogue/Level0Room", roomCoordinates);
    }

    public enum Level0Connectors implements ConnectorInRoom {
        W(new DiscreteCoordinates(0, 4),
                new DiscreteCoordinates(8, 5), Orientation.LEFT),
        S(new DiscreteCoordinates(4, 0),
                new DiscreteCoordinates(5, 8), Orientation.DOWN),
        E(new DiscreteCoordinates(9, 4),
                new DiscreteCoordinates(1, 5), Orientation.RIGHT),
        N(new DiscreteCoordinates(4, 9),
                new DiscreteCoordinates(5, 1), Orientation.UP);


        Level0Connectors(DiscreteCoordinates discreteCoordinates, DiscreteCoordinates discreteCoordinates1, Orientation left) {
        }

        @Override
        public String getTitle() {
            return "icrogue/level0" + roomCoordinates.x + roomCoordinates.y;
        }
    }



}

