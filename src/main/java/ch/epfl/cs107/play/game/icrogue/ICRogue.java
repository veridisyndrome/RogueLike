package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.area.Level0;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


public class ICRogue extends AreaGame {
    protected Level0 currentRoom;
    protected ICRoguePlayer player;
    protected Staff staff;
    protected Cherry cherry;
    protected Key key;

    public void initLevel() {
        currentRoom = new Level0();

        for (int i = 0; i < currentRoom.getRoomArea().length; i++)  {
            for (int j = 0; j < currentRoom.getRoomArea()[i].length; j++)  {
                if (currentRoom.getRoomArea()[i][j] != null) {
                    addArea(currentRoom.getRoomArea()[i][j]);
                }
            }
        }

        setCurrentArea(currentRoom.getStartingRoomName(), true);
        player = new ICRoguePlayer(getCurrentArea(), Orientation.UP, new DiscreteCoordinates(2,2));
        getCurrentArea().registerActor(player);
    }


    public void update(float deltatime){
        super.update(deltatime);
        Keyboard keyboard= getWindow().getKeyboard();

        Button buttonR = keyboard.get(Keyboard.R);
        if(buttonR.isDown()){
            reset();
        }
        if(player.isPassing()) {
            getCurrentArea().unregisterActor(player);
            setCurrentArea(player.getPassingConnector().getDestinationArea(), false);
            player.switchRoom(getCurrentArea(), player.getPassingConnector().getDestinationCoords());
            getCurrentArea().registerActor(player);
        }
    }

    private void reset() {
        begin(getWindow(), getFileSystem());
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

    @Override
    public int getFrameRate() {
        return 60;
    }
}
