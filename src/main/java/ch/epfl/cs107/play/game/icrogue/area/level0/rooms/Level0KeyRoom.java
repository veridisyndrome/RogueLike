package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0KeyRoom extends Level0ItemRoom {
    /**
     * Default Level0KeyRoom constructor.
     * Adds the key item to the room.
     *
     * @param roomCoordinates (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
     */

    public Level0KeyRoom(DiscreteCoordinates roomCoordinates, int keyID) {
        super(roomCoordinates);
        addItem(new Key(this, Orientation.UP, new DiscreteCoordinates(5,5), keyID));
    }
}
