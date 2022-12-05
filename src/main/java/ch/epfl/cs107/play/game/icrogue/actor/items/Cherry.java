package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Cherry extends Item {
    private final Sprite sprite = new Sprite("icrogue/cherry", 0.6f, 0.6f, this);

    public Cherry(Area area, Orientation orientation, DiscreteCoordinates position, boolean isCollected) {
        super(area, orientation, position, isCollected);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
}
