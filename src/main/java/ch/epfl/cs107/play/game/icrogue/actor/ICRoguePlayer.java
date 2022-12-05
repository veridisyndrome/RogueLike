package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;


public class ICRoguePlayer extends ICRogueActor implements Interactor {
    private static final int MOVE_DURATION = 8;
    private final Sprite down;
    private final Sprite right;
    private final Sprite up;
    private final Sprite left;

    public ICRoguePlayer(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);


        //bas
        down = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
        // droite
        right = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 32, 16, 32), new Vector(.15f, -.15f));
        // haut
        up = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 64, 16, 32), new Vector(.15f, -.15f));
        // gauche
        left = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 96, 16, 32), new Vector(.15f, -.15f));

    }

    public void update(float deltatime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        if (keyboard.get(Keyboard.X).isDown()) {
            launchFire(getOrientation());
        }

        if (keyboard.get(Keyboard.W).isDown()) {
            wantsViewInteraction();
        }

        super.update(deltatime);
    }

    public void launchFire(Orientation orientation) {

        final Fire fire = new Fire(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(fire);
    }


    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }


    @Override
    public void draw(Canvas canvas) {
        Orientation orientation = getOrientation();
        switch (orientation) {
            case UP -> up.draw(canvas);
            case RIGHT -> right.draw(canvas);
            case DOWN -> down.draw(canvas);
            case LEFT -> left.draw(canvas);
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {

    }
}