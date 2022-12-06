package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Specific area
 */
public class Level0Room extends ICRogueRoom {

    public Level0Room(DiscreteCoordinates roomCoordinates) {
        super("icrogue/Level0Room", roomCoordinates);
    }

    @Override
    public String getTitle() {
        return "icrogue/level0" + roomCoordinates.x + roomCoordinates.y;
    }



}

