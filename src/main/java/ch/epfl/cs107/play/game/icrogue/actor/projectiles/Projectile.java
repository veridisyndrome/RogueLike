package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Projectile extends ICRogueActor implements Consumable {

    Sprite sprite;

    public void setSprite(Sprite sprite) {
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
}
