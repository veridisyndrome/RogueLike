package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;


public abstract class ICRogueRoom extends Area implements Logic {

    private final String behaviorName;
    private final DiscreteCoordinates roomCoordinates;
    private final List<Connector> connectors;

    /**
     * Default ICRogueRoom Constructor.
     * Sets the behavior of the connector in the room.
     *
     * @param connectorsCoordinates (List<DiscreteCoordinates>): List of connectors in the room. Not null
     * @param orientations          (List<Orientation>): List of the connectors' orientations. Not null
     * @param behaviorName          (String): Name of the behavior. Not null
     * @param roomCoordinates       (DiscreteCoordinates): Coordinates of the room on the map. Not null
     */
    public ICRogueRoom(List<DiscreteCoordinates> connectorsCoordinates, List<Orientation> orientations, String behaviorName, DiscreteCoordinates roomCoordinates) {
        this.behaviorName = behaviorName;
        this.roomCoordinates = roomCoordinates;
        connectors = new ArrayList<>();

        for (int i = 0; i < connectorsCoordinates.size(); i++) {
            connectors.add(new Connector(this, orientations.get(i).opposite(), connectorsCoordinates.get(i)));
        }
    }

    @Override
    public final float getCameraScaleFactor() {
        return 11;
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createArea(window);
            return true;
        }
        return false;
    }

    /**
     * Creates an area with all the connectors and actors.
     *
     * @param window (Window): Window where the area is represented. Not null
     */
    protected void createArea(Window window) {
        setBehavior(new ICRogueBehavior(window, behaviorName));
        registerActor(new Background(this, behaviorName));

        for (Connector connector : connectors) {
            registerActor(connector);
        }
    }

    /**
     *
     *
     * @param index (int): Index of the  connector. Not null
     * @param destinationArea (String): Name of the destination area. Not null
     * @param destinationCoords (DiscreteCoordinates): Coordinates of the destination area. Not null
     */
    public void setConnectorDestination(int index, String destinationArea, DiscreteCoordinates destinationCoords) {
        connectors.get(index).setDestination(destinationArea, destinationCoords);
    }

    boolean isPressed = false;

    @Override
    public void update(float deltaTime) {
        if (isOn()) {
            for (Connector connector : connectors) {
                if (connector.isClosed()) {
                    connector.open();
                }
            }
        }

        super.update(deltaTime);

        final Keyboard keyboard = getKeyboard();

        if (keyboard.get(Keyboard.O).isDown()) {
            for (Connector connector : connectors) {
                connector.open();
            }
        }

        if (keyboard.get(Keyboard.L).isDown()) {
            connectors.get(0).lock(1);
        }

        if (keyboard.get(Keyboard.T).isDown() && !isPressed) {
            for (Connector connector : connectors) {
                if(connector.isOpen()) {
                    connector.close();
                } else if(connector.isClosed()){
                    connector.open();
                }
            }
        }
        isPressed = keyboard.get(Keyboard.T).isDown();
    }

    public void closeConnector(int index) {
        connectors.get(index).close();
    }
    public void lockConnector(int index, int keyID) {
        connectors.get(index).lock(keyID);
    }

    public DiscreteCoordinates getCoords() {
        return roomCoordinates;
    }
}
