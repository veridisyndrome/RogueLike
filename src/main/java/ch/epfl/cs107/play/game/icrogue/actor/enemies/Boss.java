package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.BossLifePoint;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Water;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Boss extends Enemy {
    private static final float MOVE_DURATION = 0.25f;
    private final static float COOLDOWN = 2.f;
    private float counter = COOLDOWN;
    private float movement = .4f;
    private static final int NB_ORIENTATION = 4;
    private final Orientation[] orientations;
    private final Sprite bossDown;
    private final Sprite bossUp;
    private final Sprite bossLeft;
    private final Sprite bossRight;
    private final ICRogueInteractionHandler handler = new Boss.ICRogueBossInteractionHandler();

    /**
     * Default Boss constructor.
     * Initialises the orientation sprites.
     *
     * @param area         (Area): Owner area. Not null
     * @param orientation  (Orientation): Initial orientation of the entity in the Area. Not null
     * @param coordinates  (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     * @param orientations (Orientation): Orientations in which the entity fires. Not null
     */
    public Boss(Area area, Orientation orientation, DiscreteCoordinates coordinates, Orientation... orientations) {
        super(area, orientation, coordinates, new BossLifePoint(6));
        this.orientations = orientations;

        bossDown = new Sprite("zelda/trueBossDown", 2f, 1.5f, this, new RegionOfInterest(0, 0, 32, 32), new Vector(-0.5f, -.15f));

        bossUp = new Sprite("zelda/trueBossUp", 2f, 1.5f, this, new RegionOfInterest(0, 0, 32, 32), new Vector(-0.5f, -.15f));

        bossLeft = new Sprite("zelda/trueBossLeft", 2f, 1.5f, this, new RegionOfInterest(0, 0, 32, 32), new Vector(-0.5f, -.15f));

        bossRight = new Sprite("zelda/trueBossRight", 2f, 1.5f, this, new RegionOfInterest(0, 0, 32, 32), new Vector(-0.5f, -.15f));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        counter += deltaTime;
        movement -= deltaTime;
        if (counter >= COOLDOWN) {
            for (Orientation orientation : orientations) {
                launchWater(orientation);
            }
            counter = 0;
        }
        if (movement <= 0) {
            orientate(Orientation.values()[RandomHelper.roomGenerator.nextInt(NB_ORIENTATION)]);
            movement = .4f;
        }
        move((int) (MOVE_DURATION / deltaTime));
    }

    /**
     * Fires a water ball in the given orientation.
     *
     * @param orientation (Orientation): Defines the orientation where to fire. Not null
     */
    public void launchWater(Orientation orientation) {
        final Water water = new Water(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(water);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        switch (getOrientation()) {
            case DOWN -> bossDown.draw(canvas);
            case UP -> bossUp.draw(canvas);
            case LEFT -> bossLeft.draw(canvas);
            case RIGHT -> bossRight.draw(canvas);
        }
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

    private class ICRogueBossInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(Fire fire, boolean isCellInteraction) {
            ICRogueInteractionHandler.super.interactWith(fire, isCellInteraction);
            if (isCellInteraction && !fire.isConsumed()) {
                damage(1);
                fire.consume();
            }
        }
    }
}
