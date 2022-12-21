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

    private ConnectorState state = ConnectorState.INVISIBLE; //
    private String destinationArea; // Name Of The Destination Area
    private DiscreteCoordinates destinationCoords; //Destination Coordinates Of The Connectors
    private String destinationAreaName;
    private final Sprite invisibleDoor; //Sprite for Invisible Door
    private final Sprite closedDoor; //Sprite for Closed Door
    private final Sprite lockedDoor; //Sprite for Locked Door
    private final static int NO_KEY_ID = 1; //Default Key ID For Connectors
    private int keyId; //

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

    public boolean isClosed() {
        return state == ConnectorState.CLOSED;
    }

    public boolean isOpen() {
        return state == ConnectorState.OPEN;
    }
    public void open() {
        state = ConnectorState.OPEN;
    }
    public void close() {
        state = ConnectorState.CLOSED;
    }

    public void lock(int keyId) {
        state = ConnectorState.LOCKED;
        this.keyId = keyId;
    }

    public void tryUnlock(int keyID) {
        if (keyID == this.keyId) {
            open();
        }
    }

    public void setDestination(String destinationArea, DiscreteCoordinates destinationCoords) {
        this.destinationArea = destinationArea;
        this.destinationCoords = destinationCoords;
    }

    private enum ConnectorState {
        OPEN,
        CLOSED,
        LOCKED,
        INVISIBLE
        }


    /**
     * Destination Coordinates Getters
     *
     */

    public DiscreteCoordinates getDestinationCoords() {
        return destinationCoords;
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

    public String getDestinationArea() {
        return destinationArea;
    }

}
