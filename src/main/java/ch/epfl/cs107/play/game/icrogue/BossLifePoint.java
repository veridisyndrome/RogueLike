package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class BossLifePoint extends LifePoint{
    private final Sprite bossLifeFull;
    private final Sprite bossLife5_6;
    private final Sprite bossLife4_6;
    private final Sprite bossLifeMid;
    private final Sprite bossLife2_6;
    private final Sprite bossLife1_6;

    /**
     * Default BossLifePoint constructor.
     *
     * @param health (float): amount that defines how much damage an entity can take. Greater than 0
     */
    public BossLifePoint(float health) {
        super(health);
        bossLifeFull = new Sprite("zelda/bossLifeFull", 4.5f, .75f, null, new RegionOfInterest(0, 0, 96, 16), new Vector(5,9));
        bossLife5_6 = new Sprite("zelda/bossLife5_6", 4.5f, .75f, null, new RegionOfInterest(0, 0, 96, 16), new Vector(5,9));
        bossLife4_6 = new Sprite("zelda/bossLife4_6", 4.5f, .75f, null, new RegionOfInterest(0, 0, 96, 16), new Vector(5,9));
        bossLifeMid = new Sprite("zelda/bossLifeMid", 4.5f, .75f,  null, new RegionOfInterest(0, 0, 96, 16), new Vector(5,9));
        bossLife2_6 = new Sprite("zelda/bossLife2_6", 4.5f, .75f, null, new RegionOfInterest(0, 0, 96, 16), new Vector(5,9));
        bossLife1_6 = new Sprite("zelda/bossLife1_6", 4.5f, .75f, null, new RegionOfInterest(0, 0, 96, 16), new Vector(5,9));
    }

    @Override
    public void draw(Canvas canvas) {
        if (getHealth() == 6.f) {
            bossLifeFull.draw(canvas);
        } else if (getHealth() == 5.f) {
            bossLife5_6.draw(canvas);
        } else if (getHealth() == 4.f) {
            bossLife4_6.draw(canvas);
        } else if (getHealth() == 3.f) {
            bossLifeMid.draw(canvas);
        } else if (getHealth() == 2.f) {
            bossLife2_6.draw(canvas);
        } else {
            bossLife1_6.draw(canvas);
        }
    }
}
