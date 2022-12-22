package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.LifePoint;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Turret extends Enemy {
    private final static float ARROW_COOLDOWN = 1.f;

    // cooldown needed to prevent that an arrow is consumed by its own turret
    private final static float CONSUME_COOLDOWN = .1f;
    private float arrowCounter = ARROW_COOLDOWN;
    private float consumeCounter = CONSUME_COOLDOWN;

    private final Orientation[] orientations;
    private final Sprite turretSprite = new Sprite("icrogue/static_npc", 1.5f, 1.5f, this , null , new Vector(-0.25f, 0));
    private final ICRogueInteractionHandler handler = new Turret.ICRogueTurretInteractionHandler();

    /**
     * Default Turret constructor.
     *
     * @param area         (Area): Owner area. Not null
     * @param orientation  (Orientation): Initial orientation of the entity in the Area. Not null
     * @param coordinates  (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     * @param orientations (Orientation): Orientations in which the entity fires. Not null
     */
    public Turret(Area area, Orientation orientation, DiscreteCoordinates coordinates, Orientation... orientations) {
        super(area, orientation, coordinates, new LifePoint(1));
        this.orientations = orientations;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        arrowCounter += deltaTime;
        consumeCounter += deltaTime;
        if (arrowCounter >= ARROW_COOLDOWN) {
            for (Orientation orientation : orientations) {
                launchArrow(orientation);
            }
            arrowCounter = 0;
        }
    }

    /**
     * Fires an arrow in the given orientation.
     *
     * @param orientation (Orientation): Defines the orientation where to shoot. Not null
     */
    public void launchArrow(Orientation orientation) {
        final Arrow arrow = new Arrow(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(arrow);
        consumeCounter = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        turretSprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    /** Handles the interactions between the turret and its environment*/
    private class ICRogueTurretInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(Fire fire, boolean isCellInteraction) {
            if (isCellInteraction && !fire.isConsumed()) {
                kill();
                fire.consume();
            }
        }

        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
            if (isCellInteraction) {
                kill();
            }
        }

        // Turrets can now consume other turrets' arrows
        @Override
        public void interactWith(Arrow arrow, boolean isCellInteraction) {
            ICRogueInteractionHandler.super.interactWith(arrow, isCellInteraction);
            if (isCellInteraction && !arrow.isConsumed() && consumeCounter >= CONSUME_COOLDOWN) {
                arrow.consume();
                consumeCounter = 0;
            }
        }
    }
}
