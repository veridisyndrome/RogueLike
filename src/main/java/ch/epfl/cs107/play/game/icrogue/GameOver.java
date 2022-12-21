package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;
import static ch.epfl.cs107.play.math.TextAlign.Horizontal.CENTER;
import static ch.epfl.cs107.play.math.TextAlign.Vertical.*;
import static java.awt.Color.*;

public class GameOver implements Graphics, Logic {

    @Override
    public void draw(Canvas canvas) {
        final Vector anchor = canvas.getTransform().getOrigin().add(0, 2);

        final TextGraphics text = new TextGraphics("GAME OVER", 2, WHITE, null, 0, false, false, anchor, CENTER, TOP, 1, 0);
        final TextGraphics text2 = new TextGraphics("Press R to restart the game", 1, WHITE, null, 0, false, false, anchor.sub(0,4), CENTER, TOP, 1, 0);

        text.setFontName("Kenney Pixel");
        text2.setFontName("Kenney Pixel");
        text.draw(canvas);
        text2.draw(canvas);
    }

    @Override
    public boolean isOn() {
        return false;
    }

    @Override
    public boolean isOff() {
        return false;
    }

    @Override
    public float getIntensity() {
        return 0;
    }
}
