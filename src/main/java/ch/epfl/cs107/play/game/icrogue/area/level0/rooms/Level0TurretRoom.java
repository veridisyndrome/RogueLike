package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0TurretRoom extends Level0EnemyRoom {
    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        addEnemy(new Turret(this, Orientation.UP, Orientation.LEFT, new DiscreteCoordinates(1,8)));
        addEnemy(new Turret(this, Orientation.DOWN, Orientation.RIGHT, new DiscreteCoordinates(8,1)));
    }
}
