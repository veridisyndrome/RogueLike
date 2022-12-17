package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.List;

public abstract class Level0EnemyRoom extends Level0Room{
    private List<Enemy> enemies;

    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates, List<Enemy> enemies) {
        super(roomCoordinates);
        this.enemies = enemies;
    }

    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    protected void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    @Override
    public boolean isOn() {
        for (int i = 0; i < enemies.size(); i++) {
            if(!enemies.get(i).isAlive()) {
                return  false;
            }
        }
        return super.isOn();
    }

    @Override
    public void createArea(Window window) {
        super.createArea(window);
        for (Enemy enemy : enemies) {
            registerActor(enemy);
        }
    }



}
