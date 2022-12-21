package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import static ch.epfl.cs107.play.math.TextAlign.Horizontal.CENTER;
import static ch.epfl.cs107.play.math.TextAlign.Vertical.*;
import static java.awt.Color.WHITE;

public class Win implements Graphics, Logic {
    @Override
    public void draw(Canvas canvas) {
        final Vector anchor = canvas.getTransform().getOrigin().add(0, 2);

        final TextGraphics text1 = new TextGraphics("GAME OVER", 3, WHITE, null, 0, false, false, anchor, CENTER, TOP, 1, 0);



        text1.setFontName("Kenney Pixel");
        text2.setFontName("Kenney Pixel");
        text1.draw(canvas);
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
