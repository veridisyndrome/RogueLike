package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

public interface Consumable {
    /** Removes the entity from the area*/
    void consume();

    /** @return: true if the entity is consumed*/
    boolean isConsumed();
}
