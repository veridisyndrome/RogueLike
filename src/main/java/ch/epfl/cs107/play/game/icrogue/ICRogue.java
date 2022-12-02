package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;


public class ICRogue extends AreaGame {
    protected Level0Room currentRoom;
    protected ICRoguePlayer ICRoguePlayer;

    public void initLevel() {
        currentRoom = new Level0Room(new DiscreteCoordinates(0, 0));
        addArea(currentRoom);
        setCurrentArea(currentRoom.getTitle(), true);
        // ICRoguePlayer = new ICRoguePlayer();
        //  currentRoom.registerActor(ICRoguePlayer);

    }

    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            initLevel();
            return true;
        }
        return false;
    }

    @Override
    public String getTitle() {
        return "ICRogue";
    }

}
