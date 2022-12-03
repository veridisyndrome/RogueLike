package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICRogueRoom extends Area {

    private final String behaviorName;
    protected final DiscreteCoordinates roomCoordinates;
    public ICRogueRoom(String behaviorName, DiscreteCoordinates roomCoordinates) {
        this.behaviorName = behaviorName;
        this.roomCoordinates = roomCoordinates;
    }

    @Override
    public final float getCameraScaleFactor() {
        return 11;
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            setBehavior(new ICRogueBehavior(window, behaviorName));
            registerActor(new Background(this, behaviorName));
            return true;
        }
        return false;
    }
}
