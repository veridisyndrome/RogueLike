package ch.epfl.cs107.play.game.icrogue.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Boss;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Heart;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Water;

public interface ICRogueInteractionHandler extends AreaInteractionVisitor {
    /** Deals with the interaction that the entity might have with a cell */
    default void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with the player */
    default void interactWith(ICRoguePlayer player, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with a cherry */
    default void interactWith(Cherry cherry, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with a key */
    default void interactWith(Key key, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with a staff */
    default void interactWith(Staff staff, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with a fireball */
    default void interactWith(Fire fire, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with an arrow */
    default void interactWith(Arrow arrow, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with a turret */
    default void interactWith(Turret turret, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with a connector */
    default void interactWith(Connector connector, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with a heart */
    default void interactWith(Heart heart, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with a water ball */
    default void interactWith(Water water, boolean isCellInteraction) {}

    /** Deals with the interaction that the entity might have with the boss */
    default void interactWith(Boss boss, boolean isCellInterction) {}
}
