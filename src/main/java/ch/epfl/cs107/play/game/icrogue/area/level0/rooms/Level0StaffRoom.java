package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0StaffRoom extends Level0ItemRoom {
    /**
     * Default Level0StaffRoom constructor.
     * Randomly adds the staff item to the room in the middle 6 by 6 square.
     *
     * @param roomCoordinates (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
     */

    public Level0StaffRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        int bound1 = RandomHelper.roomGenerator.nextInt(2, 7);
        int bound2 = RandomHelper.roomGenerator.nextInt(2, 7);

        if (bound1 < bound2) {
            addItem(new Staff(this, Orientation.UP, new DiscreteCoordinates(bound1, bound2)));
        } else {
            addItem(new Staff(this, Orientation.UP, new DiscreteCoordinates(bound2, bound1)));
        }
    }
}
