package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.Level0;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


public class ICRogue extends AreaGame {
    protected Level0 currentLevel;
    protected ICRoguePlayer player;
    private final Win win = new Win();
    private final GameOver gameOver = new GameOver();

    /** Starts the game by generating the map of the level and creating the player*/
    public void initLevel() {
        currentLevel = new Level0();

        for (int i = 0; i < currentLevel.getRoomArea().length; i++)  {
            for (int j = 0; j < currentLevel.getRoomArea()[i].length; j++)  {
                if (currentLevel.getRoomArea()[i][j] != null) {
                    addArea(currentLevel.getRoomArea()[i][j]);
                }
            }
        }

        setCurrentArea(currentLevel.getStartingRoomName(), true);
        player = new ICRoguePlayer(getCurrentArea(), Orientation.UP, new DiscreteCoordinates(2,2), new LifePoint(3));
        getCurrentArea().registerActor(player);
    }

    @Override
    public void update(float deltatime){
        Keyboard keyboard = getWindow().getKeyboard();

        Button buttonR = keyboard.get(Keyboard.R);
        if (buttonR.isDown()){
            reset();
        }

        if (keyboard.get(Keyboard.ESCAPE).isDown()) {
            System.exit(0);
        }

        if (!player.isAlive()) {
            gameOver.draw(getWindow());
            return;
        }
        if (currentLevel.isOn()) {
            win.draw(getWindow());
            return;
        }
        super.update(deltatime);
        if (player.isPassing()) {
            getCurrentArea().unregisterActor(player);
            setCurrentArea(player.getPassingConnector().getDestinationArea(), false);
            player.switchRoom(getCurrentArea(), player.getPassingConnector().getDestinationCoords());
            getCurrentArea().registerActor(player);
        }
    }

    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            initLevel();
            return true;
        }
        return false;
    }

    /** Reset the game*/
    private void reset() {
        begin(getWindow(), getFileSystem());
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
