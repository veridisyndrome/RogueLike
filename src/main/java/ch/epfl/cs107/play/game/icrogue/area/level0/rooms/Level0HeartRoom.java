package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Heart;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0HeartRoom extends Level0ItemRoom {
    /**
     * Default Level0HeartRoom constructor.
     * Adds the heart item to the room.
     *
     * @param roomCoordinates (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
     */
    public Level0HeartRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        addItem(new Heart(this, Orientation.UP, new DiscreteCoordinates(5,5)));
    }
}
