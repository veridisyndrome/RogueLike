package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Connector extends AreaEntity {

    private ConnectorState state = ConnectorState.INVISIBLE;
    private String destinationArea;
    private DiscreteCoordinates destinationCoords;
    private String destinationAreaName;
    private final Sprite invisibleDoor;
    private final Sprite closedDoor;
    private final Sprite lockedDoor;
    private final static int NO_KEY_ID = 1;
    private int keyId;

    /**
     * Default Connector constructor.
     * Initialises the orientation sprites.
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public Connector(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

        invisibleDoor = new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(), (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        lockedDoor = new Sprite("icrogue/lockedDoor_"+orientation.ordinal(), (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        closedDoor = new Sprite("icrogue/door_"+orientation.ordinal(), (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
    }

    /** @return (boolean): true if the connector is closed */
    public boolean isClosed() {
        return state == ConnectorState.CLOSED;
    }

    /** @return (boolean): true if the connector is opened */
    public boolean isOpen() {
        return state == ConnectorState.OPEN;
    }

    /** opens the connector */
    public void open() {
        state = ConnectorState.OPEN;
    }

    /** closes the connector */
    public void close() {
        state = ConnectorState.CLOSED;
    }

    /** locks the connector */
    public void lock(int keyId) {
        state = ConnectorState.LOCKED;
        this.keyId = keyId;
    }

    /**
     * Unlocks the locked connector if the entity has the key with the matching identifier.
     *
     * @param keyID (int): Key identifier
     */
    public void tryUnlock(int keyID) {
        if (keyID == this.keyId) {
            open();
        }
    }

    /** Enumeration of the connectors' states */
    private enum ConnectorState {
        OPEN,
        CLOSED,
        LOCKED,
        INVISIBLE
        }

    public void setDestination(String destinationArea, DiscreteCoordinates destinationCoords) {
        this.destinationArea = destinationArea;
        this.destinationCoords = destinationCoords;
    }

    public DiscreteCoordinates getDestinationCoords() {
        return destinationCoords;
    }

    public String getDestinationArea() {
        return destinationArea;
    }

    @Override
    public void draw(Canvas canvas) {
        switch (state) {
            case INVISIBLE -> invisibleDoor.draw(canvas);
            case CLOSED -> closedDoor.draw(canvas);
            case LOCKED -> lockedDoor.draw(canvas);
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord, coord.jump(new Vector((getOrientation().ordinal()+1)%2, getOrientation().ordinal()%2)));
    }

    @Override
    public boolean takeCellSpace() {
        return !isOpen();
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

}
