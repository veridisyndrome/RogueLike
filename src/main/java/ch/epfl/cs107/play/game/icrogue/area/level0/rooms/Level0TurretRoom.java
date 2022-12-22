package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0TurretRoom extends Level0EnemyRoom {
    /**
     * Default Level0TurretRoom constructor.
     * Adds at least one and at most three Turrets to the turret room.
     *
     * @param roomCoordinates (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
     */
    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);

        // Bound used to generate the turret's coordinates in the bottom-left square of the room
        int lowerBound = 1;
        int upperBound = 4;

        // generates the number of turrets between 1 and 3
        int turretsNumber = RandomHelper.roomGenerator.nextInt(lowerBound, upperBound);

        for (int i = 0; i < turretsNumber; i++) {
            // executed only if there is a third turret to place
            if (i == 2) {
                // chooses randomly the turret coordinates, which could be either in the bottom-right square and or the top-left square of the room
                if (RandomHelper.roomGenerator.nextInt(0,2) == 0) {
                    addEnemy(new Turret(this, Orientation.UP, new DiscreteCoordinates(RandomHelper.roomGenerator.nextInt(1,4), RandomHelper.roomGenerator.nextInt(6, 9)), Orientation.DOWN, Orientation.RIGHT, Orientation.UP, Orientation.LEFT));
                } else {
                    addEnemy(new Turret(this, Orientation.UP, new DiscreteCoordinates(RandomHelper.roomGenerator.nextInt(6, 9), RandomHelper.roomGenerator.nextInt(1, 4)), Orientation.DOWN, Orientation.RIGHT, Orientation.UP, Orientation.LEFT));
                }
                break;
            }
            addEnemy(new Turret(this, Orientation.UP, new DiscreteCoordinates(RandomHelper.roomGenerator.nextInt(lowerBound, upperBound), RandomHelper.roomGenerator.nextInt(lowerBound, upperBound)), Orientation.DOWN, Orientation.RIGHT, Orientation.UP, Orientation.LEFT));

            // allows to generate a turret in the top-right square on the next step of the loop
            lowerBound += 5;
            upperBound += 5;
        }
    }
}
