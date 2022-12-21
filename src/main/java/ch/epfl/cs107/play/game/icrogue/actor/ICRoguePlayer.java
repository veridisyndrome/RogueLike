package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.LifePoint;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Boss;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Heart;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ICRoguePlayer extends ICRogueActor implements Interactor {
    private final Sprite down;
    private final Sprite right;
    private final Sprite up;
    private final Sprite left;

    private static final float MOVE_DURATION = 0.25f;
    private boolean canHaveInteraction;
    private boolean staffCollected;
    private boolean isPassing;
    private Connector passingConnector;
    private final ICRoguePlayerInteractionHandler handler = new ICRoguePlayerInteractionHandler();
    private final List<Integer> keyHold = new ArrayList<>();
    private boolean isVisited;
    private final LifePoint lifePoint;

    public void kill() {
        lifePoint.kill();
    }
    public boolean isAlive() {
        return lifePoint.isOn();
    }
    private final Animation animationDown;
    private final Animation animationUp;
    private final Animation animationLeft;
    private final Animation animationRight;
    private  boolean inMovement;


    /**
     *  Cooldown to wait to fire with the staff.
     */
    private float timeToFire = 1.0F;
    private float fireCooldown = .5f;

    /**
     * Simulates a single time step.
     *
     * @param deltaTime (float): Elapsed time since last update, in seconds, non-negative
     */
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT), deltaTime);
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP), deltaTime);
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT), deltaTime);
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN), deltaTime);

        timeToFire += deltaTime;
        fireCooldown += deltaTime;

        if (keyboard.get(Keyboard.X).isDown() && (timeToFire >= 1.0F) && staffCollected) {
            launchFire(getOrientation());
            //System.out.println("3");
            timeToFire = 0;
            fireCooldown = 0;
        }

        if (fireCooldown < .4f) {
            isFiring = true;
        } else {
            isFiring = false;
        }

        if(keyboard.get(Keyboard.UP).isDown() || keyboard.get(Keyboard.LEFT).isDown() || keyboard.get(Keyboard.RIGHT).isDown() || keyboard.get(Keyboard.DOWN).isDown()) {
            inMovement = true;
        } else {
            inMovement = false;
        }
        canHaveInteraction = keyboard.get(Keyboard.W).isDown();

        super.update(deltaTime);
        animationDown.update(deltaTime);
        animationLeft.update(deltaTime);
        animationRight.update(deltaTime);
        animationUp.update(deltaTime);
        animationStaffDown.update(deltaTime);
        animationStaffLeft.update(deltaTime);
        animationStaffRight.update(deltaTime);
        animationStaffUp.update(deltaTime);

    }

    /**
     * Fires a fireball in the given orientation.
     *
     * @param orientation (Orientation): Defines the orientation where to fire
     */
    public void launchFire(Orientation orientation) {
        final Fire fire = new Fire(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(fire);
    }

    /**
     *
     * @param orientation (Orientation): Defines the orientation where to move
     * @param b           (Button): Button pressed
     * @param deltaTime   (float): Elapsed time since last update, in seconds, non-negative
     */
    private void moveIfPressed(Orientation orientation, Button b, float deltaTime) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move((int) (MOVE_DURATION / deltaTime));
            }
        }
    }

    /**
     * Renders itself on a specified canvas.
     *
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        Orientation orientation = getOrientation();
        if (inMovement && !isFiring) {
            switch (orientation) {
                case UP -> animationUp.draw(canvas);
                case RIGHT -> animationRight.draw(canvas);
                case DOWN -> animationDown.draw(canvas);
                case LEFT -> animationLeft.draw(canvas);
            }
        } else if (!isFiring) {
            switch (orientation) {
                case UP -> up.draw(canvas);
                case RIGHT -> right.draw(canvas);
                case DOWN -> down.draw(canvas);
                case LEFT -> left.draw(canvas);
            }
        } else if (isFiring) {
            switch (orientation) {
                case UP -> animationStaffUp.draw(canvas);
                case RIGHT -> animationStaffRight.draw(canvas);
                case DOWN -> animationStaffDown.draw(canvas);
                case LEFT -> animationStaffLeft.draw(canvas);
            }
        }

    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return canHaveInteraction;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    public boolean isPassing() {
        return isPassing;
    }

    public void switchRoom(Area dest, DiscreteCoordinates coords) {
        setOwnerArea(dest);
        setCurrentPosition(coords.toVector());
        resetMotion();
        isPassing = false;
        isVisited = false;
    }

    public class ICRoguePlayerInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(Cherry cherry, boolean isCellInteraction) {
            if (isCellInteraction) {
                cherry.collect();
            }
        }
        public void interactWith(Heart heart, boolean isCellInteraction) {
            if (isCellInteraction) {
                heart.collect();
                lifePoint.heal(1.f);
            }
        }

        @Override
        public void interactWith(Staff staff, boolean isCellInteraction) {
            if (!isCellInteraction) {
                staff.collect();
                staffCollected = true;
            }
        }

        @Override
        public void interactWith(Key key, boolean isCellInteraction) {
            if (isCellInteraction) {
                key.collect();
                keyHold.add(key.getKeyId());
            }
        }

        public void interactWith(Connector connector, boolean isCellInteraction) {
            if (isCellInteraction) {
                isPassing = true;
                passingConnector = connector;
            } else {
                for (int keyID : keyHold) {
                    connector.tryUnlock(keyID);
                }
            }
        }

        @Override
        public void interactWith(Arrow arrow, boolean isCellInteraction) {
            ICRogueInteractionHandler.super.interactWith(arrow, isCellInteraction);
            if (isCellInteraction && !arrow.isConsumed()) {
                lifePoint.damage(1.f);
                arrow.consume();
            }
        }
    }

    public Connector getPassingConnector() {
        return passingConnector;
    }
}
