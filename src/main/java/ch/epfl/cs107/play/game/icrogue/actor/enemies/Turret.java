package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Turret extends ICRogueActor {
    private Area area;
    private Orientation orientation;
    private final static float COOLDOWN = 2.f;
    private float counter = 0.f;

    public Turret(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        new Sprite("icrogue/static_npc", 1.5f, 1.5f, this , null , new Vector(-0.25f, 0));
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

    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }
}
