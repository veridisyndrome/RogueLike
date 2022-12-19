package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.signal.logic.Logic;

public class LifePoint implements Logic {

    private float health;

    public LifePoint(float health) {
        this.health = health;
    }

    public void damage(float amount) {
        health -= amount;
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
}
