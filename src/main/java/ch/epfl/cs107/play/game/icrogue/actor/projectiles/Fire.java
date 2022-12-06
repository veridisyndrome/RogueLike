package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Fire extends Projectile {
    public Fire(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        this.damagePts = 1;
        this.frames = 10;

        setSprite(new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0)));
    }

    @Override
    public void consume() {
        super.consume();
        getOwnerArea().unregisterActor(this);
    }
}
