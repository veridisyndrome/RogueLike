package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.Vector;

public class Level0KeyRoom extends Level0ItemRoom{
    private Key key;

    public Level0KeyRoom(DiscreteCoordinates roomCoordinates, int keyID) {
        super(roomCoordinates);
        addItem(new Key(this, Orientation.UP, new DiscreteCoordinates(5,5), false));
    }
}
