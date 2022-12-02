package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class ICRogueBehavior extends AreaBehavior {

    /**
     * Default AreaBehavior Constructor
     *
     * @param window (Window): graphic context, not null
     * @param name   (String): name of the behavior image, not null
     */
    public ICRogueBehavior(Window window, String name) {
        super(window, name);
    }

    public enum ICRogueCellType {
        NONE(0,false),
        GROUND(-16777216, true),
        WALL(-14112955, false),
        HOLE(-65536, true);

        private final boolean isWalkable;
        private final int code;

        ICRogueCellType(int code, boolean isWalkable){
            this.code = code;
            this.isWalkable = isWalkable;
        }

        private static ICRogueCellType getTypeFromCode(int code) {
            final ICRogueCellType[] values = ICRogueCellType.values();

            for (ICRogueCellType value : values) {
                if(value.code == code){
                    return value;
                }
            }
            return NONE;
        }
    }

    public class ICRogueCell extends Cell {

        private final ICRogueCellType type;
        public  ICRogueCell(int x, int y, ICRogueCellType type){
            super(x, y);
            this.type = type;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            return false;
        }

        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

        }
    }
}

