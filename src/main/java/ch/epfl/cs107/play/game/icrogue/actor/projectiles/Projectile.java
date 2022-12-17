package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class Projectile extends ICRogueActor implements Consumable, Interactor {

    Sprite sprite;

    public void Sprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Projectile(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

    }
    protected int frames = 0;
    protected int damagePts = 0;
    private boolean isConsumed = false;
    private static final int DEFAULT_DAMAGE = 1;
    private static final int DEFAULT_MOVE_DURATION = 10;

    @Override
    public void consume()  {
        isConsumed = true;
    }

    @Override
    public boolean isConsumed() {
        return isConsumed;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        move(frames);
        super.update(deltaTime);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList (getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return true;
    }
}
