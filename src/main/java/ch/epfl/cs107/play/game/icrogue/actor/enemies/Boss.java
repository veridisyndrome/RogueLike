package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.LifePoint;
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
    private final Orientation[] orientations;
    private final static float COOLDOWN = 2.f;
    private float counter = COOLDOWN;
    private  float movement = .75f;
    private final ICRogueInteractionHandler handler = new Boss.ICRogueBossInteractionHandler();
    private static final int NB_ORIENTATION = 4;
    private  Sprite bossDown;
    private  Sprite bossUp;
    private  Sprite bossLeft;
    private  Sprite bossRight;

    public Boss(Area area, Orientation orientation, DiscreteCoordinates coordinates, Orientation... orientations) {
        super(area, orientation, coordinates, new LifePoint(10));
        this.orientations = orientations;

        bossDown = new Sprite("zelda/bossDown", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));

        bossUp = new Sprite("zelda/bossUp", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));

        bossLeft = new Sprite("zelda/bossLeft", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));

        bossRight = new Sprite("zelda/bossRight", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
    }

    /**
     * Fires a water ball in the given orientation.
     *
     * @param orientation (Orientation): Defines the orientation where to fire
     */
    public void launchWater(Orientation orientation) {
        final Water water = new Water(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(water);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        counter += deltaTime;
        movement -= deltaTime;
        System.out.println(movement);
        if (counter >= COOLDOWN) {
            for (Orientation orientation : orientations) {
                launchWater(orientation);
            }
            counter = 0;
        }
        if(movement <= 0) {
            orientate( Orientation.values()[RandomHelper.roomGenerator.nextInt(NB_ORIENTATION)]);
            movement = 1.f;
        }

    //    move()

    }

    @Override
    public void draw(Canvas canvas) {
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
            if (isCellInteraction) {
                damage(1);
            }
        }
    }
}
