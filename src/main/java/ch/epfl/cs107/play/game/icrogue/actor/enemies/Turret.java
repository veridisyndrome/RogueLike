package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Turret extends ICRogueActor {
    private Area area;
    private Orientation orientation;
    private final static float COOLDOWN = 2.f;
    private float counter = 0.f;
    private float hp = 1.f;
    private final Sprite turretSprite = new Sprite("icrogue/static_npc", 1.5f, 1.5f, this , null , new Vector(-0.25f, 0));
    private ICRogueInteractionHandler handler = new Turret.ICRogueTurretInteractionHandler();

    public Turret(Area area, Orientation orientation, Orientation orientation1, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        this.orientation = orientation1;
    }

    public void launchArrow(Orientation orientation) {
        final Arrow arrow = new Arrow(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(arrow);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        counter += deltaTime;
        if (counter >= COOLDOWN) {
            launchArrow(orientation);
        }
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
        return true;
    }

    private class ICRogueTurretInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(Fire fire, boolean isCellInteraction) {
            if (isCellInteraction) {
                death();
            }
        }

        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
            if (isCellInteraction) {
                death();
            }
        }
    }
}
