package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.LifePoint;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Enemy extends ICRogueActor implements Interactor {
    private final LifePoint lifePoint;
    public Enemy(Area area, Orientation orientation, DiscreteCoordinates position, LifePoint lifePoint) {
        super(area, orientation, position);
        this.lifePoint = lifePoint;
    }
    public boolean isAlive() {
        return lifePoint.isOn();
    }

    public void kill() {
        lifePoint.kill();
    }

    public void damage(float amount) {
        lifePoint.damage(amount);
    }
}
