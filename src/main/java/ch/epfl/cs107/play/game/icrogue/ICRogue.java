package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.Level0;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.window.Canvas;


public class ICRogue extends AreaGame {
    protected Level0 currentLevel;
    protected ICRoguePlayer player;
    private final Win win = new Win();
    private final GameOver gameOver = new GameOver();
    private LifePoint lifePoint;
    //private final Sprite fullLife;
    //private final Sprite midLife;
    //private final Sprite nullLife;

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

    //fullLife = new Sprite("zelda/fullLife", .75f, 1.5f, this, new RegionOfInterest(0, 96, 16, 32));
    //midLife = new Sprite("zelda/midLife", .75f, 1.5f, this, new RegionOfInterest(0, 96, 16, 32));
    //nullLife = new Sprite("zelda/nullLife", .75f, 1.5f, this, new RegionOfInterest(0, 96, 16, 32));


    @Override
    public void update(float deltatime){
        Keyboard keyboard= getWindow().getKeyboard();

        Button buttonR = keyboard.get(Keyboard.R);
        if(buttonR.isDown()){
            reset();
        }
        if(!player.isAlive()) {
            gameOver.draw(getWindow());
            return;
        }
        if (currentLevel.isOn()) {
            //TODO go to the next level
            win.draw(getWindow());
            return;
        }
        super.update(deltatime);
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

    /*public void draw(Canvas canvas) {
        if(lifePoint.getHealth() == 3.f) {
            System.out.println("A");
            fullLife.draw(canvas);
        } else if (lifePoint.getHealth() == 2.f) {
            System.out.println("B");
            midLife.draw(canvas);
        } else {
            System.out.println("C");
            nullLife.draw(canvas);
        }
    }*/

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
