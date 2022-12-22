package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.sql.SQLOutput;

public class LifePoint implements Logic, Graphics {

    private float health;
    private final Sprite fullLife;
    private final Sprite midLife;
    private final Sprite nullLife;

    /**
     * Default LifePoint constructor.
     *
     * @param health (float): amount that defines how much damage an entity can take. Greater than 0
     */
    public LifePoint(float health) {
        this.health = health;
        fullLife = new Sprite("zelda/fullLife2", 2.25f, .75f, null, new RegionOfInterest(0, 0, 48, 16), new Vector(0,9));
        midLife = new Sprite("zelda/midLife2", 2.25f, .75f,  null, new RegionOfInterest(0, 0, 48, 16), new Vector(0,9));
        nullLife = new Sprite("zelda/nullLife2", 2.25f, .75f, null, new RegionOfInterest(0, 0, 48, 16), new Vector(0,9));
    }

    /**
     * Decreases the entity's health.
     *
     * @param amount (float): amount subtracted from the entity's health. Greater than 0
     */
    public void damage(float amount) {
        health -= amount;
    }

    /**
     * Increases the entity's health.
     *
     * @param amount (float): amount added to the entity's health. Greater than 0
     */
    public void heal(float amount) {
        if (health < 3) {
            health += amount;
        }
    }

    /** Sets the entity's health to 0 */
    public void kill() {
        health = 0;
    }

    protected float getHealth() {
        return health;
    }

    @Override
    public boolean isOn() {
        return health > 0;
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }

    @Override
    public float getIntensity() {
        return isOn() ? 1 : 0;
    }

    @Override
    public void draw(Canvas canvas) {
        if (health == 3.f) {
            fullLife.draw(canvas);
        } else if (health == 2.f) {
            midLife.draw(canvas);
        } else {
            nullLife.draw(canvas);
        }
    }
}
