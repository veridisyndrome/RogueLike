package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;

public abstract class Level0ItemRoom extends Level0Room implements Logic {
    private final ArrayList<Item> items = new ArrayList<>();

    public Level0ItemRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    void addItem(Item item) {
        items.add(item);
    }

    @Override
    protected void createArea(Window window) {
        super.createArea(window);
        for (Item item : items) {
            registerActor(item);
        }
    }

    @Override
    public boolean isOn() {
        for (Item item : items) {
            if (item.isHeart()) {
                return true;
            } else if (!item.isCollected()) {
                return false;
            }
        }
        return true;
    }
}
