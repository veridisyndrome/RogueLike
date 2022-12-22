package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class Level0EnemyRoom extends Level0Room {
    private final List<Enemy> enemies;

    /**
     * Default Level0EnemyRoom constructor.
     *
     * @param roomCoordinates (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
     * @param enemies         (List<Enemy>): List of the enemies that will be present in the corresponding room. Not null
     */
    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates, List<Enemy> enemies) {
        super(roomCoordinates);
        this.enemies = enemies;
    }

    /**
     * Alternative Level0EnemyRoom constructor.
     *
     * @param roomCoordinates (DiscreteCoordinates): Coordinate of the room on the level's map. Not null
     */
    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
       this(roomCoordinates, new ArrayList<>());
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (int i = 0; i < enemies.size(); i++) {
            if(!enemies.get(i).isAlive()) {
                unregisterActor(enemies.get(i));
                enemies.remove(i);
                --i;
            }
        }
    }

    /**
     * Adds the enemy to the room.
     *
     * @param enemy (Enemy): enemy added to the room. Not null
     */
    protected void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    @Override
    public boolean isOn() {
        return enemies.isEmpty() && super.isOn();
    }

    @Override
    public void createArea(Window window) {
        super.createArea(window);
        for (Enemy enemy : enemies) {
            registerActor(enemy);
        }
    }
}
