package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


public class ICRoguePlayer extends ICRogueActor {
    private static final float MOVE_DURATION = 0.25f;
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

    private float timeToFire = 1.0F;

    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT), deltaTime);
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP), deltaTime);
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT), deltaTime);
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN), deltaTime);

        timeToFire += deltaTime;

        if(keyboard.get(Keyboard.X).isDown() && (timeToFire >= 1.5F)){
            launchFire(getOrientation());
            timeToFire = 0;
        }

        super.update(deltaTime);
    }

    public void launchFire(Orientation orientation) {
        final Fire fire = new Fire(getOwnerArea(), orientation, getCurrentMainCellCoordinates());
        getOwnerArea().registerActor(fire);
    }


    private void moveIfPressed(Orientation orientation, Button b, float deltaTime) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move((int)(MOVE_DURATION/deltaTime));
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
}
