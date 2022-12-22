package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Boss;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0BossRoom extends Level0EnemyRoom {
    /**
     * Default Level0BossRoom constructor.
     * Adds the boss to the boos room.
     *
     * @param roomCoordinates (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
     */
    public Level0BossRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        addEnemy(new Boss(this, Orientation.UP, new DiscreteCoordinates(4,4), Orientation.DOWN, Orientation.RIGHT, Orientation.LEFT, Orientation.UP));
    }
}
