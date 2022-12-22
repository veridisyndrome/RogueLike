package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import static ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICRogueCellType.HOLE;
import static ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICRogueCellType.WALL;

public class Fire extends Projectile implements ICRogueInteractionHandler{
    private final FireInteractionHandler handler = new FireInteractionHandler();
    private final Animation animation;

    /**
     * Default Fire constructor.
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public Fire(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        this.damagePts = 1;
        this.frames = 10;

        setSprite(new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0)));
        Sprite[] spriteFires = Sprite.extractSprites("zelda/fire", 7,1f, 1f, this, new Vector(0, 0), 16, 16);
        animation = new Animation(4,spriteFires);
    }
    @Override
    public void consume() {
        super.consume();
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        animation.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        animation.draw(canvas);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    private class FireInteractionHandler implements ICRogueInteractionHandler  {
        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            if ((cell.getType() == HOLE) || (cell.getType() == WALL)) {
                consume();
            }
        }
    }
}
