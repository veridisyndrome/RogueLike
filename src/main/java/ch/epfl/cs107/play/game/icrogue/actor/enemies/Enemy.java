package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.LifePoint;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Enemy extends ICRogueActor implements Interactor {
    private final LifePoint lifePoint;

    /**
     * Default Enemy constructor.
     * Initialises the orientation sprites.
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     * @param lifePoint   (LifePoint): Initial life points of the entity. Not null
     */
    public Enemy(Area area, Orientation orientation, DiscreteCoordinates position, LifePoint lifePoint) {
        super(area, orientation, position);
        this.lifePoint = lifePoint;
    }

    /** @return (boolean): true if the entity's health is greater than 0 */
    public boolean isAlive() {
        return lifePoint.isOn();
    }

    /** sets the entity's health to 0*/
    public void kill() {
        lifePoint.kill();
    }

    /**
     * Reduces the entity's health.
     *
     * @param amount (float): amount subtracted from the entity's health. Greater than 0
     */
    public void damage(float amount) {
        lifePoint.damage(amount);
    }

    @Override
    public void draw(Canvas canvas) {
        lifePoint.draw(canvas);
    }
}
