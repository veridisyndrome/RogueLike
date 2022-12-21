package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class LifePoint implements Logic, Graphics {

    private float health;
    private Sprite fullLife;
    private Sprite midLife;
    private Sprite nullLife;

    public LifePoint(float health) {
        this.health = health;
        fullLife = new Sprite("zelda/fullLife", .75f, .75f, null, new RegionOfInterest(0, 0, 16, 16));
        midLife = new Sprite("zelda/midLife", .75f, .75f,  null, new RegionOfInterest(0, 0, 16, 16));
        nullLife = new Sprite("zelda/nullLife", .75f, .75f, null, new RegionOfInterest(0, 0, 16, 16));
    }


    public void damage(float amount) {
        health -= amount;
    }

    public void heal(float amount) {
        if(health < 3) {
            health += amount;
        }
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

    public void kill() {
        health = 0;
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
