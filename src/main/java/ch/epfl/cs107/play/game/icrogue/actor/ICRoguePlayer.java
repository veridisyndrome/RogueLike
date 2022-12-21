package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.LifePoint;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Boss;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Heart;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Water;
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
    private boolean isVisited;
    private boolean isFiring;
    private boolean isFighting;
    private Connector passingConnector;
    private final ICRoguePlayerInteractionHandler handler = new ICRoguePlayerInteractionHandler();
    private final List<Integer> keyHold = new ArrayList<>();
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
    private final Animation animationStaffDown;
    private final Animation animationStaffUp;
    private final Animation animationStaffLeft;
    private final Animation animationStaffRight;
    private final Animation animationSwordDown;
    private final Animation animationSwordUp;
    private final Animation animationSwordLeft;
    private final Animation animationSwordRight;

    /**
     * Default ICRoguePlayer constructor.
     * Initialises the orientation sprites.
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     * @param lifePoint   (LifePoint): Initial life points of the entity. Not null
     */
    public ICRoguePlayer(Area area, Orientation orientation, DiscreteCoordinates position, LifePoint lifePoint) {
        super(area, orientation, position);

        down = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
        Sprite[] spriteDown = Sprite.extractSprites("zelda/playerDown", 4,.75f, 1.5f, this, new Vector(.15f, -.15f), 16, 32);
        animationDown = new Animation(4, spriteDown);

        up = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 64, 16, 32), new Vector(.15f, -.15f));
        Sprite[] spriteUp = Sprite.extractSprites("zelda/playerUp", 4,.75f, 1.5f, this, new Vector(.15f, -.15f), 16, 32);
        animationUp = new Animation(4, spriteUp);

        left = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 96, 16, 32), new Vector(.15f, -.15f));
        Sprite[] spriteLeft = Sprite.extractSprites("zelda/playerLeft", 4,.75f, 1.5f, this, new Vector(.15f, -.15f), 16, 32);
        animationLeft = new Animation(4, spriteLeft);

        right = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 32, 16, 32), new Vector(.15f, -.15f));
        Sprite[] spriteRight = Sprite.extractSprites("zelda/playerRight", 4,.75f, 1.5f, this, new Vector(.15f, -.15f), 16, 32);
        animationRight = new Animation(4, spriteRight);

        Sprite[] waterStaffDown = Sprite.extractSprites("zelda/playerStaffDown",4,1.3f, 1.5f, this, new Vector(.02f, -.15f), 32, 32);
        animationStaffDown = new Animation(4, waterStaffDown);

        Sprite[] waterStaffUp = Sprite.extractSprites("zelda/playerStaffUp",4,1.3f, 1.5f, this, new Vector(.02f, -.15f), 32, 32);
        animationStaffUp = new Animation(4, waterStaffUp);

        Sprite[] waterStaffLeft = Sprite.extractSprites("zelda/playerStaffLeft",4,1.3f, 1.5f, this, new Vector(.02f, -.15f), 32, 32);
        animationStaffLeft = new Animation(4, waterStaffLeft);

        Sprite[] waterStaffRight = Sprite.extractSprites("zelda/playerStaffRight",4,1.3f, 1.5f, this, new Vector(.02f, -.15f), 32, 32);
        animationStaffRight = new Animation(4, waterStaffRight);

        Sprite[] swordDown = Sprite.extractSprites("zelda/playerSwordDown",4,1.3f, 1.5f, this, new Vector(.02f, -.15f), 32, 32);
        animationSwordDown = new Animation(4, swordDown);

        Sprite[] swordUp = Sprite.extractSprites("zelda/playerSwordUp",4,1.3f, 1.5f, this, new Vector(.02f, -.15f), 32, 32);
        animationSwordUp = new Animation(4, swordUp);

        Sprite[] swordLeft = Sprite.extractSprites("zelda/playerSwordLeft",4,1.3f, 1.5f, this, new Vector(.02f, -.15f), 32, 32);
        animationSwordLeft = new Animation(4, swordLeft);

        Sprite[] swordRight = Sprite.extractSprites("zelda/playerSwordRight",4,1.3f, 1.5f, this, new Vector(.02f, -.15f), 32, 32);
        animationSwordRight = new Animation(4, swordRight);

        this.lifePoint = lifePoint;
    }


    /** Cooldown to wait to fire with the staff.*/
    private float timeToFire = 1.0F;

    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT), deltaTime);
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP), deltaTime);
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT), deltaTime);
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN), deltaTime);

        timeToFire += deltaTime;


        if (keyboard.get(Keyboard.X).isDown() && (timeToFire >= 1.0F) && staffCollected) {
            isFiring = true;
            launchFire(getOrientation());
            timeToFire = 0;
        }

        if (timeToFire > .4f) {
            isFiring = false;
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
     * @param orientation (Orientation): Defines the orientation where to fire. Not null
     */
    public void launchFire(Orientation orientation) {
        final Fire fire = new Fire(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(fire);
    }

    /**
     * Moves the player in the chosen direction
     *
     * @param orientation (Orientation): Defines the orientation where to move. Not null
     * @param b           (Button): Button pressed. Not null
     * @param deltaTime   (float): Elapsed time since last update, in seconds, non-negative. Not null
     */
    private void moveIfPressed(Orientation orientation, Button b, float deltaTime) {
        if (b.isDown() ) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move((int) (MOVE_DURATION / deltaTime));
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Orientation orientation = getOrientation();
        if (isDisplacementOccurs() && !isFiring) {
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
        } else if (isFighting) {
            switch (orientation) {
                case UP -> animationSwordUp.draw(canvas);
                case RIGHT -> animationSwordRight.draw(canvas);
                case DOWN -> animationSwordDown.draw(canvas);
                case LEFT -> animationSwordLeft.draw(canvas);
            }

            isFighting = false;

        } else {
            switch (orientation) {
                case UP -> animationStaffUp.draw(canvas);
                case RIGHT -> animationStaffRight.draw(canvas);
                case DOWN -> animationStaffDown.draw(canvas);
                case LEFT -> animationStaffLeft.draw(canvas);
            }
        }
        lifePoint.draw(canvas);
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

        /**
         * Collects the heart when a cell interaction occurs and increments the life points by 1.
         *
         * @param heart             (Heart): heart in the room. Not null
         * @param isCellInteraction (boolean): verifies the state of the cell interaction. Not null
         */
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

        //TODO a revoir
        /**
         * Unlocks the locked door if the player has the right key
         *
         * @param connector         (Connector): Locked connector. Not null
         * @param isCellInteraction (boolean): verifies the state of the cell interaction. Not null
         */

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

        @Override
        public void interactWith(Water water, boolean isCellInteraction) {
            ICRogueInteractionHandler.super.interactWith(water, isCellInteraction);
            if (isCellInteraction && !water.isConsumed()) {
                lifePoint.damage(1.f);
                water.consume();
            }
        }

        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {
            ICRogueInteractionHandler.super.interactWith(turret, isCellInteraction);
            isFighting = true;
        }

        @Override
        public void interactWith(Boss boss, boolean isCellInteraction) {
            ICRogueInteractionHandler.super.interactWith(boss, isCellInteraction);
            lifePoint.damage(1);
        }

    }

    /** @return (boolean): Indicates which connector the player is passing*/
    public Connector getPassingConnector() {
        return passingConnector;
    }
}
